package graduation.mcs.widget.xmpp;

import java.util.ArrayList;
import java.util.List;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.packet.Message;

/**
 * @author: xucz
 * @date: 2016-3-1
 * @description:
 */
public class MessageObservable {

  List<MessageObserver> observers = new ArrayList<MessageObserver>();


  public MessageObservable() {}

  public void registObserver(MessageObserver observer) {
    if (observer == null) {
      throw new NullPointerException("observer == null");
    }
    synchronized (this) {
      if (!observers.contains(observer))
        observers.add(observer);
    }
  }

  public int countObservers() {
    return observers.size();
  }

  public synchronized void unregistObserver(MessageObserver observer) {
    observers.remove(observer);
  }

  public synchronized void unregistObservers() {
    observers.clear();
  }


  @SuppressWarnings("unchecked")
  public void notifyObservers(Chat chat, Message message) {
    int size = 0;
    MessageObserver[] arrays = null;
    synchronized (this) {
        size = observers.size();
        arrays = new MessageObserver[size];
        observers.toArray(arrays);
    }
    if (arrays != null) {
      for (MessageObserver observer : arrays) {
        observer.update(chat, message);
      }
    }
  }

}
