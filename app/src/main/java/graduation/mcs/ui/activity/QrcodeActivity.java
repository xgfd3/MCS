package graduation.mcs.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import graduation.mcs.R;
import graduation.mcs.interactor.SignInteractor;
import graduation.mcs.utils.ToastUtils;
import graduation.mcs.utils.UriUtils;
import graduation.mcs.widget.qrcode.CaptureActivity;
import graduation.mcs.widget.qrcode.decode.QrcodeDecoder;
import graduation.mcs.widget.subscribers.ProgressSubscriber;
import graduation.mcs.widget.subscribers.SubscriberOnNextListener;

/**
 * Created by xucz on 4/19/16.
 */
public class QrcodeActivity extends CaptureActivity {

  private static final int CHOOSE_PIC = 1;

  @Bind(R.id.capture_preview)
  SurfaceView scanPreview;

  @Bind(R.id.capture_crop_view)
  RelativeLayout scanCropview;

  @Bind(R.id.capture_container)
  RelativeLayout scanContainer;

  @Bind(R.id.capture_scan_line)
  ImageView scanLine;

  private SignInteractor signInteractor;

  @Override protected int getContentViewRes() {
    return R.layout.activity_qrcode;
  }

  @Override protected void initViewAndEvent() {
    ButterKnife.bind(this);
    // 扫描线动画
    TranslateAnimation animation = new TranslateAnimation(
        Animation.RELATIVE_TO_PARENT, 0.0f,
        Animation.RELATIVE_TO_PARENT, 0.0f,
        Animation.RELATIVE_TO_PARENT, 0.0f,
        Animation.RELATIVE_TO_PARENT, 0.9f);
    animation.setDuration(3000);
    animation.setRepeatCount(-1);
    animation.setRepeatMode(Animation.RESTART);
    scanLine.startAnimation(animation);

    signInteractor = new SignInteractor();
  }

  @OnClick(R.id.iv_cancel)
  public void cancle(){
    finish();
  }

  @OnCheckedChanged(R.id.cb_torch)
  public void torch(boolean isChecked){
    if (getCameraManager() != null) {
      if (isChecked) {
        getCameraManager().setTorch(true);
      } else {
        getCameraManager().setTorch(false);
      }
    }
  }

  @OnClick(R.id.tv_gallery)
  public void gallery(){
    Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
    intent.addCategory(Intent.CATEGORY_OPENABLE);
    intent.setType("image/*");
    startActivityForResult(intent, CHOOSE_PIC);
  }

  @Override protected View getScanCropView() {
    return scanCropview;
  }

  @Override protected View getScanContainer() {
    return scanContainer;
  }

  @Override protected SurfaceView getScanPreview() {
    return scanPreview;
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    ButterKnife.unbind(this);
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK && requestCode == CHOOSE_PIC) {
      Uri uri = data.getData();
      String path = UriUtils.getPath(this, uri);
      signInteractor.decodeQrcode(path, this);
    }
  }

  @Override protected void handleDecodeBundle(Bundle bundle) {
    String result = (String) bundle.get(QrcodeDecoder.BUNDLE_RESULT);
    ToastUtils.show(result);
    signInteractor.sendQrcodeJson(result, new ProgressSubscriber<Integer>(new SubscriberOnNextListener<Integer>() {
      @Override public void onNext(Integer code) {
        QrcodeActivity.this.finish();
      }
    }, this));
  }
}
