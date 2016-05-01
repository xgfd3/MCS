package graduation.mcs.widget.xmpp;

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.packet.Message;

/**
 * @author: xucz
 * @date: 2016-3-1
 * @description:	
 *	
 */
public interface MessageObserver {

    void update(Chat chat, Message message);
    
}
