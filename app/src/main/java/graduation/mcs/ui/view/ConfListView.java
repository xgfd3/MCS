package graduation.mcs.ui.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import butterknife.Bind;
import graduation.mcs.R;
import graduation.mcs.base.BaseLinearLayout;
import graduation.mcs.dao.Conference;
import graduation.mcs.ui.adapter.RVConfListAdapter;
import graduation.mcs.utils.LogUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xucz on 4/18/16.
 */
public class ConfListView extends BaseLinearLayout{


  @Bind(R.id.conf_list_refresh)
  SwipeRefreshLayout refreshLayout;

  @Bind(R.id.conf_list_rv)
  RecyclerView recyclerView;

  private List<Conference> conferences;
  private RVConfListAdapter rvConfListAdapter;
  private SwipeRefreshLayout.OnRefreshListener refreshListener;

  public ConfListView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void initViewAndEvents() {
    refreshLayout.setColorScheme(R.color.color1, R.color.color2, R.color.color3, R.color.color4);
    conferences = new ArrayList<>();
    rvConfListAdapter = new RVConfListAdapter(getContext(), conferences);
    recyclerView.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.MATCH_PARENT));
    recyclerView.setAdapter(rvConfListAdapter);
    LogUtils.e(this, "initViewAndEvents");
  }

  public void setRefreshListener(SwipeRefreshLayout.OnRefreshListener refreshListener){
    this.refreshListener = refreshListener;
    refreshLayout.setOnRefreshListener(refreshListener);
  }

  public void showDate(List<Conference> conferences) {
    this.conferences.clear();
    if( conferences!=null)this.conferences.addAll(conferences);
    rvConfListAdapter.notifyDataSetChanged();
  }

  public void showRefresh(){
    if(!refreshLayout.isRefreshing())refreshLayout.setRefreshing(true);
  }

  public void refresh(){
    showRefresh();
    if(refreshListener!=null) refreshListener.onRefresh();
  }

  public void hideRefresh(){
    if(refreshLayout.isRefreshing())refreshLayout.setRefreshing(false);
  }

}
