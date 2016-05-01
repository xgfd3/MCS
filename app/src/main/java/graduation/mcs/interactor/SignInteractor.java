package graduation.mcs.interactor;

import android.widget.ImageView;
import de.greenrobot.dao.query.QueryBuilder;
import graduation.mcs.dao.Attendance;
import graduation.mcs.dao.AttendanceDao;
import graduation.mcs.dao.Conference;
import graduation.mcs.dao.ConferenceSigned;
import graduation.mcs.dao.ConferenceSignedDao;
import graduation.mcs.helper.DaoHelper;
import graduation.mcs.helper.QrcodeHelper;
import graduation.mcs.helper.VerificationHelper;
import graduation.mcs.helper.XmppHelper;
import graduation.mcs.utils.LogUtils;
import graduation.mcs.utils.ToastUtils;
import graduation.mcs.widget.executor.JobExecutor;
import graduation.mcs.widget.executor.UIExecutor;
import graduation.mcs.widget.qrcode.CaptureActivity;
import graduation.mcs.widget.xmpp.MessageObserver;
import java.util.Date;
import java.util.List;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.packet.Message;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by xucz on 2016/4/23.
 */
public class SignInteractor {

  private final QrcodeHelper qrcodeHelper;
  private final JobExecutor jobExecutor;
  private final UIExecutor uiExecutor;
  private final XmppHelper xmppHelper;
  private final VerificationHelper verificationHelper;
  private final AttendanceDao attendanceDao;
  private final ConferenceSignedDao conferenceSignedDao;

  public SignInteractor() {
    qrcodeHelper = QrcodeHelper.getInstance();
    xmppHelper = XmppHelper.getInstance();
    jobExecutor = JobExecutor.getInstance();
    uiExecutor = UIExecutor.getInstance();
    verificationHelper = VerificationHelper.getInstance();
    attendanceDao = DaoHelper.getInstance().getDaoSession().getAttendanceDao();
    conferenceSignedDao = DaoHelper.getInstance().getDaoSession().getConferenceSignedDao();
  }

  public void displayQrcode(Conference conference, ImageView qrcodeView) {
    String qrcodeJson = qrcodeHelper.getQrcodeJson(conference.getCreator_account_phone(),
        conference.getConference_uuid());
    LogUtils.e(this, qrcodeJson);
    qrcodeHelper.display(qrcodeJson, qrcodeView);
  }

  public void decodeQrcode(String path, CaptureActivity captureActivity) {
    qrcodeHelper.decode(path, captureActivity);
  }

  public void sendQrcodeJson(String result, Subscriber<Integer> subscriber) {
    // 是否登录
    String phone = verificationHelper.verifyIfLogin();
    String nickname = "";
    if (phone == null) {
      ToastUtils.show("please check your network and login first!");
      subscriber.onNext(0);
      subscriber.onCompleted();
      return;
    } else {
      nickname = xmppHelper.getVCard().getNickName();
    }

    // 是否能正确解析
    String res = result;
    String[] parseQrcodeJson = qrcodeHelper.parseQrcodeJson(res);
    final String from = parseQrcodeJson[0];
    String uuid = parseQrcodeJson[1];
    if (uuid == null) {
      ToastUtils.show("the qrcode is not right!");
      subscriber.onNext(1);
      subscriber.onCompleted();
      return;
    }

    Attendance attendance =
        new Attendance(null, uuid, phone, nickname, new Long(new Date().getTime()), "");
    final String json = qrcodeHelper.getSignInfo(attendance);
    final String theme = "1";
    build(new Observable.OnSubscribe<Integer>() {
      @Override public void call(Subscriber<? super Integer> subscriber) {
        Exception e = xmppHelper.sendMessage(from, json, theme);
        if (e == null) {
          subscriber.onNext(2);
        } else {
          subscriber.onError(e);
        }
        subscriber.onCompleted();
      }
    }).subscribe(subscriber);
  }

  private Observable build(Observable.OnSubscribe onSubscribe) {
    return Observable.create(onSubscribe)
        .subscribeOn(Schedulers.from(jobExecutor))
        .observeOn(uiExecutor.getScheduler());
  }

  public void registMessageObserver(MessageObserver messageObserver) {
    xmppHelper.registMessageObserver(messageObserver);
  }

  public void unRegistMessageObserver(MessageObserver messageObserver) {
    xmppHelper.unRegistMessageObserver(messageObserver);
  }

  public Attendance sendConferenceInfo(Chat chat, String body, Conference conference) {
    // 是否能正确解析
    String res = body;
    Attendance attendance = qrcodeHelper.parseSignInfo(res);
    if (attendance == null) {
      ToastUtils.show("the qrcode is not right!");
      return null;
    }

    // 会议uuid是否与当前的相同
    if (!attendance.getConference_uuid().equals(conference.getConference_uuid())) {
      // 不同，发送错误信息：XX会议的签到已停止
      String error1 = qrcodeHelper.getErrorJson(attendance.getConference_uuid(),
          conference.getTheme() + "会议的签到已停止!");
      LogUtils.e(this, "error1" + error1);
      Message msg_error_1 = new Message();
      msg_error_1.setBody(error1);
      msg_error_1.setSubject("3");
      try {
        chat.sendMessage(msg_error_1);
      } catch (SmackException.NotConnectedException e) {
        e.printStackTrace();
      }
      return null;
    }

    // 是否在签到时间内
    long sign_time = attendance.getTime_sign().longValue();
    LogUtils.e(this, "sign_time   " + sign_time);
    LogUtils.e(this, "getTime_begin   " + conference.getTime_begin());
    LogUtils.e(this, "getTime_end   " + conference.getTime_end());
    if (sign_time < conference.getTime_begin() || sign_time > conference.getTime_end()) {
      // 超出签到时间：发送错误信息：XX会议的不会签到时间范围内
      String error2 = qrcodeHelper.getErrorJson(attendance.getConference_uuid(),
          conference.getTheme() + "会议的签到时间已经过去!");
      Message msg_error_2 = new Message();
      msg_error_2.setBody(error2);
      LogUtils.e(this, "error2" + error2);
      msg_error_2.setSubject("3");
      try {
        chat.sendMessage(msg_error_2);
      } catch (SmackException.NotConnectedException e) {
        e.printStackTrace();
      }
      return null;
    }

    // 是否已经签到过了
    QueryBuilder<Attendance> qb = attendanceDao.queryBuilder();
    Attendance attendance_signed = qb.where(
        qb.and(AttendanceDao.Properties.Conference_uuid.eq(attendance.getConference_uuid()),
            AttendanceDao.Properties.Account_phone.eq(attendance.getAccount_phone()))).unique();
    if (attendance_signed != null) {
      // 已签到过了: 发送信息：XX会议已签到过
      String error3 = qrcodeHelper.getErrorJson(attendance.getConference_uuid(),
          conference.getTheme() + "会议已签到过!");
      Message msg_error_3 = new Message();
      msg_error_3.setBody(error3);
      LogUtils.e(this, "error3" + error3);
      msg_error_3.setSubject("3");
      try {
        chat.sendMessage(msg_error_3);
      } catch (SmackException.NotConnectedException e) {
        e.printStackTrace();
      }
      return null;
    }

    // 签到, 插入数据库，并发送会议信息
    attendanceDao.insert(attendance);
    String json = qrcodeHelper.getConferenceJson(conference);
    Message msg = new Message();
    msg.setBody(json);
    msg.setSubject("2");
    LogUtils.e(this, "json" + json);

    try {
      chat.sendMessage(msg);
      return attendance;
    } catch (SmackException.NotConnectedException e) {
      e.printStackTrace();
      return null;
    }
  }

  public boolean onSignSuccess(String body) {
    // 是否能正确解析
    ConferenceSigned conferenceSigned = qrcodeHelper.parseConferenceJson(body);
    if (conferenceSigned != null) {
      conferenceSigned.setSign_account_phone(xmppHelper.getAccountPhone());
      conferenceSignedDao.insert(conferenceSigned);
      return true;
    }
    return false;
  }

  public String onSignFailure(String body) {
    String[] error = qrcodeHelper.parseErrorJson(body);
    if (error != null) {
      return error[1];
    }
    return null;
  }

  public void loadConferenceSigned(Subscriber<List<ConferenceSigned>> subscriber) {
    // 判断是否有激活账号
    final String phone = verificationHelper.verifyActiveAccoutnInDB();
    if( phone == null ){
      ToastUtils.show("please login first");
      subscriber.onNext(null);
      subscriber.onCompleted();
      return;
    }

    build(new Observable.OnSubscribe<List<ConferenceSigned>>() {
      @Override public void call(Subscriber<? super List<ConferenceSigned>> subscriber) {
        List<ConferenceSigned> conferenceSigneds = conferenceSignedDao.queryBuilder()
            .where(ConferenceSignedDao.Properties.Sign_account_phone.eq(phone))
            .list();
        subscriber.onNext(conferenceSigneds);
        subscriber.onCompleted();
      }
    }).subscribe(subscriber);
  }
}
