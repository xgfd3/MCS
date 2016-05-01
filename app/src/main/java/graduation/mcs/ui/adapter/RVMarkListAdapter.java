package graduation.mcs.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import graduation.mcs.R;
import graduation.mcs.dao.Attendance;
import graduation.mcs.utils.TimeUtils;
import java.util.List;

/**
 * Created by xucz on 2016/4/24.
 */
public class RVMarkListAdapter extends RecyclerView.Adapter<RVMarkListAdapter.ViewHolder> {

  private final Context context;
  private final List<Attendance> attendances;

  public RVMarkListAdapter(Context context, List<Attendance> attendances){

    this.context = context;
    this.attendances = attendances;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(View.inflate(context, R.layout.item_mark, null));
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    holder.showData(attendances.get(position));
  }

  @Override public int getItemCount() {
    return attendances.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder{

    @Bind(R.id.mark_item_nickname)
    TextView nickname;

    @Bind(R.id.mark_item_time_sign)
    TextView time;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void showData(Attendance attendance) {
      nickname.setText(attendance.getAccount_nickname());
      time.setText(TimeUtils.secT2DateStrEn(attendance.getTime_sign()));
    }

  }
}
