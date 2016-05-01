package graduation.mcs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import graduation.mcs.R;
import graduation.mcs.base.BaseActivity;
import graduation.mcs.base.BaseSetting;
import graduation.mcs.ui.view.Regist2View;
import graduation.mcs.utils.ToastUtils;

/**
 * Created by xucz on 2016/4/21.
 */
public class Regist2Activity extends BaseActivity implements View.OnClickListener{

  private String verifycode;
  private Regist2View regist2View;
  private String phone;

  @Override protected void initSetting(BaseSetting setting) {
    setting.put(BaseSetting.SettingParam.ContentViewLayoutRes, R.layout.activity_common);
    setting.put(BaseSetting.SettingParam.IsHaveOptionMenu, false);
    setting.put(BaseSetting.SettingParam.OptionMenuRes, 0);
    setting.put(BaseSetting.SettingParam.IsOverridePendingTransition, false);
    setting.put(BaseSetting.SettingParam.OverridePendingTransitionMode, TransitionMode.RIGHT);
    setting.put(BaseSetting.SettingParam.ContainerViewRes, R.layout.view_regist_2);
  }

  @Override protected void initViewsAndEvents(View containerView) {
    getSupportActionBar().setTitle(R.string.login_regist_2_title);

    regist2View = (Regist2View) containerView;
    regist2View.setOnClickListener(this);
  }

  @Override protected void onGetBundleExtras(Bundle extras) {
    if(extras.getInt("status") == 1){
      verifycode = extras.getString("verifycode");
      phone = extras.getString("phone");
      regist2View.setPhone(phone);
    }else if(extras.getInt("status") == 2){
      Intent intent = new Intent();
      intent.putExtras(extras);
      setResult(0,intent);
      finish();
    }
  }

  @Override public void onClick(View v) {
    switch(v.getId()){
      case R.id.regist_2_next_step:
        if( verifycode.equals(regist2View.getVerifyCode())){
          Intent intent = new Intent(this,Regist3Activity.class);
          intent.putExtra("phone", phone);
          intent.putExtra("status", 1);
          startActivityForResult(intent,0);
        }else {
          ToastUtils.show(getString(R.string.regist_remind_verifycode_error));
        }
        break;
      case R.id.regist_2_resend_message:

        break;
    }
  }
}
