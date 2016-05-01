package graduation.mcs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import graduation.mcs.R;
import graduation.mcs.base.BaseActivity;
import graduation.mcs.base.BaseSetting;
import graduation.mcs.interactor.AccountInteractor;
import graduation.mcs.ui.view.LoginView;
import graduation.mcs.utils.LogUtils;
import graduation.mcs.widget.subscribers.ProgressSubscriber;
import graduation.mcs.widget.subscribers.SubscriberOnNextListener;

/**
 * Created by xucz on 2016/4/12.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{

  private LoginView loginView;

  private AccountInteractor accountInteractor;

  @Override protected void initSetting(BaseSetting setting) {
    setting.put(BaseSetting.SettingParam.ContentViewLayoutRes, R.layout.activity_common);
    setting.put(BaseSetting.SettingParam.IsHaveOptionMenu, false);
    setting.put(BaseSetting.SettingParam.OptionMenuRes, 0);
    setting.put(BaseSetting.SettingParam.IsOverridePendingTransition, true);
    setting.put(BaseSetting.SettingParam.OverridePendingTransitionMode, TransitionMode.RIGHT);
    setting.put(BaseSetting.SettingParam.ContainerViewRes, R.layout.view_login);
  }

  @Override protected void initViewsAndEvents(View containerView) {
    getSupportActionBar().setTitle(R.string.login_title);
    loginView = (LoginView) containerView;
    loginView.setOnClickListener(this);
    accountInteractor = new AccountInteractor();
  }

  @Override protected void onGetBundleExtras(Bundle extras) {
    LogUtils.e(this, "onGetBundleExtras");
    if( extras!=null && extras.getInt("status") == 2){
      Intent intent = new Intent();
      intent.putExtras(extras);
      setResult(0,intent);
      finish();
    }
  }

  @Override public void onClick(View v) {
    int id = v.getId();
    switch (id){
      case R.id.login_btn_login:
        final String[] usernameAndPassword = loginView.getUsernameAndPassword();
        accountInteractor.login(usernameAndPassword[0], usernameAndPassword[1], new ProgressSubscriber<String>(
            new SubscriberOnNextListener<String>() {
              @Override public void onNext(String str) {
                String nickname = str;
                String [] data = new String[]{usernameAndPassword[0], usernameAndPassword[1], nickname};
                Intent intent = new Intent();
                intent.putExtra("accountinfo", data);
                setResult(0, intent);
                finish();
              }
            }, this));
        break;
      case R.id.login_btn_regist:
        startActivityForResult(new Intent(this, Regist1Activity.class),0);
        break;
    }
  }
}
