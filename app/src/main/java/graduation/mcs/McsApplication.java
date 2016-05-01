package graduation.mcs;

import android.app.Application;
import android.content.Context;
import graduation.mcs.base.BaseAppManager;
import graduation.mcs.helper.DaoHelper;
import graduation.mcs.helper.FileHelper;
import graduation.mcs.helper.XmppHelper;

/**
 * Created by xucz on 2016/4/12.
 */
public class McsApplication extends Application {

  private static McsApplication mContext;

  @Override public void onCreate() {
    super.onCreate();
    this.mContext = this;
    DaoHelper.getInstance().initialize(this);
    FileHelper.getInstance().initialize(this);
    XmppHelper.getInstance().initialize(this);
  }

  public static Context getContext() {
    return mContext;
  }

  public void exitApp() {
    XmppHelper.getInstance().unRegistMessageObservers();
    XmppHelper.getInstance().disconnect();
    BaseAppManager.getInstance().clear();
    System.gc();
    android.os.Process.killProcess(android.os.Process.myPid());
  }
}
