package graduation.mcs.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import butterknife.Bind;
import graduation.mcs.R;
import graduation.mcs.base.BaseLinearLayout;
import graduation.mcs.dao.Conference;

/**
 * Created by xucz on 2016/4/15.
 */
public class ConfView extends BaseLinearLayout {

  @Bind(R.id.view_content)
  ConfContentView contentView;

  @Bind(R.id.view_header)
  ConfHeaderView headerView;

  public ConfContentView getContentView(){
    return this.contentView;
  }
  public ConfHeaderView getHeaderView(){
    return this.headerView;
  }

  public ConfView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void initViewAndEvents() {

  }

  public void updateUI(Conference conference) {
    contentView.updateUI(conference);
    headerView.updateUI(conference);
  }
}
