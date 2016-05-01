package graduation.mcs.widget.executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by xucz on 4/10/16.
 */
public class JobExecutor implements Executor {

  private static final int INITIAL_POOL_SIZE = 3;
  private static final int MAX_POOL_SIZE = 5;

  // Sets the amount of time an idle thread waits before terminating
  private static final int KEEP_ALIVE_TIME = 10;

  // Sets the Time Unit to seconds
  private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

  private final BlockingQueue<Runnable> workQueue;

  private final ThreadPoolExecutor threadPoolExecutor;

  private final ThreadFactory threadFactory;

  public JobExecutor(){
    this.workQueue = new LinkedBlockingQueue<>();
    this.threadFactory = new JobThreadFactory();
    this.threadPoolExecutor = new ThreadPoolExecutor(INITIAL_POOL_SIZE, MAX_POOL_SIZE,
        KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, this.workQueue, this.threadFactory);
  }


  public static class Sigleton{
    public static JobExecutor jobExecutor = new JobExecutor();
  }

  public static JobExecutor getInstance(){
    return Sigleton.jobExecutor;
  }

  @Override public void execute(Runnable command) {
    if (command == null) {
      throw new IllegalArgumentException("Runnable to execute cannot be null");
    }
    this.threadPoolExecutor.execute(command);
  }

  private static class JobThreadFactory implements ThreadFactory {
    private static final String THREAD_NAME = "android_";
    private int counter = 0;

    @Override public Thread newThread(Runnable runnable) {
      return new Thread(runnable, THREAD_NAME + counter++);
    }
  }

}
