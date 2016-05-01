package graduation.mcs.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import graduation.mcs.R;
import graduation.mcs.base.BaseLinearLayout;
import graduation.mcs.dao.Conference;
import graduation.mcs.utils.TimeUtils;

/**
 * Created by xucz on 2016/4/15.
 */
public class ConfContentView extends BaseLinearLayout{

  @Bind(R.id.conf_place)
  TextView place;

  @Bind(R.id.time_start)
  TextView time_start;

  @Bind(R.id.time_end)
  TextView time_end;

  @Bind(R.id.conf_open_level)
  TextView open_level;

  @Bind(R.id.conf_attendence_size)
  TextView attendence_size;

  public ConfContentView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void initViewAndEvents() {

  }

  @OnClick(R.id.theme)
  public void changeTheme(View view){
    click(view);
  }
  @OnClick(R.id.place)
  public void changePlace(View view){
    click(view);
  }
  @OnClick(R.id.time_start)
  public void changeStartTime(View view){
    click(view);
  }
  @OnClick(R.id.time_end)
  public void changeEndTime(View view){
    click(view);
  }
  @OnClick(R.id.open)
  public void changeOpen(View view){
    click(view);
  }
  @OnClick(R.id.max_men)
  public void changeMaxMen(View view){
    click(view);
  }
  @OnClick(R.id.special_men)
  public void changeSpecialMen(View view){
    click(view);
  }

  public void updateUI(Conference conference) {
    place.setText(conference.getPlace()!=null?conference.getPlace():"");
    time_start.setText(conference.getTime_begin()!=0? TimeUtils.secT2DateStrEn(
        conference.getTime_begin() ):"---------- --:--");
    time_end.setText(conference.getTime_end()!=0? TimeUtils.secT2DateStrEn(
        conference.getTime_end()):"---------- --:--");
    switch (conference.getOpen_status()){
      case 0:
        open_level.setText(getResources().getString(R.string.open_low));
        break;
      case 1:
        open_level.setText(getResources().getString(R.string.open_middle));
        break;
      case 2:
        open_level.setText(getResources().getString(R.string.open_hight));
        break;
    }
    attendence_size.setText(conference.getAttendance_size()!=null?conference.getAttendance_size() + "":"");
  }
}
