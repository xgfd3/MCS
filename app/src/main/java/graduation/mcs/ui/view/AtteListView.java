package graduation.mcs.ui.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import butterknife.Bind;
import graduation.mcs.R;
import graduation.mcs.base.BaseLinearLayout;
import graduation.mcs.dao.ConferenceSigned;
import graduation.mcs.ui.adapter.RVAtteListAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xucz on 2016/4/23.
 */
public class AtteListView extends BaseLinearLayout{

  @Bind(R.id.atte_list_refresh) SwipeRefreshLayout refreshLayout;

  @Bind(R.id.atte_list_rv) RecyclerView recyclerView;

  private List<ConferenceSigned> conferences;
  private SwipeRefreshLayout.OnRefreshListener refreshListener;
  private RVAtteListAdapter rvAtteListAdapter;

  public AtteListView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void initViewAndEvents() {
    refreshLayout.setColorScheme(R.color.color1, R.color.color2, R.color.color3, R.color.color4);

    recyclerView.setLayoutManager(
        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    recyclerView.setHasFixedSize(true);

    conferences = new ArrayList<>();
    rvAtteListAdapter = new RVAtteListAdapter(getContext(), conferences);
    recyclerView.setAdapter(rvAtteListAdapter);

  }

  public void setRefreshListener(SwipeRefreshLayout.OnRefreshListener refreshListener){
    this.refreshListener = refreshListener;
    refreshLayout.setOnRefreshListener(refreshListener);
  }

  public void showDate(List<ConferenceSigned> conferences) {
    this.conferences.clear();
    if( conferences!=null)this.conferences.addAll(conferences);
    rvAtteListAdapter.notifyDataSetChanged();
  }

  public void showRefresh(){
    if(!refreshLayout.isRefreshing())refreshLayout.setRefreshing(true);
  }

  public void refresh(){
    showRefresh();
    if( refreshListener != null)refreshListener.onRefresh();
  }

  public void hideRefresh(){
    if(refreshLayout.isRefreshing())refreshLayout.setRefreshing(false);
  }
}
