package graduation.mcs.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import graduation.mcs.R;
import graduation.mcs.base.BaseLinearLayout;

/**
 * Created by xucz on 2016/4/21.
 */
public class LoginView extends BaseLinearLayout {

  @Bind(R.id.login_username)
  TextView username;

  @Bind(R.id.login_password)
  TextView password;


  public LoginView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void initViewAndEvents() {

  }

  @OnClick(R.id.login_btn_regist)
  public void regist(View view){
    click(view);
  }

  @OnClick(R.id.login_btn_login)
  public void login(View view){
    click(view);
  }

  public String[] getUsernameAndPassword(){
    return new String[]{username.getText().toString(), password.getText().toString()};
  }
}
