package graduation.mcs.widget.xmpp;

import android.util.Log;
import org.jivesoftware.smack.ReconnectionManager;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jivesoftware.smackx.vcardtemp.VCardManager;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;

/**
 * @author: xucz
 * @date: 2016-3-1
 * @description: 与XMPP服务器通信的辅助类
 */
public class XMPP {
  private String TAG = this.getClass().getSimpleName();

  private MessageObservable mMessageObservable; // 消息被观察者

  private ConnectionHandler connectionHandler; // 连接后各种状态的处理者
  private ChatHandler chatHandler; // 发送消息后各种状态的处理者

  private XMPPTCPConnection connection;
  private ChatManager chatManager;    // 消息管理者
  private AccountManager accountManager;  // 账户管理者
  private VCardManager vCardManager;  // 个人详细信息管理者
  private String serviceName;
  private String host;

  //private XMPPBinder xmppBinder;

  /* 私有构造方法，防止被实例化 */
  private XMPP() {
    mMessageObservable = new MessageObservable();
    chatHandler = new ChatHandler(mMessageObservable);
    connectionHandler = new ConnectionHandler();
  }

  /* 此处使用一个内部类来维护单例, 保证线程安全 */
  private static class Singleton {
    private static XMPP instance = new XMPP();
  }

  /* 获取实例 */
  public static XMPP getInstance() {
    return Singleton.instance;
  }

  /**
   * 连接服务器并初始化管理器
   */
  public Exception connect(String serviceName, String host) {
    this.serviceName = serviceName;
    this.host = host;
    connection = ConnectionFactory.create(serviceName, host);
    connection.addConnectionListener(connectionHandler);
    chatManager = ChatManager.getInstanceFor(connection);
    chatManager.addChatListener(chatHandler);
    accountManager = AccountManager.getInstance(connection);
    vCardManager = VCardManager.getInstanceFor(connection);
    try {
      connection.connect();
    } catch (Exception e) {
      e.printStackTrace();
      return e;
    }
    return null;
  }

  /**
   * 注销并断开连接
   */
  public Exception disconnect() {
    if (connection == null || !connection.isConnected()) {
      Log.d(TAG, "connection == null");
    } else {
      try {
        connection.disconnect(new Presence(Presence.Type.available));
        connection.removeConnectionListener(connectionHandler);
        // 默认重连是打开的，当断开时要关闭重连
        ReconnectionManager.getInstanceFor(connection).disableAutomaticReconnection();

        chatManager.removeChatListener(chatHandler);
        connection = null;
        accountManager = null;
        vCardManager = null;
        chatManager = null;
      } catch (NotConnectedException e) {
        e.printStackTrace();
        return e;
      }
    }
    return null;
  }

  /**
   * 注册账号
   *
   * @param phone 用手机号作为账号
   * @param password 密码
   */
  public Exception registAccount(String phone, String password) {
    if (connection == null || !connection.isConnected() ||accountManager == null ) {
      Log.d(TAG, "accountManager == null");
      if (serviceName != null && host != null) {
        connect(serviceName, host);
      }
    }
    try {
      accountManager.createAccount(phone, password);
    } catch (Exception e) {
      e.printStackTrace();
      connection.disconnect();
      return e;
    }
    return null;
  }

  /**
   * 登录
   */
  public Exception login(String username, String password) {
    if (connection == null || !connection.isConnected()) {
      Log.d(TAG, "connection == null");
      if (serviceName != null && host != null) {
        connect(serviceName, host);
      }
    }
    try {
      connection.login(username, password);
    } catch (Exception e) {
      e.printStackTrace();
      connection.disconnect();
      return e;
    }
    return null;
  }

  /**
   * 设置个人详细信息， 在登录成功后使用
   */
  public Exception setUserVCard(VCard vCard) {
    if (vCardManager == null) {
      Log.d(TAG, "vCardManager == null");
    } else {
      try {
        vCardManager.saveVCard(vCard);
      } catch (Exception e) {
        e.printStackTrace();
        return e;
      }
      return null;
    }
    return null;
  }

  /**
   * 获取个人详细信息， 在登录成功后使用
   */
  public VCard getUserVCard() {
    if (vCardManager == null) {
      Log.d(TAG, "vCardManager == null");
    } else {
      try {
        VCard vCard = vCardManager.loadVCard();
        return vCard;
      } catch (SmackException.NoResponseException e) {
        e.printStackTrace();
      } catch (XMPPException.XMPPErrorException e) {
        e.printStackTrace();
      } catch (NotConnectedException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  /**
   * 发送消息
   */
  public Exception sendMessage(String userJID, Message message) {
    if (chatManager == null) {
      Log.d(TAG, "chatManager == null");
    } else {
      Chat chat = chatManager.createChat(userJID);
      try {
        chat.sendMessage(message);
      } catch (NotConnectedException e) {
        // 没连接上就发消息
        // 连接上后突然断开
        e.printStackTrace();
        return e;
      }
      return null;
    }
    return null;
  }

  /**
   * 通过MessageObservable来注册用于接收消息的观察者
   */
  public MessageObservable getmMessageObservable() {
    return this.mMessageObservable;
  }

  /**
   * 通过ConnectionHandler可监听连接状态
   */
  public ConnectionHandler getConnectionHandler() {
    return this.connectionHandler;
  }

  /**
   * 通过ChatHandler可监听消息发送的情况
   */
  public ChatHandler getChatHandler() {
    return this.chatHandler;
  }

  public XMPPTCPConnection getConnection(){return connection;}
}
