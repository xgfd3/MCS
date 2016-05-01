package graduation.mcs.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import graduation.mcs.dao.DaoMaster;
import graduation.mcs.dao.DaoSession;

/**
 * Created by xucz on 2016/4/16.
 */
public class DaoHelper {
  public static final String DB_NAME = "mcs.db";

  private DaoSession daoSession;


  public static class Singleton{
    public static DaoHelper daoHelper = new DaoHelper();
  }

  public static DaoHelper getInstance(){
    return Singleton.daoHelper;
  }

  public void initialize(Context context){
    DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
    SQLiteDatabase db = devOpenHelper.getWritableDatabase();
    DaoMaster daoMaster = new DaoMaster(db);
    daoSession = daoMaster.newSession();
  }

  public DaoSession getDaoSession(){
    return daoSession;
  }
}
