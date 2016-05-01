package graduation.mcs.base;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import graduation.mcs.R;

/**
 * Created by xucz on 2016/4/17.
 */
public abstract class BaseEnsureDialogFragment extends BaseDialogFragment {

  private OnEnsureListener ensureListener;

  public interface OnEnsureListener{
    void onDialogOK(String tag, Object object);
  }

  @Bind(R.id.dialog_title)
  TextView title;

  @Override protected void initSetting(BaseSetting setting) {
    setting.put(BaseSetting.SettingParam.ContentViewLayoutRes, R.layout.dialog_ensure_base);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    // 先在view中添加上container的视图，以便在ButteKnif绑定时能够找到containerView里的视图
    if(getDialogContainerRes()!=0){
      View inflate = View.inflate(mContext, getDialogContainerRes(), null);
      ((FrameLayout)view.findViewById(R.id.dialog_container)).addView(inflate, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
    }
    super.onViewCreated(view, savedInstanceState);
  }

  @Override protected void initViewsAndEvents(View view) {
    title.setText(getTitle());
  }

  protected abstract String getTitle();

  protected abstract int getDialogContainerRes();

  @OnClick(R.id.dialog_no)
  public void cancle(){
    dismiss();
  }

  @OnClick(R.id.dialog_ok)
  public void ok(){
    onOK();
  }

  protected abstract void onOK();

  protected OnEnsureListener getEnsureListener(){
    return ensureListener;
  }

  public void setEnsureListener(OnEnsureListener ensureListener){
    this.ensureListener = ensureListener;
  }
}
