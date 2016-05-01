package graduation.mcs.service;

import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import graduation.mcs.widget.xmpp.BaseXmppService;

/**
 * Created by xucz on 2016/4/20.
 */
public class XmppService extends BaseXmppService{

  @Nullable @Override public IBinder onBind(Intent intent) {
    return null;
  }



}
