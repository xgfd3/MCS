package graduation.mcs.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import graduation.mcs.R;
import graduation.mcs.base.BaseLinearLayout;
import graduation.mcs.dao.Account;
import graduation.mcs.utils.TimeUtils;

/**
 * Created by xucz on 2016/4/22.
 */
public class PersonInfoView extends BaseLinearLayout {

  @Bind(R.id.person_info_avatar_iv) ImageView avatar;

  @Bind(R.id.person_info_nickname_tv) TextView nickname;

  @Bind(R.id.person_info_sax_tv) TextView sax;

  @Bind(R.id.person_info_birthday_tv) TextView birthday;

  @Bind(R.id.person_info_realname_tv) TextView realname;

  @Bind(R.id.person_info_workplace_tv) TextView workplace;

  @Bind(R.id.person_info_job_tv) TextView job;

  public PersonInfoView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void initViewAndEvents() {

  }

  public void updateUi(Account account){
    nickname.setText(account.getNickname()!=null?account.getNickname():"");
    sax.setText(account.getSax()!=null?account.getSax():"");
    birthday.setText(account.getBirthday()!=null? TimeUtils.secT2DateStrEn(account.getBirthday()):"");
    realname.setText(account.getReal_name()!=null?account.getReal_name():"");
    workplace.setText(account.getWork_place()!=null?account.getWork_place():"");
    job.setText(account.getJob()!=null?account.getJob():"");
  }

  @OnClick(R.id.person_info_avatar)
  public void avatar(View view){
      click(view);
  }

  @OnClick(R.id.person_info_nickname)
  public void nickname(View view){
      click(view);
  }

  @OnClick(R.id.person_info_sax)
  public void sax(View view){
      click(view);
  }

  @OnClick(R.id.person_info_birthday)
  public void birthday(View view){
      click(view);
  }

  @OnClick(R.id.person_info_realname)
  public void realname(View view){
      click(view);
  }

  @OnClick(R.id.person_info_worksplace)
  public void workplace(View view){
      click(view);
  }

  @OnClick(R.id.person_info_job_tv)
  public void job(View view){
      click(view);
  }

  @OnClick(R.id.person_info_logout)
  public void logout(View view){
    click(view);
  }
}
