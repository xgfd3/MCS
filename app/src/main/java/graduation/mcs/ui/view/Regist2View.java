package graduation.mcs.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import graduation.mcs.R;
import graduation.mcs.base.BaseLinearLayout;

/**
 * Created by xucz on 2016/4/21.
 */
public class Regist2View extends BaseLinearLayout {

  @Bind(R.id.regist_2_verifycode)
  EditText verifycode;

  @Bind(R.id.regist_2_phone)
  TextView phone;

  public Regist2View(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void initViewAndEvents() {

  }

  public void setPhone(String phoneStr){
    phone.setText("+86" + phoneStr);
  }

  public String getVerifyCode(){
    return verifycode.getText().toString();
  }

  @OnClick(R.id.regist_2_resend_message)
  public void resendMessage(View view){
    click(view);
  }

  @OnClick(R.id.regist_2_next_step)
  public void next(View view){
    click(view);
  }
}
