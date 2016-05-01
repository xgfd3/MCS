package graduation.mcs.base;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import graduation.mcs.R;

/**
 * Created by xucz on 2016/4/14.
 */
public abstract class BaseFragment extends BaseLazyFragment {

  @Override protected void setOptionMenu(Menu menu, MenuInflater inflater) {
    int extralRes = (int)mSetting.get(BaseSetting.SettingParam.ExtralOptionMenuRes);
    if( extralRes != 0)inflater.inflate(extralRes, menu);
  }

  @Override protected BaseSetting getSetting() {
    BaseSetting setting = new BaseSetting();
    setting.put(BaseSetting.SettingParam.IsHaveOptionMenu, true);
    setting.put(BaseSetting.SettingParam.OptionMenuRes,R.menu.option_base);
    initSetting(setting);
    return setting;
  }

  protected abstract void initSetting(BaseSetting setting);

  @Override protected void onGetArgument(Bundle arguments) {

  }
}
