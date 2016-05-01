package graduation.mcs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import graduation.mcs.R;
import graduation.mcs.base.BaseActivity;
import graduation.mcs.base.BaseSetting;
import graduation.mcs.dao.Account;
import graduation.mcs.interactor.AccountInteractor;
import graduation.mcs.ui.view.PersonInfoView;
import graduation.mcs.widget.subscribers.ProgressSubscriber;
import graduation.mcs.widget.subscribers.SubscriberOnNextListener;

/**
 * Created by xucz on 2016/4/22.
 */
public class PersonInfoActivity extends BaseActivity implements View.OnClickListener{

  private Account account;
  private PersonInfoView personInfoView;
  private AccountInteractor accountInteractor;

  @Override protected void initSetting(BaseSetting setting) {
    setting.put(BaseSetting.SettingParam.ContentViewLayoutRes, R.layout.activity_common);
    setting.put(BaseSetting.SettingParam.IsHaveOptionMenu, false);
    setting.put(BaseSetting.SettingParam.OptionMenuRes, 0);
    setting.put(BaseSetting.SettingParam.IsOverridePendingTransition, true);
    setting.put(BaseSetting.SettingParam.OverridePendingTransitionMode, TransitionMode.RIGHT);
    setting.put(BaseSetting.SettingParam.ContainerViewRes, R.layout.view_person_info);
  }

  @Override protected void initViewsAndEvents(View containerView) {
    getSupportActionBar().setTitle(R.string.person_info_title);
    personInfoView = (PersonInfoView) containerView;
    personInfoView.setOnClickListener(this);
    accountInteractor = new AccountInteractor();
  }

  @Override protected void onGetBundleExtras(Bundle extras) {
    if( extras!=null && extras.getSerializable("account")!=null){
      account = (Account) extras.getSerializable("account");
      personInfoView.updateUi(account);
    }
  }

  @Override public void onClick(View v) {
    switch (v.getId()){
      case R.id.person_info_logout:
        accountInteractor.logout(new ProgressSubscriber<String>(new SubscriberOnNextListener() {
          @Override public void onNext(Object o) {
            Intent intent = new Intent();
            intent.putExtra("islogout", true);
            setResult(0, intent);
            finish();
          }
        }, this));
        break;
    }
  }
}
