package graduation.mcs.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;

/**
 * Created by xucz on 2016/4/17.
 */
public abstract class BaseLazyDialogFragment extends AppCompatDialogFragment {

  /**
   * context
   */
  protected Context mContext = null;

  private int visibleCount = 0;

  protected BaseSetting mSetting;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // set the dialog style so that the dialog can fix the windows width
    setStyle(STYLE_NO_TITLE,
        android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
    mSetting = getSetting();
    boolean isHaveOptionMenu = (boolean) mSetting.get(BaseSetting.SettingParam.IsHaveOptionMenu);
    setHasOptionsMenu(isHaveOptionMenu);
  }

  protected abstract void onGetArguments(Bundle arguments);

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    
    int viewId = (int) mSetting.get(BaseSetting.SettingParam.ContentViewLayoutRes);
    
    if (viewId != 0) {
      return inflater.inflate(viewId, container, false);
    } else {
      return super.onCreateView(inflater, container, savedInstanceState);
    }
  }

  protected abstract BaseSetting getSetting();


  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);
    Bundle arguments = getArguments();
    if( arguments!=null) onGetArguments(arguments);
    initViewsAndEvents(view);
  }

  @Override
  public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
    if (isVisibleToUser) {
      visibleCount ++;
      onViewStatusChange(true, visibleCount);
    } else {
      onViewStatusChange(false, visibleCount);
    }
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    menu.clear();
    int menuRes = (int)mSetting.get(BaseSetting.SettingParam.OptionMenuRes);
    if( menuRes != 0) inflater.inflate(menuRes, menu);
    setOptionMenu(menu, inflater);
    super.onCreateOptionsMenu(menu, inflater);
  }

  protected abstract void setOptionMenu(Menu menu, MenuInflater inflater);

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    mContext = context;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  /**
   * init all views and add events
   */
  protected abstract void initViewsAndEvents(View view);

  /**
   * called when the view of user changed
   * @param isVisible the mark to identify the view is visible to the user or not
   * @param visibleCount
   */
  protected abstract void onViewStatusChange(boolean isVisible, int visibleCount);

  @Nullable @Override public View getView() {
    return super.getView();
  }
}
