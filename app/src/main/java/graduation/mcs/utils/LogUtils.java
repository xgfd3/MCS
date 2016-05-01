package graduation.mcs.utils;

import android.util.Log;

/**
 * Created by xucz on 2016/4/14.
 */
public class LogUtils {
  public static boolean DEBUG = true;

  public static void e(Object obj, String msg){
    if(DEBUG) Log.e(obj.getClass().getName(), msg);
  }

  public static void i(Object obj, String msg){
    if(DEBUG) Log.i(obj.getClass().getName(), msg);
  }
}
