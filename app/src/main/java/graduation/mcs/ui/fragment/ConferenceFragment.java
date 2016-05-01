package graduation.mcs.ui.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MenuItem;
import android.view.View;
import graduation.mcs.R;
import graduation.mcs.base.BaseFragment;
import graduation.mcs.base.BaseSetting;
import graduation.mcs.dao.Conference;
import graduation.mcs.interactor.ConferenceInteractor;
import graduation.mcs.ui.activity.CreateConfActivity;
import graduation.mcs.ui.view.ConfListView;
import graduation.mcs.utils.LogUtils;
import java.util.List;
import rx.Subscriber;

/**
 * Created by xucz on 2016/4/13.
 */
public class ConferenceFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
  public static final int REQUEST_CODE_CREATE_CONF = 1;

  private ConfListView confListView;

  private ConferenceInteractor conferenceInteractor = new ConferenceInteractor();

  public static ConferenceFragment newInstance() {
    ConferenceFragment conferenceFragment = new ConferenceFragment();
    return conferenceFragment;
  }
  @Override protected void initSetting(BaseSetting setting) {
    setting.put(BaseSetting.SettingParam.ContentViewLayoutRes, R.layout.fragment_conference);
    setting.put(BaseSetting.SettingParam.ExtralOptionMenuRes, R.menu.option_conference);
  }

  @Override protected void initViewsAndEvents(View view) {
    LogUtils.e(this, "initViewsAndEvents(View view)");
    confListView = (ConfListView) view;
    confListView.setRefreshListener(this);
  }

  @Override protected void onViewStatusChange(boolean isVisible, int visibleCount) {
    LogUtils.e(this, "onViewStatusChange(boolean isVisible, int visibleCount)");
    if( isVisible && visibleCount==1){
      confListView.refresh();
    }
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int itemId = item.getItemId();
    if( itemId == R.id.action_add){
      startActivityForResult(new Intent(getActivity(), CreateConfActivity.class),
          REQUEST_CODE_CREATE_CONF);
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public void onRefresh() {
    conferenceInteractor.getAllConference(new Subscriber<List<Conference>>() {
      @Override public void onStart() {
        super.onStart();
      }

      @Override public void onCompleted() {
        confListView.hideRefresh();
      }

      @Override public void onError(Throwable e) {
        e.printStackTrace();
      }

      @Override public void onNext(List<Conference> conferences) {
        confListView.showDate(conferences);
        LogUtils.e(this, "onNext(List<Conference> conferences)");
      }
    });
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    LogUtils.e(this, "requestCode----------" + requestCode);
    if (requestCode == REQUEST_CODE_CREATE_CONF){
      confListView.refresh();
    }
  }
}
