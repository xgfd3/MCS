package graduation.mcs.helper;

import android.text.TextUtils;
import graduation.mcs.dao.Account;
import graduation.mcs.dao.AccountDao;
import graduation.mcs.widget.xmpp.ConnectionHandler;

/**
 * Created by xucz on 4/17/16.
 */
public class VerificationHelper {

  private final AccountDao accountDao;
  private final XmppHelper xmppHelper;

  private VerificationHelper(){
    accountDao = DaoHelper.getInstance().getDaoSession().getAccountDao();
    xmppHelper = XmppHelper.getInstance();
  }

  public String verifyActiveAccoutnInDB() {
    Account account = accountDao.queryBuilder()
        .where(accountDao.queryBuilder()
            .or(AccountDao.Properties.Status.eq(1), AccountDao.Properties.Status.eq(2)))
        .unique();
    return account == null? null : account.getPhone();
  }

  public String verifyIfLogin() {
    if( xmppHelper.getCurrentConnectionStatus() == ConnectionHandler.ConnectionStatus.AUTHENTICATED){
      return xmppHelper.getAccountPhone();
    }
    return null;
  }

  public static class SingleTon{
    public static VerificationHelper verificationHelper = new VerificationHelper();
  }

  public static VerificationHelper getInstance(){
    return SingleTon.verificationHelper;
  }

  public boolean verifyNull(String str){
    return TextUtils.isEmpty(str);
  }

}
