package graduation.mcs.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import butterknife.Bind;
import graduation.mcs.R;
import graduation.mcs.base.BaseEnsureDialogFragment;

/**
 * Created by xucz on 2016/4/17.
 */
public class OpenDialogFragment extends BaseEnsureDialogFragment {
  private String LOW;
  private String MIDDLE;
  private String HIGHT;

  @Bind(R.id.open_seekbar)
  SeekBar seekBar;

  @Bind(R.id.open_seekbar_level)
  TextView level;

  public static OpenDialogFragment newInstance(int data){
    OpenDialogFragment openDialogFragment = new OpenDialogFragment();
    Bundle bundle = new Bundle();
    bundle.putInt("data", data);
    openDialogFragment.setArguments(bundle);
    return openDialogFragment;
  }

  @Override protected void onGetArguments(Bundle arguments) {
    super.onGetArguments(arguments);
    int data = arguments.getInt("data");

    updtateLevelText(data);
    seekBar.setMax(2);
    seekBar.setProgress(data);
  }

  @Override protected String getTitle() {
    return getString(R.string.dialog_open_title);
  }

  @Override protected int getDialogContainerRes() {
    return R.layout.dialog_open;
  }

  @Override protected void initViewsAndEvents(View view) {
    super.initViewsAndEvents(view);
    seekBar.setMax(2);
    updtateLevelText(seekBar.getProgress());
    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        updtateLevelText(progress);
      }

      @Override public void onStartTrackingTouch(SeekBar seekBar) {

      }

      @Override public void onStopTrackingTouch(SeekBar seekBar) {

      }
    });
  }

  private void updtateLevelText(int progress) {
    if( LOW == null)LOW = getString(R.string.open_low);
    if( MIDDLE == null)MIDDLE = getString(R.string.open_middle);
    if( HIGHT == null)HIGHT = getString(R.string.open_hight);
    if(progress == 0){
      level.setText(LOW);
    }else if(progress == 1){
      level.setText(MIDDLE);
    }else if(progress==2){
      level.setText(HIGHT);
    }
  }

  @Override protected void onOK() {
    int level = seekBar.getProgress();
    getEnsureListener().onDialogOK(getTag(), level);
    dismiss();
  }
}
