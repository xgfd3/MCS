package graduation.mcs.ui.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import graduation.mcs.R;
import graduation.mcs.base.BaseRelativeLayout;
import graduation.mcs.dao.Conference;
import graduation.mcs.utils.TimeUtils;

/**
 * Created by xucz on 2016/4/15.
 */
public class ConfHeaderView extends BaseRelativeLayout {

  @Bind(R.id.tv_theme) TextView theme;
  @Bind(R.id.tv_place) TextView place;
  @Bind(R.id.tv_time_start) TextView time_start;
  @Bind(R.id.tv_time_end) TextView time_end;

  public ConfHeaderView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void initViewAndEvents() {
  }

  @OnClick(R.id.fab) public void clickFab(View view) {
    click(view);
  }

  @OnClick(R.id.iv_avatar) public void clickAvatar(View view) {
    click(view);
  }

  public void updateUI(Conference conference) {
    theme.setText(TextUtils.isEmpty(conference.getTheme()) ? getResources().getString(R.string.conference_theme):conference.getTheme());
    place.setText(TextUtils.isEmpty(conference.getPlace()) ? getResources().getString(R.string.conference_place):conference.getPlace());
    time_start.setText(getResources().getString(R.string.conference_time_start) + TimeUtils.secT2DateStrEn(conference.getTime_begin()));
    time_end.setText(getResources().getString(R.string.conference_time_end) + TimeUtils.secT2DateStrEn(conference.getTime_end()));
  }
}
