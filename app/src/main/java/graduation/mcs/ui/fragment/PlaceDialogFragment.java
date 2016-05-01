package graduation.mcs.ui.fragment;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import butterknife.Bind;
import graduation.mcs.R;
import graduation.mcs.base.BaseEnsureDialogFragment;

/**
 * Created by xucz on 2016/4/17.
 */
public class PlaceDialogFragment extends BaseEnsureDialogFragment {

  @Bind(R.id.place_content)
  EditText content;

  @Bind(R.id.place_loacation_activity)
  ImageView location;

  public static PlaceDialogFragment newInstance(String data){
    PlaceDialogFragment placeDialogFragment = new PlaceDialogFragment();

    Bundle bundle = new Bundle();
    bundle.putString("data", data);
    placeDialogFragment.setArguments(bundle);
    return placeDialogFragment;
  }

  @Override protected void onGetArguments(Bundle arguments) {
    super.onGetArguments(arguments);
    String data = arguments.getString("data");
    content.setText(data);
  }

  @Override protected String getTitle() {
    return getResources().getString(R.string.dialog_place_title);
  }

  @Override protected int getDialogContainerRes() {
    return R.layout.dialog_place;
  }

  @Override protected void onOK() {
    String data = content.getText().toString();
    getEnsureListener().onDialogOK(getTag(), data);
    dismiss();
  }
}
