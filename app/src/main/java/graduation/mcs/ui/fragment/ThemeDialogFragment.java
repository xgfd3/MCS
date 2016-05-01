package graduation.mcs.ui.fragment;

import android.os.Bundle;
import android.widget.TextView;
import butterknife.Bind;
import graduation.mcs.R;
import graduation.mcs.base.BaseEnsureDialogFragment;

/**
 * Created by xucz on 2016/4/17.
 */
public class ThemeDialogFragment extends BaseEnsureDialogFragment {

  @Bind(R.id.theme_content)
  TextView content;

  @Bind(R.id.theme_description)
  TextView description;


  public static ThemeDialogFragment newInstance(String[] data){
    ThemeDialogFragment themeDialogFragment = new ThemeDialogFragment();

    Bundle bundle= new Bundle();
    bundle.putStringArray("data", data);
    themeDialogFragment.setArguments(bundle);
    return themeDialogFragment;
  }

  @Override protected void onGetArguments(Bundle arguments) {
    super.onGetArguments(arguments);
    String[] data = arguments.getStringArray("data");
    content.setText(data[0]);
    description.setText(data[1]);
  }

  @Override protected String getTitle() {
    return getString(R.string.dialog_theme_title);
  }

  @Override protected int getDialogContainerRes() {
    return R.layout.dialog_theme;
  }

  @Override protected void onOK() {
    String[] data = new String[2];
    data[0] = content.getText().toString();
    data[1] = description.getText().toString();
    getEnsureListener().onDialogOK(getTag(), data);
    dismiss();
  }
}
