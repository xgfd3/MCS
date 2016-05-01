package graduation.mcs.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.ButterKnife;
import graduation.mcs.utils.LogUtils;

/**
 * Created by xucz on 2016/4/16.
 */
public abstract class BaseLinearLayout extends LinearLayout {

  private OnClickListener onClickListener;

  public BaseLinearLayout(Context context) {
    this(context, null);
  }

  public BaseLinearLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
    LogUtils.e(this,"BaseLinearLayout(Context context, AttributeSet attrs)");
  }

  public BaseLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override public void setOnClickListener(OnClickListener l) {
    //super.setOnClickListener(l);
    onClickListener = l;
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);
    LogUtils.e(this, "onFinishInflate");
  }

  @Override protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    LogUtils.e(this, "onAttachedToWindow");
    initViewAndEvents();
  }

  protected abstract void initViewAndEvents();

  @Override protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    LogUtils.e(this, "onDetachedFromWindow");
  }

  protected void click(View view){
    if( onClickListener!=null) onClickListener.onClick(view);
  }
}
