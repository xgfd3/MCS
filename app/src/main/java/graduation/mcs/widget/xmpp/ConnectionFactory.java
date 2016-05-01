package graduation.mcs.widget.xmpp;

import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.ReconnectionManager;
import org.jivesoftware.smack.ReconnectionManager.ReconnectionPolicy;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

/**
 * @author: xucz
 * @date: 2016-3-1
 * @description:
 */
public class ConnectionFactory {

  public static XMPPTCPConnection create(String serviceName, String host) {
    XMPPTCPConnectionConfiguration config =
        XMPPTCPConnectionConfiguration.builder().setServiceName(serviceName) // 配置服务器名称
            .setHost(host) // 配置主机名
            .setPort(5222) // 配置端口号
            .setSecurityMode(SecurityMode.disabled) // 配置安全模式
            .setCompressionEnabled(true) // 配置是否启用数据流压缩
            .setConnectTimeout(1000 * 60 * 60 * 24) // 配置连接等待时间为24h，这样在有网络的情况下，服务器一但连通就能连接上
            .setDebuggerEnabled(false)
            .build();
    SASLAuthentication.blacklistSASLMechanism("SCRAM-SHA-1");
    XMPPTCPConnection connection = new XMPPTCPConnection(config);
    // 配置回复包的等待时间为1h
    connection.setPacketReplyTimeout(1000 * 60 * 60);
    ReconnectionManager reconnectionManager = ReconnectionManager.getInstanceFor(connection);
    // 配置重连的方式
    // ReconnectionPolicy.RANDOM_INCREASING_DELAY:
    // 第一个分钟内每隔10秒重试，下五个分钟内第隔一分钟重试，之后第隔五分钟重试
    // ReconnectionPolicy.FIXED_DELAY : 隔一段自定义的时间重试
    reconnectionManager.setReconnectionPolicy(ReconnectionPolicy.RANDOM_INCREASING_DELAY);
    // 配置启用重连
    reconnectionManager.enableAutomaticReconnection();
    return connection;
  }
}
