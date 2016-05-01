package graduation.mcs.widget.xmpp;

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;

import android.util.Log;

/**
 * @author: xucz
 * @date: 2016-3-1
 * @description:
 */
public class ChatHandler implements ChatManagerListener, ChatMessageListener {
  private String TAG = this.getClass().getSimpleName();
  private MessageObservable chatObservable;

  public ChatHandler() {
    this.chatObservable = new MessageObservable();
  }

  public ChatHandler(MessageObservable chatObservable) {
    this.chatObservable = chatObservable;
  }

  public MessageObservable getChatObservable() {
    return chatObservable;
  }

  @Override public void chatCreated(Chat chat, boolean createdLocally) {
    chat.addMessageListener(this);
  }

  @Override public void processMessage(Chat chat, Message message) {
    Log.e(TAG, "processMessage : " + Thread.currentThread().getId());
    Log.e(TAG, message.toXML().toString());
    chatObservable.notifyObservers(chat, message);
  }

}
