package graduation.mcs.widget.qrcode.decode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import com.google.zxing.RGBLuminanceSource;
import graduation.mcs.widget.qrcode.widgets.ICaptureActivity;
import java.io.ByteArrayOutputStream;

public class ImageDecoder {

    private QrcodeDecoder qrcodeDecoder;

    public ImageDecoder() {
        qrcodeDecoder = new QrcodeDecoder(QrcodeDecoder.ALL_MODE);
    }

    public void decode(String imageUrl, ICaptureActivity iCaptureActivity) {
        new DecodeTask(iCaptureActivity).execute(qrcodeDecoder, imageUrl);
    }

    public class DecodeTask extends AsyncTask<Object, Void, Bundle> {

        private ICaptureActivity iCaptureActivity;

        public DecodeTask(ICaptureActivity iCaptureActivity) {
            this.iCaptureActivity = iCaptureActivity;
        }

        @Override
        protected Bundle doInBackground(Object... arg0) {
            QrcodeDecoder qrcodeDecoder = (QrcodeDecoder) arg0[0];
            String imageUrl = (String) arg0[1];

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeFile(imageUrl, options);
            options.inSampleSize = options.outHeight / 400;
            if (options.inSampleSize <= 0) {
                options.inSampleSize = 1;
            }
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(imageUrl, options);

            if (bitmap != null) {
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                int[] pixels = new int[width * height];
                bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

                RGBLuminanceSource source = new RGBLuminanceSource(width,
                        height, pixels);
                String result = qrcodeDecoder.decode(source);

                Bundle bundle = new Bundle();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
                bundle.putByteArray(QrcodeDecoder.BUNDLE_BITMAP,
                        out.toByteArray());

                bundle.putString(QrcodeDecoder.BUNDLE_RESULT, result);
                return bundle;

            }
            return null;
        }

        @Override
        protected void onPostExecute(Bundle result) {
            if (result != null)
                iCaptureActivity.handleDecode(result);
        }
    }

}
