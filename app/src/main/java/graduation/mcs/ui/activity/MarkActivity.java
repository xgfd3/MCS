package graduation.mcs.ui.activity;

import android.os.Bundle;
import android.view.View;
import graduation.mcs.R;
import graduation.mcs.base.BaseActivity;
import graduation.mcs.base.BaseSetting;
import graduation.mcs.dao.Attendance;
import graduation.mcs.dao.Conference;
import graduation.mcs.interactor.SignInteractor;
import graduation.mcs.ui.view.MarkView;
import graduation.mcs.utils.LogUtils;
import graduation.mcs.widget.xmpp.MessageObserver;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.packet.Message;

/**
 * Created by xucz on 4/19/16.
 */
public class MarkActivity extends BaseActivity {

  private Conference conference;

  private SignInteractor signInteractor;
  private MarkView markView;

  private MessageObserver messageObserver = new MessageObserver() {
    @Override public void update(Chat chat, Message message) {
      if( message.getSubject().equals("1")) {
        LogUtils.e(this, Thread.currentThread().getId() + "------" + message.getFrom() + ":" + message.getBody());
        final Attendance attendance = signInteractor.sendConferenceInfo(chat, message.getBody(), conference);
        if( attendance!=null){
          runOnUiThread(new Runnable() {
            @Override public void run() {
              // 更新attendance到显示列表中
              markView.addAttendance(attendance);
            }
          });
        }
      }
    }
  };

  @Override protected void initSetting(BaseSetting setting) {
    setting.put(BaseSetting.SettingParam.ContentViewLayoutRes, R.layout.activity_common);
    setting.put(BaseSetting.SettingParam.IsHaveOptionMenu, false);
    setting.put(BaseSetting.SettingParam.OptionMenuRes, 0);
    setting.put(BaseSetting.SettingParam.IsOverridePendingTransition, true);
    setting.put(BaseSetting.SettingParam.OverridePendingTransitionMode, TransitionMode.RIGHT);
    setting.put(BaseSetting.SettingParam.ContainerViewRes, R.layout.view_mark);
  }

  @Override protected void initViewsAndEvents(View containerView) {
    getSupportActionBar().setTitle(R.string.conference_item_mark_title);
    markView = (MarkView) containerView;
    signInteractor = new SignInteractor();
    signInteractor.registMessageObserver(messageObserver);
  }

  @Override protected void onGetBundleExtras(Bundle extras) {
    conference = (Conference) extras.getSerializable("conference");
    signInteractor.displayQrcode(conference, markView.getQrcodeView());
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    signInteractor.unRegistMessageObserver(messageObserver);
  }
}
