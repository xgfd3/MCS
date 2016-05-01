package graduation.mcs.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import graduation.mcs.R;
import graduation.mcs.dao.Conference;
import graduation.mcs.ui.activity.MarkActivity;
import graduation.mcs.ui.activity.MenSignedActivity;
import graduation.mcs.utils.LogUtils;
import graduation.mcs.utils.TimeUtils;
import java.util.List;

/**
 * Created by xucz on 4/18/16.
 */
public class RVConfListAdapter extends RecyclerView.Adapter<RVConfListAdapter.ViewHolder> {

  private Context context;
  private List<Conference> conferences;

  public RVConfListAdapter(Context context, List<Conference> conferences) {
    this.context = context;
    this.conferences = conferences;
  }

  @Override public RVConfListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(View.inflate(context, R.layout.item_conf, null));
  }

  @Override public void onBindViewHolder(RVConfListAdapter.ViewHolder holder, int position) {
    holder.showData(conferences.get(conferences.size() - position - 1));
  }

  @Override public int getItemCount() {
    return conferences.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.conf_item_theme) TextView theme;

    @Bind(R.id.conf_item_avatar) ImageView avatar;

    @Bind(R.id.conf_item_place) TextView place;

    @Bind(R.id.conf_item_time_start)
    TextView time_start;

    @Bind(R.id.conf_item_time_end)
    TextView time_end;

    String place_pre;
    private Conference conference;

    public ViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, itemView);
      place_pre = place.getText().toString();
    }

    public void showData(Conference conference) {
      this.conference = conference;
      theme.setText(conference.getTheme());
      place.setText(place_pre + "：" + conference.getPlace());
      time_start.setText(TimeUtils.secT2DateStrEn(conference.getTime_begin()));
      time_end.setText(TimeUtils.secT2DateStrEn(conference.getTime_end()));
    }

    @OnClick(R.id.conf_item_mark) public void clickMark() {
      LogUtils.e(this, "clickMark()");
      Intent intent = new Intent(context, MarkActivity.class);
      intent.putExtra("conference", conference);
      context.startActivity(intent);
    }

    @OnClick(R.id.conf_item_men_signed) public void clickMenSigned() {
      LogUtils.e(this, "clickMenSigned()");
      Intent intent = new Intent(context, MenSignedActivity.class);
      intent.putExtra("conference_uuid", conference.getConference_uuid());
      context.startActivity(intent);
    }
  }
}
