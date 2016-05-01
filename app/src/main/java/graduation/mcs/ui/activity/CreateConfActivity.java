package graduation.mcs.ui.activity;

import android.os.Bundle;
import android.view.View;
import graduation.mcs.R;
import graduation.mcs.base.BaseActivity;
import graduation.mcs.base.BaseEnsureDialogFragment;
import graduation.mcs.base.BaseSetting;
import graduation.mcs.dao.Conference;
import graduation.mcs.interactor.ConferenceInteractor;
import graduation.mcs.ui.fragment.MaxParterDialogFragment;
import graduation.mcs.ui.fragment.OpenDialogFragment;
import graduation.mcs.ui.fragment.PlaceDialogFragment;
import graduation.mcs.ui.fragment.ThemeDialogFragment;
import graduation.mcs.ui.fragment.TimeDialogFragment;
import graduation.mcs.ui.view.ConfView;
import graduation.mcs.utils.LogUtils;
import graduation.mcs.widget.subscribers.ProgressSubscriber;
import graduation.mcs.widget.subscribers.SubscriberOnNextListener;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by xucz on 2016/4/14.
 *
 * Activity或Fragment作为事件的处理中心
 */
public class CreateConfActivity extends BaseActivity
    implements View.OnClickListener, BaseEnsureDialogFragment.OnEnsureListener {

  private Conference conference;
  private ConfView confView;

  private ConferenceInteractor conferenceInteractor = new ConferenceInteractor();

  @Override protected void initSetting(BaseSetting setting) {
    setting.put(BaseSetting.SettingParam.ContentViewLayoutRes, R.layout.activity_common);
    setting.put(BaseSetting.SettingParam.IsHaveOptionMenu, false);
    setting.put(BaseSetting.SettingParam.OptionMenuRes, 0);
    setting.put(BaseSetting.SettingParam.IsOverridePendingTransition, true);
    setting.put(BaseSetting.SettingParam.OverridePendingTransitionMode, TransitionMode.RIGHT);
    setting.put(BaseSetting.SettingParam.ContainerViewRes, R.layout.view_conf);
  }

  @Override protected void initViewsAndEvents(View containerView) {
    getSupportActionBar().setTitle(R.string.conference_create_title);

    confView = (ConfView) containerView;
    confView.getContentView().setOnClickListener(this);
    confView.getHeaderView().setOnClickListener(this);

    conference =
        new Conference(null, "", "", 0, new Date().getTime(), new Date().getTime(), null, null,
            null, 0, 2, null);
    confView.updateUI(conference);
  }

  @Override protected void onGetBundleExtras(Bundle extras) {

  }

  @Override public void onClick(View view) {
    LogUtils.e(this, "onClick: " + view.toString());
    int id = view.getId();
    switch (id) {
      case R.id.theme:
        ThemeDialogFragment themeDialogFragment = ThemeDialogFragment.newInstance(
            new String[] { conference.getTheme(), conference.getDescription() });
        themeDialogFragment.setEnsureListener(this);
        themeDialogFragment.show(getSupportFragmentManager(), "theme");
        break;
      case R.id.place:
        PlaceDialogFragment placeDialogFragment =
            PlaceDialogFragment.newInstance(conference.getPlace());
        placeDialogFragment.setEnsureListener(this);
        placeDialogFragment.show(getSupportFragmentManager(), "place");
        break;
      case R.id.time_start:
        TimeDialogFragment timeDialogFragment = TimeDialogFragment.newInstance();
        timeDialogFragment.setEnsureListener(this);
        timeDialogFragment.show(getSupportFragmentManager(), "time_start");
        break;
      case R.id.time_end:
        TimeDialogFragment timeDialogFragment2 = TimeDialogFragment.newInstance();
        timeDialogFragment2.setEnsureListener(this);
        timeDialogFragment2.show(getSupportFragmentManager(), "time_end");
        break;
      case R.id.open:
        OpenDialogFragment openDialogFragment =
            OpenDialogFragment.newInstance(conference.getOpen_status());
        openDialogFragment.setEnsureListener(this);
        openDialogFragment.show(getSupportFragmentManager(), "open");
        break;
      case R.id.max_men:
        MaxParterDialogFragment maxParterDialogFragment =
            MaxParterDialogFragment.newInstance(conference.getAttendance_size());
        maxParterDialogFragment.setEnsureListener(this);
        maxParterDialogFragment.show(getSupportFragmentManager(), "max_men");
        break;
      case R.id.special_men:

        break;
      case R.id.iv_avatar:

        break;
      case R.id.fab:
        conferenceInteractor.addConference(conference,
            new ProgressSubscriber(new SubscriberOnNextListener() {
              @Override public void onNext(Object o) {
                CreateConfActivity.this.setResult(0);
                CreateConfActivity.this.finish();
              }
            }, this));
        break;
    }
  }

  @Override public void onDialogOK(String tag, Object object) {
    LogUtils.e(this, "TAG: " + tag + "-----------" + "OBJECT: " + object.toString());
    if (tag.equals("theme")) {
      String[] theme_des = (String[]) object;
      conference.setTheme(theme_des[0]);
      conference.setDescription(theme_des[1]);
    } else if (tag.equals("place")) {
      String place = (String) object;
      conference.setPlace(place);
    } else if (tag.equals("time_start")) {
      Calendar calendar = (Calendar) object;
      conference.setTime_begin(calendar.getTimeInMillis());
    } else if (tag.equals("time_end")) {
      Calendar calendar = (Calendar) object;
      conference.setTime_end(calendar.getTimeInMillis());
    } else if (tag.equals("open")) {
      int level = (int) object;
      conference.setOpen_status(level);
    } else if (tag.equals("max_men")) {
      int count = (int) object;
      conference.setAttendance_size(count);
    }
    confView.updateUI(conference);
  }
}
