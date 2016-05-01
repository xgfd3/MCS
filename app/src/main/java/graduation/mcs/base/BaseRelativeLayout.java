package graduation.mcs.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import butterknife.ButterKnife;

/**
 * Created by xucz on 2016/4/16.
 */
public abstract class BaseRelativeLayout extends RelativeLayout{

  private OnClickListener onClickListener;

  public BaseRelativeLayout(Context context) {
    this(context, null);
  }

  public BaseRelativeLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public BaseRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);
  }

  @Override protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    initViewAndEvents();
  }

  protected abstract void initViewAndEvents();

  @Override protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
  }

  @Override public void setOnClickListener(OnClickListener l) {
    //super.setOnClickListener(l);
    onClickListener = l;
  }

  protected void click(View view){
    if( onClickListener!=null) onClickListener.onClick(view);
  }

}
