package graduation.mcs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import graduation.mcs.R;
import graduation.mcs.base.BaseActivity;
import graduation.mcs.base.BaseSetting;
import graduation.mcs.interactor.AccountInteractor;
import graduation.mcs.ui.view.Regist1View;
import graduation.mcs.widget.subscribers.ProgressSubscriber;
import graduation.mcs.widget.subscribers.SubscriberOnNextListener;

/**
 * Created by xucz on 2016/4/21.
 */
public class Regist1Activity extends BaseActivity implements View.OnClickListener{

  private Regist1View regist1View;

  private AccountInteractor accountInteractor;

  @Override protected void initSetting(BaseSetting setting) {
    setting.put(BaseSetting.SettingParam.ContentViewLayoutRes, R.layout.activity_common);
    setting.put(BaseSetting.SettingParam.IsHaveOptionMenu, false);
    setting.put(BaseSetting.SettingParam.OptionMenuRes, 0);
    setting.put(BaseSetting.SettingParam.IsOverridePendingTransition, false);
    setting.put(BaseSetting.SettingParam.OverridePendingTransitionMode, TransitionMode.RIGHT);
    setting.put(BaseSetting.SettingParam.ContainerViewRes, R.layout.view_regist_1);
  }

  @Override protected void initViewsAndEvents(View containerView) {
    getSupportActionBar().setTitle(R.string.login_regist_1_title);
    regist1View = (Regist1View) containerView;
    regist1View.setOnClickListener(this);
    accountInteractor = new AccountInteractor();
  }

  @Override protected void onGetBundleExtras(Bundle extras) {
    if(extras!=null && extras.getInt("status")==2){
      Intent intent = new Intent();
      intent.putExtras(extras);
      setResult(0, intent);
      finish();
    }
  }

  @Override public void onClick(View v) {
    switch (v.getId()){
      case R.id.regist_get_verifycode:
        accountInteractor.getVerifycode(regist1View.getPhoneNumber(), new ProgressSubscriber<String>(
            new SubscriberOnNextListener<String>() {
              @Override public void onNext(String verifycode) {
                Intent intent = new Intent(Regist1Activity.this, Regist2Activity.class);
                intent.putExtra("verifycode", verifycode);
                intent.putExtra("phone", regist1View.getPhoneNumber());
                intent.putExtra("status", 1);
                startActivityForResult(intent,0);
              }
            }, this));
        break;
    }
  }
}
