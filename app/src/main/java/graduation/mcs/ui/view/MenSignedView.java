package graduation.mcs.ui.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import butterknife.Bind;
import graduation.mcs.R;
import graduation.mcs.base.BaseLinearLayout;
import graduation.mcs.dao.Attendance;
import graduation.mcs.ui.adapter.RVMensignedListAdapter;
import graduation.mcs.widget.recycleview.DividerItemDecoration;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xucz on 2016/4/23.
 */
public class MenSignedView extends BaseLinearLayout {

  @Bind(R.id.men_signed_refresh)
  SwipeRefreshLayout refreshLayout;

  @Bind(R.id.men_signed_recycleview)
  RecyclerView recyclerView;

  private List<Attendance> attendances;
  private RVMensignedListAdapter rvMensignedListAdapter;
  private SwipeRefreshLayout.OnRefreshListener refreshListener;

  public MenSignedView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void initViewAndEvents() {
    refreshLayout.setColorScheme(R.color.color1, R.color.color2, R.color.color3, R.color.color4);

    recyclerView.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    recyclerView.setHasFixedSize(true);
    recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));

    attendances = new ArrayList<>();
    rvMensignedListAdapter = new RVMensignedListAdapter(getContext(), attendances);
    recyclerView.setAdapter(rvMensignedListAdapter);

  }

  public void setRefreshListener(SwipeRefreshLayout.OnRefreshListener refreshListener){
    this.refreshListener = refreshListener;
    refreshLayout.setOnRefreshListener(refreshListener);
  }

  public void showRefresh(){
    refreshLayout.setRefreshing(true);
    if(refreshListener!=null) refreshListener.onRefresh();
  }

  public void hideRefresh(){
    refreshLayout.setRefreshing(false);
  }

  public void showData(List<Attendance> attendances){
    this.attendances.clear();
    if( attendances !=null)this.attendances.addAll(attendances);
    rvMensignedListAdapter.notifyDataSetChanged();
  }

}
