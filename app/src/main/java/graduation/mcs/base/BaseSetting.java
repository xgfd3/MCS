package graduation.mcs.base;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xucz on 2016/4/14.
 */
public class BaseSetting {

  public enum SettingParam{
    // all
    ContentViewLayoutRes,
    IsHaveOptionMenu,
    OptionMenuRes,
    // BaseAppCompatActivity
    IsOverridePendingTransition,
    OverridePendingTransitionMode,
    IsApplySystemBarTranslucency,
    SystemBarTintDrawableRes,
    // BaseActivity
    ContainerViewRes,
    // BaseFragment
    ExtralOptionMenuRes
  }

  private Map<SettingParam, Object> setting = new HashMap<>();

  public void put(SettingParam param, Object value){
    setting.put(param, value);
  }

  public Object get(SettingParam param){
    Object ob = setting.get(param);
    if( ob == null ){
      throw new IllegalArgumentException("\"" + param + "\" setting parameter is not prepared!");
    }
    return ob;
  }

  public void clean(){
    setting.clear();
  }
}
