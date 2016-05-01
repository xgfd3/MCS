package graduation.mcs.ui.fragment;

import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import graduation.mcs.R;
import graduation.mcs.base.BaseFragment;
import graduation.mcs.base.BaseSetting;

/**
 * Created by xucz on 2016/4/14.
 */
public class ContactFragment extends BaseFragment {
  @Bind(R.id.tv_test) TextView tv;

  public static ContactFragment newInstance() {
    ContactFragment manFragment = new ContactFragment();
    return manFragment;
  }
  @Override protected void initSetting(BaseSetting setting) {
    setting.put(BaseSetting.SettingParam.ContentViewLayoutRes, R.layout.fragment_man);
    setting.put(BaseSetting.SettingParam.ExtralOptionMenuRes, 0);
  }

  @Override protected void initViewsAndEvents(View view) {
    tv.setText(this.toString());
  }

  @Override protected void onViewStatusChange(boolean isVisible, int visibleCount) {

  }

}
