package graduation.mcs.ui.fragment;

import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import butterknife.Bind;
import graduation.mcs.R;
import graduation.mcs.base.BaseEnsureDialogFragment;
import graduation.mcs.utils.TimeUtils;
import java.util.Calendar;

/**
 * Created by xucz on 2016/4/17.
 */
public class TimeDialogFragment extends BaseEnsureDialogFragment implements
    DatePicker.OnDateChangedListener, TimePicker.OnTimeChangedListener{

  @Bind(R.id.time_date)
  DatePicker datePicker;

  @Bind(R.id.time_time)
  TimePicker timePicker;

  private Calendar mCalendar;

  public static TimeDialogFragment newInstance(){
    TimeDialogFragment timeDialogFragment = new TimeDialogFragment();
    return timeDialogFragment;
  }

  @Override protected String getTitle() {
    return getString(R.string.dialog_time_title);
  }

  @Override protected int getDialogContainerRes() {
    return R.layout.dialog_time;
  }

  @Override protected void initViewsAndEvents(View view) {
    super.initViewsAndEvents(view);
    mCalendar= Calendar.getInstance();
    timePicker.setIs24HourView(true);
    TimeUtils.resizePikcer(datePicker, (int) getResources().getDimension(R.dimen.date_picker_width));
    TimeUtils.resizePikcer(timePicker, (int) getResources().getDimension(R.dimen.time_picker_width));

    datePicker.init(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
        mCalendar.get(Calendar.DAY_OF_MONTH), this);
    timePicker.setCurrentHour(mCalendar.get(Calendar.HOUR_OF_DAY));
    timePicker.setCurrentMinute(mCalendar.get(Calendar.MINUTE));
    timePicker.setOnTimeChangedListener(this);
  }

  @Override protected void onOK() {
    getEnsureListener().onDialogOK(getTag(), mCalendar);
    dismiss();
  }

  /**
   * 为DateTimePicker设置进入时显示的日期和时间
   *
   * @param calendar Calendar对象
   */
  public void setCurrent(Calendar calendar){
    mCalendar = calendar;

    datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH), this);
    timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
    timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
    timePicker.setOnTimeChangedListener(this);
  }
  /**
   * 获取设置后的Calendar对象
   *
   * @return Calendar对象
   */
  public Calendar getCalendar(){
    return mCalendar;
  }

  @Override public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
    mCalendar.set(Calendar.YEAR, year);
    mCalendar.set(Calendar.MONTH, monthOfYear);//这里已经是-1后的月份了
    mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
  }

  @Override public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
    mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
    mCalendar.set(Calendar.MINUTE, minute);
  }
}
