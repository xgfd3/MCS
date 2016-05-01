package graduation.mcs.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import graduation.mcs.R;
import graduation.mcs.dao.Attendance;
import graduation.mcs.utils.TimeUtils;
import graduation.mcs.utils.ToastUtils;
import java.util.List;

/**
 * Created by xucz on 2016/4/23.
 */
public class RVMensignedListAdapter extends RecyclerView.Adapter<RVMensignedListAdapter.ViewHolder> {

  private final Context context;
  private final List<Attendance> attendances;

  public RVMensignedListAdapter(Context context, List<Attendance> attendances){

    this.context = context;
    this.attendances = attendances;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(View.inflate(context, R.layout.item_men_signed, null));
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    holder.show(attendances.get(position));
  }

  @Override public int getItemCount() {
    return attendances.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder{

    @Bind(R.id.men_item_avatar)
    ImageView avatar;

    @Bind(R.id.men_item_nickname)
    TextView nickname;

    @Bind(R.id.men_item_phone)
    TextView phone;

    @Bind(R.id.men_item_time_sign)
    TextView time;

    @Bind(R.id.men_item_place_sign)
    TextView place;
    private Attendance attendance;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this,itemView);
    }

    public void show(Attendance attendance) {
      this.attendance = attendance;
      nickname.setText(attendance.getAccount_nickname());
      phone.setText(attendance.getAccount_phone());
      time.setText(TimeUtils.secT2DateStrEn(attendance.getTime_sign()));
      place.setText(attendance.getPlace_sign() != null? attendance.getPlace_sign(): "");
    }

    @OnClick(R.id.men_item_plan_btn)
    public void plan(){
      ToastUtils.show("plan:" + attendance.getAccount_nickname());
    }
  }

}
