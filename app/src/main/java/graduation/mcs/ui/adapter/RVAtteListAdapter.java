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
import graduation.mcs.dao.ConferenceSigned;
import graduation.mcs.utils.TimeUtils;
import graduation.mcs.utils.ToastUtils;
import java.util.List;

/**
 * Created by xucz on 2016/4/24.
 */
public class RVAtteListAdapter extends RecyclerView.Adapter<RVAtteListAdapter.ViewHolder> {

  private final Context context;
  private final List<ConferenceSigned> conferenceSigneds;

  public RVAtteListAdapter(Context context, List<ConferenceSigned> conferenceSigneds){
    this.context = context;
    this.conferenceSigneds = conferenceSigneds;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(View.inflate(context, R.layout.item_atte, null));
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    holder.showData(conferenceSigneds.get(conferenceSigneds.size() - position-1));
  }

  @Override public int getItemCount() {
    return conferenceSigneds.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder{
    @Bind(R.id.atte_item_avatar)
    ImageView avatar;

    @Bind(R.id.atte_item_theme)
    TextView theme;

    @Bind(R.id.atte_item_place)
    TextView place;

    @Bind(R.id.atte_item_time_start)
    TextView time_start;

    @Bind(R.id.atte_item_time_end)
    TextView time_end;

    String place_pre;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      place_pre = place.getText().toString();
    }

    public void showData(ConferenceSigned conferenceSigned) {
      theme.setText(conferenceSigned.getTheme());
      place.setText(place_pre + "：" + conferenceSigned.getPlace());
      time_start.setText(TimeUtils.secT2DateStrEn(conferenceSigned.getTime_begin()));
      time_end.setText(TimeUtils.secT2DateStrEn(conferenceSigned.getTime_end()));
    }

    @OnClick(R.id.atte_item_personal_plan)
    public void plan(){
      ToastUtils.show("用网页来显示安排，做成混合式应用，暂时不实现");
    }

    @OnClick(R.id.atte_item_men_signed)
    public void menSigned(){
      ToastUtils.show("考虑要不要有这个功能，暂时不实现");
    }
  }
}
