package graduation.mcs.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import graduation.mcs.R;
import graduation.mcs.base.BaseActivity;
import graduation.mcs.base.BaseSetting;
import graduation.mcs.dao.Attendance;
import graduation.mcs.interactor.MenSignedInteractor;
import graduation.mcs.ui.view.MenSignedView;
import graduation.mcs.utils.LogUtils;
import java.util.List;
import rx.Subscriber;

/**
 * Created by xucz on 2016/4/20.
 */
public class MenSignedActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

  private MenSignedView menSignedView;

  private MenSignedInteractor menSignedInteractor;
  private String uuid;

  @Override protected void initSetting(BaseSetting setting) {
    setting.put(BaseSetting.SettingParam.ContentViewLayoutRes, R.layout.activity_common);
    setting.put(BaseSetting.SettingParam.IsHaveOptionMenu, false);
    setting.put(BaseSetting.SettingParam.OptionMenuRes, 0);
    setting.put(BaseSetting.SettingParam.IsOverridePendingTransition, true);
    setting.put(BaseSetting.SettingParam.OverridePendingTransitionMode, TransitionMode.RIGHT);
    setting.put(BaseSetting.SettingParam.ContainerViewRes, R.layout.view_men_signed);
  }

  @Override protected void initViewsAndEvents(View containerView) {
    getSupportActionBar().setTitle(R.string.men_signed_title);
    menSignedView = (MenSignedView) containerView;
    menSignedView.setRefreshListener(this);
    menSignedInteractor = new MenSignedInteractor();
  }

  @Override protected void onGetBundleExtras(Bundle extras) {
    uuid = extras.getString("conference_uuid");
    LogUtils.e(this, uuid);
    menSignedView.showRefresh();
  }

  @Override public void onRefresh() {
    menSignedInteractor.loadMenSigned(uuid, new Subscriber<List<Attendance>>() {
      @Override public void onCompleted() {

      }

      @Override public void onError(Throwable e) {

      }

      @Override public void onNext(List<Attendance> attendances) {
        menSignedView.showData(attendances);
        menSignedView.hideRefresh();
      }
    });
  }
}
