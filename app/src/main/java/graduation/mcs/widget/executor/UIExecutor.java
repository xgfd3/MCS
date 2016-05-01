package graduation.mcs.widget.executor;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by xucz on 4/10/16.
 */
public class UIExecutor{
  public UIExecutor(){}

  public static class SingleTon{
    public static UIExecutor uiExecutor = new UIExecutor();
  }

  public static UIExecutor getInstance(){
    return SingleTon.uiExecutor;
  }

  public Scheduler getScheduler() {
    return AndroidSchedulers.mainThread();
  }
}
