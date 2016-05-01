package graduation.mcs.base;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import butterknife.ButterKnife;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import graduation.mcs.R;
import java.util.List;

/**
 * Created by xucz on 2016/4/12.
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity {

  /**
   * overridePendingTransition mode
   */
  public enum TransitionMode {
    LEFT, RIGHT, TOP, BOTTOM, SCALE, FADE, NONE
  }

  private View initView;

  protected BaseSetting mSetting;

  protected void setInitView(View initView) {
    this.initView = initView;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    mSetting = getSetting();

    if ((boolean) mSetting.get(BaseSetting.SettingParam.IsOverridePendingTransition)) {
      switch ((TransitionMode) mSetting.get(
          BaseSetting.SettingParam.OverridePendingTransitionMode)) {
        case LEFT:
          overridePendingTransition(R.anim.left_in, R.anim.left_out);
          break;
        case RIGHT:
          overridePendingTransition(R.anim.right_in, R.anim.right_out);
          break;
        case TOP:
          overridePendingTransition(R.anim.top_in, R.anim.top_out);
          break;
        case BOTTOM:
          overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out);
          break;
        case SCALE:
          overridePendingTransition(R.anim.scale_in, R.anim.scale_out);
          break;
        case FADE:
          overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
          break;
      }
    }
    super.onCreate(savedInstanceState);
    BaseAppManager.getInstance().addActivity(this);

    setTranslucentStatus(
        (boolean) mSetting.get(BaseSetting.SettingParam.IsApplySystemBarTranslucency));
    int drawRes = (int) mSetting.get(BaseSetting.SettingParam.SystemBarTintDrawableRes);
    if (drawRes != 0) {
      setSystemBarTintDrawable(getResources().getDrawable(drawRes));
    }

    int viewId = (int) mSetting.get(BaseSetting.SettingParam.ContentViewLayoutRes);
    if (viewId != 0) {
      setContentView(viewId);
    } else {
      throw new IllegalArgumentException("You must return a right contentView layout resource Id");
    }

    initViewsAndEvents(initView);

    Bundle extras = getIntent().getExtras();
    onGetBundleExtras(extras);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    boolean isHaveOptionMenu = (boolean) mSetting.get(BaseSetting.SettingParam.IsHaveOptionMenu);
    if (isHaveOptionMenu) {
      int menuRes = (int) mSetting.get(BaseSetting.SettingParam.OptionMenuRes);
      if (menuRes != 0) getMenuInflater().inflate(menuRes, menu);

      return true;
    } else {
      menu.clear();
    }
    return super.onCreateOptionsMenu(menu);
  }

  protected abstract BaseSetting getSetting();

  @Override public void finish() {
    super.finish();
    BaseAppManager.getInstance().removeActivity(this);
    if ((boolean) mSetting.get(BaseSetting.SettingParam.IsOverridePendingTransition)) {
      switch ((TransitionMode) mSetting.get(
          BaseSetting.SettingParam.OverridePendingTransitionMode)) {
        case LEFT:
          overridePendingTransition(R.anim.left_in, R.anim.left_out);
          break;
        case RIGHT:
          overridePendingTransition(R.anim.right_in, R.anim.right_out);
          break;
        case TOP:
          overridePendingTransition(R.anim.top_in, R.anim.top_out);
          break;
        case BOTTOM:
          overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out);
          break;
        case SCALE:
          overridePendingTransition(R.anim.scale_in, R.anim.scale_out);
          break;
        case FADE:
          overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
          break;
      }
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    ButterKnife.unbind(this);
  }

  @Override public void setContentView(int layoutResID) {
    super.setContentView(layoutResID);
    ButterKnife.bind(this);
  }

  /**
   * init all views and add events
   */
  protected abstract void initViewsAndEvents(View containerView);

  /**
   * get bundle data
   */
  protected abstract void onGetBundleExtras(Bundle extras);

  /**
   * set status bar translucency
   */
  protected void setTranslucentStatus(boolean on) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      Window win = getWindow();
      WindowManager.LayoutParams winParams = win.getAttributes();
      final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
      if (on) {
        winParams.flags |= bits;
      } else {
        winParams.flags &= ~bits;
      }
      win.setAttributes(winParams);
    }
  }

  /**
   * use SytemBarTintManager
   */
  protected void setSystemBarTintDrawable(Drawable tintDrawable) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      SystemBarTintManager mTintManager = new SystemBarTintManager(this);
      if (tintDrawable != null) {
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setTintDrawable(tintDrawable);
      } else {
        mTintManager.setStatusBarTintEnabled(false);
        mTintManager.setTintDrawable(null);
      }
    }
  }

  protected void addFragment(int containerViewId, Fragment fragment) {
    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
    fragmentTransaction.add(containerViewId, fragment);
    fragmentTransaction.commit();
  }

  private Fragment mCurrentFrgment = null;

  protected void switchFragment(int index, List<Fragment> fragments) {
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

    if (null != mCurrentFrgment) {
      ft.hide(mCurrentFrgment);
    }
    Fragment fragment =
        getSupportFragmentManager().findFragmentByTag(fragments.get(index).getClass().getName());

    if (null == fragment) {
      fragment = fragments.get(index);
    }
    mCurrentFrgment = fragment;

    if (!fragment.isAdded()) {
      ft.add(R.id.common_container, fragment, fragment.getClass().getName());
    } else {
      ft.show(fragment);
    }
    ft.commit();
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if( data == null){
      onGetBundleExtras(new Bundle());
    }else {
      onGetBundleExtras(data.getExtras());
    }
  }
}
