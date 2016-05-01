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
public class MaxParterDialogFragment extends BaseEnsureDialogFragment{
  public static final int MAX = 1000;

  @Bind(R.id.parter_seekbar_count)
  TextView count;

  @Bind(R.id.parter_seekbar)
  SeekBar seekBar;

  public static MaxParterDialogFragment newInstance(int data){
    MaxParterDialogFragment maxParterDialogFragment = new MaxParterDialogFragment();

    Bundle bundle = new Bundle();
    bundle.putInt("data", data);
    maxParterDialogFragment.setArguments(bundle);
    return maxParterDialogFragment;
  }

  @Override protected void onGetArguments(Bundle arguments) {
    super.onGetArguments(arguments);
    int data = arguments.getInt("data");
    seekBar.setMax(MAX);
    count.setText(String.valueOf(data));
    seekBar.setProgress(data);
  }

  @Override protected String getTitle() {
    return getString(R.string.dialog_max_parter_title);
  }

  @Override protected int getDialogContainerRes() {
    return R.layout.dialog_max_parter;
  }

  @Override protected void initViewsAndEvents(View view) {
    super.initViewsAndEvents(view);
    count.setText(String.valueOf(seekBar.getProgress()));
    seekBar.setMax(MAX);
    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        count.setText(String.valueOf(progress));
      }

      @Override public void onStartTrackingTouch(SeekBar seekBar) {

      }

      @Override public void onStopTrackingTouch(SeekBar seekBar) {

      }
    });

  }

  @Override protected void onOK() {
    int count = seekBar.getProgress();
    getEnsureListener().onDialogOK(getTag(), count);
    dismiss();
  }
}
