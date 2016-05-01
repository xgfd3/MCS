package graduation.mcs.widget.qrcode.encode;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * @author: xucz
 * @date: 2016-3-23
 * @description:
 * 
 */
@SuppressLint("NewApi")
public class MemoryCache {
    private LruCache<String, Bitmap> bitmapCache;
    
    public MemoryCache() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 8);

        bitmapCache = new LruCache<String, Bitmap>(maxMemory) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    public void cacheBitmap(String data, Bitmap bitmap) {
        bitmapCache.put(data, bitmap);
    }

    public Bitmap readBitmap(String data) {
        return  bitmapCache.get(data);
    }

}
