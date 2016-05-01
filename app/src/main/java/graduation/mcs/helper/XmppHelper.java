package graduation.mcs.helper;

import android.content.Context;
import graduation.mcs.dao.AccountDao;
import graduation.mcs.utils.LogUtils;
import graduation.mcs.utils.ToastUtils;
import graduation.mcs.widget.executor.JobExecutor;
import graduation.mcs.widget.executor.UIExecutor;
import graduation.mcs.widget.xmpp.ConnectionHandler;
import graduation.mcs.widget.xmpp.MessageObserver;
import graduation.mcs.widget.xmpp.XMPP;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by xucz on 2016/4/20.
 */
public class XmppHelper {
  public static final String serviceName = "xucz-pc";
  public static final String host = "192.168.167.1";
  public static final String jid_suffix = "@xucz-pc";

  private final AccountDao accountDao;
  private final XMPP xmpp;
  private final JobExecutor jobExecutor;
  private final UIExecutor uiExecutor;

  private XmppHelper() {
    accountDao = DaoHelper.getInstance().getDaoSession().getAccountDao();
    xmpp = XMPP.getInstance();
    jobExecutor = JobExecutor.getInstance();
    uiExecutor = UIExecutor.getInstance();
  }

  public ConnectionHandler.ConnectionStatus getCurrentConnectionStatus() {
    return xmpp.getConnectionHandler().getCurrentStatus();
  }

  public String getAccountPhone() {
    LogUtils.e(this, xmpp.getConnection().getUser());
    return xmpp.getConnection().getUser().replaceAll(jid_suffix + "/Smack", "");
  }

  public Exception sendMessage(String phone, String json, String subject) {
    LogUtils.e(this,"send -----" + json);
    Message message = new Message();
    message.setBody(json);
    message.setSubject(subject);
    LogUtils.e(this, message.toXML().toString() );
    return xmpp.sendMessage(phone + jid_suffix, message);
  }

  public void registMessageObserver(MessageObserver messageObserver) {
    xmpp.getmMessageObservable().registObserver(messageObserver);
  }

  public void unRegistMessageObserver(MessageObserver messageObserver){
    xmpp.getmMessageObservable().unregistObserver(messageObserver);
  }

  public void unRegistMessageObservers() {
    xmpp.getmMessageObservable().unregistObservers();
  }

  public static class SingleTon {

    public static XmppHelper xmppHelper = new XmppHelper();
  }

  public static XmppHelper getInstance() {
    return SingleTon.xmppHelper;
  }

  public void initialize(Context context) {
    // 连接服务器
    buildConnect().subscribe(new Subscriber<String>() {
      @Override public void onCompleted() {

      }

      @Override public void onError(Throwable e) {
        e.printStackTrace();
        ToastUtils.show(e.toString());
      }

      @Override public void onNext(String str) {
        ToastUtils.show(str);
      }
    });
  }

  private Observable buildConnect() {
    return Observable.create(new Observable.OnSubscribe<String>() {
      @Override public void call(Subscriber<? super String> subscriber) {
        Exception e = xmpp.connect(serviceName, host);
        if (e == null) {
          subscriber.onNext("Connect Success");
        } else {
          subscriber.onError(e);
        }
        subscriber.onCompleted();
      }
    }).subscribeOn(Schedulers.from(jobExecutor)).observeOn(uiExecutor.getScheduler());
  }

  public Exception login(String phone, String password_plain) {
    return xmpp.login(phone + jid_suffix, password_plain);
  }

  public VCard getVCard() {
    return xmpp.getUserVCard();
  }

  public Exception regist(String phone, String pass) {
    return xmpp.registAccount(phone, pass);
  }

  public Exception setVCard(VCard vCard) {
    return xmpp.setUserVCard(vCard);
  }

  public void disconnect() {
    //xmpp.getmMessageObservable().unregistObservers();
    xmpp.disconnect();
  }
}
