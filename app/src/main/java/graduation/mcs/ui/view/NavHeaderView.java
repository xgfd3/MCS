package graduation.mcs.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import graduation.mcs.R;
import graduation.mcs.base.BaseRelativeLayout;

/**
 * Created by xucz on 2016/4/15.
 */
public class NavHeaderView  extends BaseRelativeLayout {
  private boolean isLogin = false;

  @Bind(R.id.nav_header_login)
  LinearLayout loginView;

  @Bind(R.id.nav_header_unlogin)
  TextView unLoginTv;

  @Bind(R.id.nav_header_login_nickname)
  TextView nickname;

  @Bind(R.id.nav_header_login_phone)
  TextView phone;

  public NavHeaderView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void initViewAndEvents() {

  }

  public void showUnLoginView() {
    isLogin = false;
    loginView.setVisibility(View.GONE);
    unLoginTv.setVisibility(View.VISIBLE);
  }

  @OnClick(R.id.nav_header_avatar)
  public void clickAvartar(View view){
    view.setTag(isLogin);
    click(view);
  }

  public void showLoginView(String[] accountinfo) {
    isLogin = true;
    loginView.setVisibility(View.VISIBLE);
    unLoginTv.setVisibility(View.GONE);
    nickname.setText(accountinfo[2]);
    phone.setText(accountinfo[0]);
  }
}
