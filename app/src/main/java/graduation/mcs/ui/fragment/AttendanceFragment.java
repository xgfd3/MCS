package graduation.mcs.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import graduation.mcs.R;
import graduation.mcs.base.BaseFragment;
import graduation.mcs.base.BaseSetting;
import graduation.mcs.dao.ConferenceSigned;
import graduation.mcs.interactor.SignInteractor;
import graduation.mcs.ui.activity.QrcodeActivity;
import graduation.mcs.ui.view.AtteListView;
import graduation.mcs.utils.LogUtils;
import graduation.mcs.utils.ToastUtils;
import graduation.mcs.widget.xmpp.MessageObserver;
import java.util.List;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.packet.Message;
import rx.Subscriber;

/**
 * Created by xucz on 2016/4/13.
 */
public class AttendanceFragment extends BaseFragment implements SignDialogFragment.OnClickListener,
    SwipeRefreshLayout.OnRefreshListener{

  private AtteListView atteListView;

  private SignInteractor signInteractor;

  private MessageObserver messageObserver = new MessageObserver() {
    @Override public void update(Chat chat, Message message) {
      LogUtils.e(this,
          Thread.currentThread().getId() + ":" + message.getSubject() + "---" + message.getBody());
      if( message.getSubject().equals("2")){
        // 成功签到
        if(signInteractor.onSignSuccess(message.getBody())){
          showAlertDialog(R.string.message_dialog_title_success, "签到成功",
              new DialogInterface.OnClickListener() {
                @Override public void onClick(DialogInterface dialog, int which) {
                  ToastUtils.show(Thread.currentThread().getId() + ":签到成功");
                  if(atteListView!=null)atteListView.refresh();
                }
              });
        }

      }else if( message.getSubject().equals("3")){
        // 签到失败
        String error = signInteractor.onSignFailure(message.getBody());

        if(error!=null)showAlertDialog(R.string.message_dialog_title_failure, error, null);
      }
    }
  };

  public static AttendanceFragment newInstance(){
    AttendanceFragment attendanceFragment = new AttendanceFragment();
    return attendanceFragment;
  }

  @Override protected void initSetting(BaseSetting setting) {
    setting.put(BaseSetting.SettingParam.ContentViewLayoutRes, R.layout.fragment_attendence);
    setting.put(BaseSetting.SettingParam.ExtralOptionMenuRes, R.menu.option_attendence);
  }

  @Override protected void initViewsAndEvents(View view) {
    atteListView = (AtteListView) view;
    signInteractor = new SignInteractor();
    signInteractor.registMessageObserver(messageObserver);
    atteListView.setRefreshListener(this);
    LogUtils.e(this, "refreshView---" + atteListView.toString());
    atteListView.refresh();
  }

  @Override protected void onViewStatusChange(boolean isVisible, int visibleCount) {
    if( isVisible && visibleCount == 1){
      if(atteListView!=null)atteListView.refresh();
    }
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if( item.getItemId() == R.id.action_sign ){
      SignDialogFragment signDialogFragment = SignDialogFragment.getInstance();
      signDialogFragment.setOnClickListener(this);
      signDialogFragment.show(getFragmentManager(), "sign_menu");
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public void onClick(View view) {
    switch (view.getId()){
      case R.id.sign_qrcode:
        startActivity(new Intent(getActivity(), QrcodeActivity.class));
        break;
      case R.id.sign_serial:
        break;
    }
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    signInteractor.unRegistMessageObserver(messageObserver);
  }

  private void showAlertDialog(final int titleRes, final String content, final DialogInterface.OnClickListener yesListener){
    AttendanceFragment.this.getActivity().runOnUiThread(new Runnable() {
      @Override public void run() {
        AlertDialog.Builder b = new AlertDialog.Builder(AttendanceFragment.this.getActivity());
        b.setTitle(titleRes);
        b.setMessage(content);
        b.setNegativeButton(R.string.message_dialog_cancle, null);
        b.setPositiveButton(R.string.message_dialog_yes, yesListener);
        b.show();
      }
    });
  }

  @Override public void onRefresh() {
    signInteractor.loadConferenceSigned(new Subscriber<List<ConferenceSigned>>() {
      @Override public void onStart() {
        super.onStart();
      }

      @Override public void onCompleted() {
        atteListView.hideRefresh();
      }

      @Override public void onError(Throwable e) {

      }

      @Override public void onNext(List<ConferenceSigned> conferenceSigneds) {
        atteListView.showDate(conferenceSigneds);
      }
    });
  }
}
