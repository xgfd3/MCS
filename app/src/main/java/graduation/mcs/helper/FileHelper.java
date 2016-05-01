package graduation.mcs.helper;

import android.content.Context;
import android.os.Environment;
import java.io.File;

/**
 * Created by xucz on 4/19/16.
 */
public class FileHelper  {

  private Context context;

  public static class Singleton{
    public static FileHelper fileHelper = new FileHelper();
  }

  public static FileHelper getInstance(){
    return Singleton.fileHelper;
  }

  public void initialize(Context context){
    this.context = context;
  }

  public String getQrcodeCachePath() {
    return createCachePath("qrcode");
  }

  private String createCachePath(String cacheName){
    if( isExtralStorageExit() ){
      String packageName = context.getPackageName();
      return Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + packageName + "/" + cacheName;
    }else{
      File dir = context.getDir(cacheName, 0);
      return dir.getAbsolutePath();
    }
  }

  private boolean isExtralStorageExit() {
    if( Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED){
      return true;
    }
    return false;
  }
}
