package graduation.mcs.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.List;

/**
 * Created by xucz on 2016/4/14.
 */
public class VPConAttAdapter extends FragmentPagerAdapter{

  private final List<Fragment> fragments;
  private final String[] pagerTitle;

  public VPConAttAdapter(FragmentManager fm, List<Fragment> fragments, String[] pagerTitle) {
    super(fm);
    this.fragments = fragments;
    this.pagerTitle = pagerTitle;
  }

  @Override public Fragment getItem(int position) {
    return fragments.get(position);
  }

  @Override public int getCount() {
    return fragments.size();
  }

  @Override public CharSequence getPageTitle(int position) {
    return pagerTitle[position];
  }
}
