package graduation.mcs.utils;

import android.widget.Toast;
import graduation.mcs.McsApplication;

/**
 * Created by xucz on 4/18/16.
 */
public class ToastUtils {

  private static Toast mToast;
  public static void show(String msg){
    if (mToast == null) {
      mToast = Toast.makeText(McsApplication.getContext(), msg, Toast.LENGTH_SHORT);
    } else {
      mToast.setText(msg);
    }
    mToast.show();
  }
}
