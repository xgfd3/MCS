package graduation.mcs.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author: xucz
 * @date: 2016-3-15
 * @description:	
 *	
 */
public class NetStatueReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if( intent.getAction().equalsIgnoreCase("android.net.conn.CONNECTIVITY_CHANGE") ){

        }
    }

}
