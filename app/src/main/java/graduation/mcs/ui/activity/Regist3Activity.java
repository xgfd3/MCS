package graduation.mcs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import graduation.mcs.R;
import graduation.mcs.base.BaseActivity;
import graduation.mcs.base.BaseSetting;
import graduation.mcs.interactor.AccountInteractor;
import graduation.mcs.ui.view.Regist3View;
import graduation.mcs.widget.subscribers.ProgressSubscriber;
import graduation.mcs.widget.subscribers.SubscriberOnNextListener;

/**
 * Created by xucz on 2016/4/21.
 */
public class Regist3Activity extends BaseActivity implements View.OnClickListener{

  private Regist3View regist3View;
  private String phone;
  private AccountInteractor accountInteractor;

  @Override protected void initSetting(BaseSetting setting) {
    setting.put(BaseSetting.SettingParam.ContentViewLayoutRes, R.layout.activity_common);
    setting.put(BaseSetting.SettingParam.IsHaveOptionMenu, false);
    setting.put(BaseSetting.SettingParam.OptionMenuRes, 0);
    setting.put(BaseSetting.SettingParam.IsOverridePendingTransition, false);
    setting.put(BaseSetting.SettingParam.OverridePendingTransitionMode, TransitionMode.RIGHT);
    setting.put(BaseSetting.SettingParam.ContainerViewRes, R.layout.view_regist_3);
  }

  @Override protected void initViewsAndEvents(View containerView) {
    getSupportActionBar().setTitle(R.string.login_regist_3_title);
    regist3View = (Regist3View) containerView;
    regist3View.setOnClickListener(this);

    accountInteractor = new AccountInteractor();
  }

  @Override protected void onGetBundleExtras(Bundle extras) {
    if( extras.getInt("status") == 1){
      phone = extras.getString("phone");
    }
  }

  @Override public void onClick(View v) {
    switch (v.getId()){
      case R.id.regist_3_finish:
        final String[] inputData = regist3View.getInputData();
        accountInteractor.registAndLogin(phone, inputData[0], inputData[1], inputData[2], new ProgressSubscriber<String>(
            new SubscriberOnNextListener() {
              @Override public void onNext(Object o) {
                String [] data = new String[]{ phone, inputData[0], inputData[2]};
                Intent accountInfo = new Intent();
                accountInfo.putExtra("accountinfo", data);
                accountInfo.putExtra("status", 2);
                setResult(0, accountInfo);
                finish();
              }
            }, this));
        break;
    }
  }
}
