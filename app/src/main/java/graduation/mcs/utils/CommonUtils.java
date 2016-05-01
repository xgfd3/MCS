package graduation.mcs.utils;

import android.content.Context;
import java.util.UUID;

/**
 * Created by xucz on 2016/4/14.
 */
public class CommonUtils {

  /**
   * 返回一个不重复的字符串
   *
   * @return
   */
  public static String uuid() {
    return UUID.randomUUID().toString().replace("-", "").toUpperCase();
  }

  /**
   * dip 转 px
   *
   * @param context
   * @param dipValue
   * @return
   */
  public static int dip2px(Context context, float dipValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dipValue * scale + 0.5f);
  }

  /**
   * px 转 dip
   *
   * @param context
   * @param pxValue
   * @return
   */
  public static int px2dip(Context context, float pxValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (pxValue / scale + 0.5f);
  }

}
