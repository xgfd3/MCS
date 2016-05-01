package graduation.mcs.ui.view;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.ImageView;
import butterknife.Bind;
import graduation.mcs.R;
import graduation.mcs.base.BaseLinearLayout;
import graduation.mcs.dao.Attendance;
import graduation.mcs.ui.adapter.RVMarkListAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xucz on 2016/4/23.
 */
public class MarkView extends BaseLinearLayout{
  @Bind(R.id.mark_qrcode)
  ImageView qrcode;

  @Bind(R.id.mark_recyclerview)
  RecyclerView recyclerView;
  private List<Attendance> attendances;
  private RVMarkListAdapter rvMarkListAdapter;

  public MarkView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void initViewAndEvents() {
    recyclerView.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));
    recyclerView.setHasFixedSize(true);
    recyclerView.setItemAnimator(new DefaultItemAnimator());

    attendances = new ArrayList<>();
    rvMarkListAdapter = new RVMarkListAdapter(getContext(), attendances);
    recyclerView.setAdapter(rvMarkListAdapter);

  }

  public ImageView getQrcodeView() {
    return qrcode;
  }

  public void addAttendance(Attendance attendance){
    attendances.add(0,attendance);
    rvMarkListAdapter.notifyItemInserted(0);
    recyclerView.scrollToPosition(0);
  }


}
