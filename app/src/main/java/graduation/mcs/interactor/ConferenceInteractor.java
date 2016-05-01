package graduation.mcs.interactor;

import graduation.mcs.dao.Conference;
import graduation.mcs.dao.ConferenceDao;
import graduation.mcs.helper.DaoHelper;
import graduation.mcs.helper.VerificationHelper;
import graduation.mcs.utils.CommonUtils;
import graduation.mcs.utils.ToastUtils;
import graduation.mcs.widget.executor.JobExecutor;
import graduation.mcs.widget.executor.UIExecutor;
import java.util.Date;
import java.util.List;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by xucz on 4/18/16.
 */
public class ConferenceInteractor {

  private final ConferenceDao conferenceDao;
  private final VerificationHelper verificationHelper;

  public ConferenceInteractor(){
    conferenceDao = DaoHelper.getInstance().getDaoSession().getConferenceDao();
    verificationHelper = VerificationHelper.getInstance();
  }

  public void addConference(final Conference conference, Subscriber subscriber){
    final Conference conference1 = conference;
    if( verificationHelper.verifyNull(conference1.getTheme())){
      ToastUtils.show("the theme must be not null!");
      return;
    }else if(verificationHelper.verifyNull(conference1.getPlace())){
      ToastUtils.show("the place must be not null!");
      return;
    }

    // 是否已登录
    String phone = verificationHelper.verifyIfLogin();
    if( phone == null){
      ToastUtils.show("please check your network and login first!");
      return;
    }

    // uuid, creator, create time
    conference1.setConference_uuid(CommonUtils.uuid());
    conference1.setCreator_account_phone(phone);
    conference1.setTime_create(new Date().getTime());

    Observable.create(new Observable.OnSubscribe<Void>() {
      @Override public void call(Subscriber<? super Void> subscriber) {
        conferenceDao.insert(conference1);
        subscriber.onNext(null);
      }
    }).observeOn(UIExecutor.getInstance().getScheduler())
        .subscribeOn(Schedulers.from(JobExecutor.getInstance())).subscribe(subscriber);
  }

  public void getAllConference(Subscriber<List<Conference>> subscriber){
    // 判断是否有激活账号
    String phone = verificationHelper.verifyActiveAccoutnInDB();
    if( phone == null ){
      ToastUtils.show("please login first");
      subscriber.onNext(null);
      subscriber.onCompleted();
      return;
    }

    final String finalPhone = phone;
    Observable.create(new Observable.OnSubscribe<List<Conference>>() {
      @Override public void call(Subscriber<? super List<Conference>> subscriber) {
        // 获取当前登录账号所创建的会议
        List<Conference> conferences = conferenceDao.queryBuilder().where(
            ConferenceDao.Properties.Creator_account_phone.eq(finalPhone)).list();

        subscriber.onNext(conferences);
        subscriber.onCompleted();
      }
    }).subscribeOn(Schedulers.from(JobExecutor.getInstance()))
        .observeOn(UIExecutor.getInstance().getScheduler())
        .subscribe(subscriber);
  }
}
