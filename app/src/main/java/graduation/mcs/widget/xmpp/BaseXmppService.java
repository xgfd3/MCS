package graduation.mcs.widget.xmpp;

import android.app.Service;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/**
 * Created by xucz on 2016/4/20.
 */
public abstract class BaseXmppService extends Service {

  private Looper xmppthreadLooper;
  private Handler jobThreadHandler;

  @Override public void onCreate() {
    super.onCreate();

    HandlerThread xmppthread = new HandlerThread("xmppthread");
    xmppthread.start();

    xmppthreadLooper = xmppthread.getLooper();
    jobThreadHandler = new Handler(xmppthreadLooper);
  }

  @Override public void onDestroy() {
    xmppthreadLooper.quit();
  }

  protected Handler getJobThreadHandler(){
    return this.jobThreadHandler;
  }

}
