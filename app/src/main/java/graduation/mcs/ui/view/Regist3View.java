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
public class Regist3View extends BaseLinearLayout {
  @Bind(R.id.regist_3_password) EditText password;

  @Bind(R.id.regist_3_ensure_password) EditText ensurePassword;

  @Bind(R.id.regist_3_nickname) EditText nickname;

  public Regist3View(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void initViewAndEvents() {
    // 限制验证码输入框字数
  }

  public String [] getInputData(){
    return new String[]{password.getText().toString(), ensurePassword.getText().toString(), nickname.getText().toString()};
  }

  @OnClick(R.id.regist_3_finish) public void finish(View view) {
    click(view);
  }
}
