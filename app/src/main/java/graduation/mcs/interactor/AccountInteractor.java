package graduation.mcs.interactor;

import android.text.TextUtils;
import de.greenrobot.dao.query.QueryBuilder;
import graduation.mcs.dao.Account;
import graduation.mcs.dao.AccountDao;
import graduation.mcs.helper.DaoHelper;
import graduation.mcs.helper.VerificationHelper;
import graduation.mcs.helper.XmppHelper;
import graduation.mcs.utils.LogUtils;
import graduation.mcs.widget.executor.JobExecutor;
import graduation.mcs.widget.executor.UIExecutor;
import graduation.mcs.widget.subscribers.ProgressSubscriber;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by xucz on 2016/4/22.
 */
public class AccountInteractor {

  private final XmppHelper xmppHelper;
  private final VerificationHelper verificationHelper;
  private final JobExecutor jobExecutor;
  private final UIExecutor uiExecutor;
  private final AccountDao accountDao;

  public AccountInteractor() {
    accountDao = DaoHelper.getInstance().getDaoSession().getAccountDao();
    xmppHelper = XmppHelper.getInstance();
    verificationHelper = VerificationHelper.getInstance();
    jobExecutor = JobExecutor.getInstance();
    uiExecutor = UIExecutor.getInstance();
  }

  public void login(String username, String password, ProgressSubscriber<String> subscriber) {
    // 通过正则表达式验证手机号和密码格式是否正确
    LogUtils.e(this, "username: " + username + "------" + "password:" + password);
    buildLogin(username, password).subscribe(subscriber);
  }

  private Observable buildLogin(final String username, final String password) {
    return build(new Observable.OnSubscribe<String>() {
      @Override public void call(Subscriber<? super String> subscriber) {
        // 登录
        Exception e = xmppHelper.login(username, password);
        if (e == null) {
          // 登录成功，获取昵称
          VCard vCard = xmppHelper.getVCard();
          // 将数据写进数据库
          checkAccountToDB(username, password, vCard);
          subscriber.onNext(vCard.getNickName());
        } else {
          subscriber.onError(e);
        }
        subscriber.onCompleted();
      }
    });
  }

  private void checkAccountToDB(String phone, String password, VCard vCard){
    Account account =
        accountDao.queryBuilder().where(AccountDao.Properties.Phone.eq(phone)).unique();
    if( account != null ){
      if(TextUtils.isEmpty(account.getReal_name())){
        account.setStatus(1);
      }else {
        account.setStatus(2);
      }
      accountDao.update(account);
    }else{
      account = new Account();
      account.setPhone(phone);
      account.setPassword_plain(password);
      account.setNickname(vCard.getNickName());

      // 从vCard获取到real_name，sax，birthday，job，work_place，head_img : 图片字节流等信息，并判断是否已完善信息
      account.setStatus(1);
      accountDao.insert(account);
    }
  }

  private void insertAccountToDB(String phone, String password, VCard vCard) {
      Account account = new Account();
      account.setPhone(phone);
      account.setPassword_plain(password);
      account.setNickname(vCard.getNickName());

      // 从vCard获取到real_name，sax，birthday，job，work_place，head_img : 图片字节流等信息，并判断是否已完善信息
      account.setStatus(1);
      accountDao.insert(account);
  }

  public void queryAccountAndLogin(Subscriber<Account> accountProgressSubscriber) {
    buildQueryAndLogin().subscribe(accountProgressSubscriber);
  }

  private Observable buildQueryAndLogin() {
    return build(new Observable.OnSubscribe<Account>() {
      @Override public void call(Subscriber<? super Account> subscriber) {
        QueryBuilder<Account> qb = accountDao.queryBuilder();
        Account account = qb.where(
            qb.and(AccountDao.Properties.Status.notEq(0), AccountDao.Properties.Status.notEq(-1)))
            .unique();
        if (account != null) {
          subscriber.onNext(account);
          Exception e = xmppHelper.login(account.getPhone(), account.getPassword_plain());
          if (e == null) {
          } else {
            subscriber.onError(e);
          }
        } else {
          subscriber.onNext(null);
        }
        subscriber.onCompleted();
      }
    });
  }

  private Observable build(Observable.OnSubscribe onSubscribe) {
    return Observable.create(onSubscribe)
        .subscribeOn(Schedulers.from(jobExecutor))
        .observeOn(uiExecutor.getScheduler());
  }

  public void getVerifycode(String phoneNumber, Subscriber<String> subscriber) {
    // 验证手机号是否符合格式

    // 生成验证码后发送给服务器，发送成功后再调用onNext
    subscriber.onNext("000000");
    subscriber.onCompleted();
  }

  public void registAndLogin(String phone, String pass, String pass2, String nickname,
      Subscriber<String> subscriber) {
    // pass和pass2是否符合格式并且一致

    buildRegistAndLogin(phone, pass, nickname).subscribe(subscriber);
  }

  private Observable buildRegistAndLogin(final String phone, final String pass,
      final String nickname) {
    return build(new Observable.OnSubscribe<String>() {
      @Override public void call(Subscriber<? super String> subscriber) {
        Exception e = xmppHelper.regist(phone, pass);
        if (e == null) {
          Exception e2 = xmppHelper.login(phone, pass);
          if (e2 == null) {
            VCard vCard = new VCard();
            vCard.setNickName(nickname);
            Exception e3 = xmppHelper.setVCard(vCard);
            if (e3 == null) {
              insertAccountToDB(phone, pass, vCard);
              subscriber.onNext(null);
            } else {
              subscriber.onError(e3);
            }
          } else {
            subscriber.onError(e2);
          }
        } else {
          subscriber.onError(e);
        }
        subscriber.onCompleted();
      }
    });
  }

  public void logout(ProgressSubscriber<String> subscriber) {
    buildLogout().subscribe(subscriber);
  }

  private Observable buildLogout() {
    return build(new Observable.OnSubscribe<String>() {
      @Override public void call(Subscriber<? super String> subscriber) {
        xmppHelper.disconnect();
        updateDBAccount();
        subscriber.onNext(null);
        subscriber.onCompleted();
      }
    });
  }

  private void updateDBAccount() {
    QueryBuilder<Account> qb = accountDao.queryBuilder();
    Account account =
        qb.where(qb.or(AccountDao.Properties.Status.eq(1), AccountDao.Properties.Status.eq(2)))
            .unique();
    account.setStatus(0);
    accountDao.update(account);
  }
}
