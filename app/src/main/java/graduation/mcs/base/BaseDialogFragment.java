package graduation.mcs.base;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

/**
 * Created by xucz on 2016/4/17.
 */
public abstract class BaseDialogFragment extends BaseLazyDialogFragment {

  @Override protected BaseSetting getSetting() {
    BaseSetting setting = new BaseSetting();
    setting.put(BaseSetting.SettingParam.IsHaveOptionMenu, false);
    setting.put(BaseSetting.SettingParam.OptionMenuRes, 0);
    initSetting(setting);
    return setting;
  }

  protected abstract void initSetting(BaseSetting setting);

  @Override protected void setOptionMenu(Menu menu, MenuInflater inflater) {

  }

  @Override protected void onViewStatusChange(boolean isVisible, int visibleCount) {}

  @Override protected void onGetArguments(Bundle arguments) {

  }
}
