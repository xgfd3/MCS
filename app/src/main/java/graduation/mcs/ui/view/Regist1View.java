package graduation.mcs.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import butterknife.Bind;
import butterknife.OnClick;
import graduation.mcs.R;
import graduation.mcs.base.BaseLinearLayout;

/**
 * Created by xucz on 2016/4/21.
 */
public class Regist1View extends BaseLinearLayout {

  @Bind(R.id.regist_phone) EditText phone;

  public Regist1View(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void initViewAndEvents() {

  }

  public String getPhoneNumber(){
    return phone.getText().toString();
  }

  @OnClick(R.id.regist_get_verifycode)
  public void getVerifyCode(View view){
    click(view);
  }

}
