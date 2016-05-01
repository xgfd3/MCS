package graduation.mcs.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.Bind;
import graduation.mcs.R;
import graduation.mcs.base.BaseFragment;
import graduation.mcs.base.BaseSetting;
import graduation.mcs.ui.adapter.VPConAttAdapter;
import graduation.mcs.widget.smarttab.SmartTabLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xucz on 2016/4/14.
 */
public class ConfAtteFragment extends BaseFragment {

  @Bind(R.id.stl_conatt) SmartTabLayout mSmarkTab;

  @Bind(R.id.vp_conatt) ViewPager mViewPager;
  private AttendanceFragment attendanceFragment;
  private ConferenceFragment conferenceFragment;

  public static ConfAtteFragment newInstance() {
    ConfAtteFragment conAttFragment = new ConfAtteFragment();
    return conAttFragment;
  }

  @Override protected void initSetting(BaseSetting setting) {
    setting.put(BaseSetting.SettingParam.ContentViewLayoutRes, R.layout.fragment_conatt);
    setting.put(BaseSetting.SettingParam.IsHaveOptionMenu, false);
    setting.put(BaseSetting.SettingParam.ExtralOptionMenuRes, 0);
  }

  @Override protected void initViewsAndEvents(View view) {
    List<Fragment> fragments = new ArrayList<>();
    attendanceFragment = AttendanceFragment.newInstance();
    conferenceFragment = ConferenceFragment.newInstance();
    fragments.add(attendanceFragment);
    fragments.add(conferenceFragment);

    String[] title = getResources().getStringArray(R.array.title_conatt);

    VPConAttAdapter vpConAttAdapter =
        new VPConAttAdapter(getActivity().getSupportFragmentManager(), fragments, title);
    mViewPager.setAdapter(vpConAttAdapter);
    mSmarkTab.setCustomTabView(R.layout.custom_tab, 0);
    mSmarkTab.setViewPager(mViewPager);
    mSmarkTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

      }

      @Override public void onPageSelected(int position) {

      }

      @Override public void onPageScrollStateChanged(int state) {

      }
    });
  }

  @Override protected void onViewStatusChange(boolean isVisible, int visibleCount) {

  }

  public void refreshAtteAndConf() {
    attendanceFragment.onRefresh();
    conferenceFragment.onRefresh();
  }
}
