package graduation.mcs.widget.qrcode.encode;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

public class EncodeCache {
    private MemoryCache memoryCache;
    private LocalCache localCache;
    private QrcodeEncoder qrcodeEncoder;
    
    public EncodeCache(MemoryCache memoryCache, LocalCache localCache) {
        this.memoryCache = memoryCache;
        this.localCache = localCache;
        qrcodeEncoder = new QrcodeEncoder();
    }
    
    public void display(String data, ImageView container, int dimension) {
        new CreateTask().execute(data, container, dimension);
    }
    
    class CreateTask extends AsyncTask<Object, Void, Bitmap>{

        private ImageView container;
        private String data;
        private int dimension;

        @Override
        protected Bitmap doInBackground(Object... params) {
            data = (String) params[0];
            container = (ImageView) params[1];
            dimension = (Integer) params[2];
            
            container.setTag(data);
            
            return qrcodeEncoder.create(data, dimension);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if( result != null ){
                String tag = (String) container.getTag();
                if( tag.equals(data) ){
                    container.setImageBitmap(result);
                    localCache.cacheBitmap(data, result);
                    memoryCache.cacheBitmap(data, result);
                }
            }
        }
    }
    
}
