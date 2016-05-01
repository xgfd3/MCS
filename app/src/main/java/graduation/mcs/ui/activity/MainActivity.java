package graduation.mcs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import butterknife.Bind;
import graduation.mcs.McsApplication;
import graduation.mcs.R;
import graduation.mcs.base.BaseActivity;
import graduation.mcs.base.BaseSetting;
import graduation.mcs.dao.Account;
import graduation.mcs.interactor.AccountInteractor;
import graduation.mcs.ui.fragment.ConfAtteFragment;
import graduation.mcs.ui.fragment.ContactFragment;
import graduation.mcs.ui.fragment.FileFragment;
import graduation.mcs.ui.view.NavHeaderView;
import graduation.mcs.utils.LogUtils;
import graduation.mcs.utils.ToastUtils;
import java.util.ArrayList;
import java.util.List;
import rx.Subscriber;

public class MainActivity extends BaseActivity
    implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
  private static long DOUBLE_CLICK_TIME = 0L;

  @Bind(R.id.drawer_layout) DrawerLayout drawer;

  @Bind(R.id.nav_view) NavigationView navigationView;

  List<Fragment> mFragments;

  private NavHeaderView navHeaderView;
  private AccountInteractor accountInteractor;
  private Account account;
  private ConfAtteFragment confAtteFragment;

  @Override protected void initSetting(BaseSetting setting) {
    setting.put(BaseSetting.SettingParam.ContentViewLayoutRes, R.layout.activity_main);
    setting.put(BaseSetting.SettingParam.IsOverridePendingTransition, false);
    setting.put(BaseSetting.SettingParam.OverridePendingTransitionMode, TransitionMode.NONE);
    setting.put(BaseSetting.SettingParam.IsHaveOptionMenu, false);
    setting.put(BaseSetting.SettingParam.OptionMenuRes, 0);
    setting.put(BaseSetting.SettingParam.ContainerViewRes, 0);
  }

  @Override protected void initViewsAndEvents(View view) {
    ActionBarDrawerToggle toggle =
        new ActionBarDrawerToggle(this, drawer, mToolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();
    navigationView.setNavigationItemSelectedListener(this);

    navHeaderView = (NavHeaderView) navigationView.getHeaderView(0);
    navHeaderView.setOnClickListener(this);

    mFragments = new ArrayList<Fragment>();
    confAtteFragment = ConfAtteFragment.newInstance();
    mFragments.add(confAtteFragment);
    mFragments.add(FileFragment.newInstance());
    mFragments.add(ContactFragment.newInstance());

    switchFragment(0, mFragments);

    // 账号信息初始化
    accountInteractor = new AccountInteractor();
    accountInteractor.queryAccountAndLogin(new Subscriber<Account>() {
      @Override public void onCompleted() {

      }

      @Override public void onError(Throwable e) {
        ToastUtils.show(e.toString());
      }

      @Override public void onNext(Account account) {
        MainActivity.this.account = account;
        if (account != null) {
          navHeaderView.showLoginView(new String[]{account.getPhone(), account.getPassword_plain(), account.getNickname()});
        }else {
          navHeaderView.showUnLoginView();
          drawer.openDrawer(GravityCompat.START);
          Snackbar.make(drawer, getString(R.string.unlogin_remind), Snackbar.LENGTH_LONG)
              .show();
        }
      }
    });

  }

  @Override protected void onGetBundleExtras(Bundle extras) {
    if (extras != null && extras.getStringArray("accountinfo") != null) {
      String[] accountinfo = extras.getStringArray("accountinfo");
      // 显示登录时的侧边栏
      navHeaderView.showLoginView(accountinfo);
      // 更新会议列表
      if(confAtteFragment!=null) confAtteFragment.refreshAtteAndConf();
      if(account==null) account = new Account();
      account.setPhone(accountinfo[0]);
      account.setPassword_plain(accountinfo[1]);
      account.setNickname(accountinfo[2]);
    }else if(extras!=null && extras.getBoolean("islogout")){
      account = new Account();
      navHeaderView.showUnLoginView();
      if(confAtteFragment!=null) confAtteFragment.refreshAtteAndConf();
    }
  }

  @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
      if (drawer.isDrawerOpen(Gravity.LEFT)) {
        drawer.closeDrawer(Gravity.LEFT);
      } else {
        drawer.openDrawer(Gravity.LEFT);
      }
      return true;
    } else if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
      if (drawer.isDrawerOpen(Gravity.LEFT)) {
        drawer.closeDrawer(Gravity.LEFT);
      } else {
        if ((System.currentTimeMillis() - DOUBLE_CLICK_TIME) > 2000) {
          Snackbar.make(drawer, getString(R.string.double_click_exit), Snackbar.LENGTH_SHORT)
              .show();
          DOUBLE_CLICK_TIME = System.currentTimeMillis();
        } else {
          ((McsApplication) getApplication()).exitApp();
        }
      }
      return true;
    }
    return super.onKeyDown(keyCode, event);
  }

  @Override public boolean onNavigationItemSelected(MenuItem item) {
    int id = item.getItemId();

    if (id == R.id.nav_confatte) {
      switchFragment(0, mFragments);
    } else if (id == R.id.nav_file) {
      switchFragment(1, mFragments);
    } else if (id == R.id.nav_contact) {
      switchFragment(2, mFragments);
    }
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  @Override public void onClick(View v) {
    drawer.closeDrawer(GravityCompat.START);
    int id = v.getId();
    switch (id) {
      case R.id.nav_header_avatar:
        if ((boolean) v.getTag()) {
          // 登录后
          Intent intent = new Intent(this, PersonInfoActivity.class);
          intent.putExtra("account",account);
          startActivityForResult(intent,0);
        } else {
          // 未登录
          startActivityForResult(new Intent(this, LoginActivity.class), 0);
        }
        break;
    }
  }

  @Override protected void onRestart() {
    super.onRestart();
    LogUtils.e(this, this.toString() + ": onRestart()");
    String verifycode = getIntent().getStringExtra("verifycode");
    if (verifycode != null) LogUtils.e(this, "verifycod : " + verifycode);
  }
}
