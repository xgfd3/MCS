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
public class FileFragment extends BaseFragment {
  @Bind(R.id.tv_test)
  TextView tv;

  public static FileFragment newInstance(){
    FileFragment fileFragment = new FileFragment();
    return fileFragment;
  }

  @Override protected void initSetting(BaseSetting setting) {
    setting.put(BaseSetting.SettingParam.ContentViewLayoutRes, R.layout.fragment_file);
    setting.put(BaseSetting.SettingParam.ExtralOptionMenuRes, 0);
  }

  @Override protected void initViewsAndEvents(View view) {
    tv.setText(this.toString());
  }

  @Override protected void onViewStatusChange(boolean isVisible, int visibleCount) {}


}
