package graduation.mcs.widget.xmpp;

import android.util.Log;
import javax.security.auth.callback.CallbackHandler;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.sasl.SASLMechanism;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.util.ByteUtils;

/**
 * @author: xucz
 * @date: 2016-3-1
 * @description:
 * 
 */
public class ConnectionHandler implements ConnectionListener {
    private String TAG = this.getClass().getSimpleName();

    public enum ConnectionStatus {
        UNCONNECTED, CONNECTED, AUTHENTICATED, CONNECTIONCLOSED, CONNECTIONCLOSEDONERROR, RECONNECTING, RECONNECTIONSUCCESSFUL, RECONNECTIONFAILED;
    }

    private ConnectionStatus currentStatus = ConnectionStatus.UNCONNECTED;

    public ConnectionStatus getCurrentStatus(){
        return this.currentStatus;
    }

    @Override
    public void connected(XMPPConnection connection) {
        currentStatus = ConnectionStatus.CONNECTED;
        Log.e(TAG, "connected : " + Thread.currentThread().getId());
    }

    @Override
    public void authenticated(XMPPConnection connection, boolean resumed) {
        currentStatus = ConnectionStatus.AUTHENTICATED;
        Log.e(TAG, "authenticated : " + Thread.currentThread().getId());
    }

    @Override
    public void connectionClosed() {
        currentStatus = ConnectionStatus.CONNECTIONCLOSED;
        Log.e(TAG, "connectionClosed : " + Thread.currentThread().getId());
    }

    @Override
    public void connectionClosedOnError(Exception e) {
        currentStatus = ConnectionStatus.CONNECTIONCLOSEDONERROR;
        Log.e(TAG, "connectionClosedOnError : "
                + Thread.currentThread().getId());
    }

    @Override
    public void reconnectionSuccessful() {
        currentStatus = ConnectionStatus.RECONNECTIONSUCCESSFUL;
        Log.e(TAG, "reconnectionSuccessful : " + Thread.currentThread().getId());
    }

    @Override
    public void reconnectingIn(int seconds) {
        currentStatus = ConnectionStatus.RECONNECTING;
        Log.e(TAG, "reconnectingIn : " + Thread.currentThread().getId() + "----" + "在" + seconds + "后重连");
    }

    @Override
    public void reconnectionFailed(Exception e) {
        currentStatus = ConnectionStatus.RECONNECTIONFAILED;
        Log.e(TAG, "connected : " + Thread.currentThread().getId());
    }

}
