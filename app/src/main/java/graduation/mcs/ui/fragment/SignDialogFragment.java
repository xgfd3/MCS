package graduation.mcs.ui.fragment;

import android.view.View;
import butterknife.OnClick;
import graduation.mcs.R;
import graduation.mcs.base.BaseDialogFragment;
import graduation.mcs.base.BaseSetting;

/**
 * Created by xucz on 2016/4/20.
 */
public class SignDialogFragment extends BaseDialogFragment {

  private OnClickListener onClickListener;

  public interface OnClickListener{
    void onClick(View view);
  }

  public void setOnClickListener(OnClickListener onClickListener){
    this.onClickListener = onClickListener;
  }

  public static SignDialogFragment getInstance(){
    SignDialogFragment signDialogFragment = new SignDialogFragment();
    return signDialogFragment;
  }

  @Override protected void initSetting(BaseSetting setting) {
    setting.put(BaseSetting.SettingParam.ContentViewLayoutRes, R.layout.dialog_sign);
  }

  @Override protected void initViewsAndEvents(View view) {

  }

  @OnClick(R.id.sign_qrcode)
  public void clickQrcode(View view){
    dismiss();
    if(onClickListener!=null) onClickListener.onClick(view);
  }

  @OnClick(R.id.sign_serial)
  public void clickSerial(View view){
    dismiss();
    if(onClickListener!=null) onClickListener.onClick(view);
  }
}
