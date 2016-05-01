package graduation.mcs.base;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import butterknife.Bind;
import graduation.mcs.R;

/**
 * Created by xucz on 2016/4/14.
 */
public abstract  class BaseActivity extends BaseAppCompatActivity{

  @Bind(R.id.common_toolbar)
  protected Toolbar mToolbar;

  @Bind(R.id.common_container)
  FrameLayout mContainer;

  @Override protected BaseSetting getSetting() {
    BaseSetting setting= new BaseSetting();
    setting.put(BaseSetting.SettingParam.IsApplySystemBarTranslucency, true);
    setting.put(BaseSetting.SettingParam.SystemBarTintDrawableRes, R.drawable.sr_primary);
    initSetting(setting);
    return setting;
  }

  @Override
  public void setContentView(int layoutResID) {
    super.setContentView(layoutResID);
    if (null != mToolbar) {
      setSupportActionBar(mToolbar);
      getSupportActionBar().setHomeButtonEnabled(true);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setElevation(0);
    }
    int viewRes = (int)getSetting().get(BaseSetting.SettingParam.ContainerViewRes);
    if (viewRes!=0){
      View view = View.inflate(this, viewRes, null);
      mContainer.addView(view);
      setInitView(view);
    }
  }

  protected abstract void initSetting(BaseSetting setting);

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if( item.getItemId() == android.R.id.home){
      finish();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
