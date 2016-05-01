package graduation.mcs.widget.qrcode.encode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

/**
 * @author: xucz
 * @date: 2016-3-23
 * @description:	
 *	
 */
public class LocalCache {
    
    private MemoryCache memoryCache;
    private String localCacheDir;

    public LocalCache(MemoryCache memoryCache, String localCacheDir) {
        this.memoryCache = memoryCache;
        this.localCacheDir = localCacheDir;
    }

    public void cacheBitmap(String data, Bitmap bitmap) {
        try {
            String name = MD5Encoder.encode(data);
            File file = new File(localCacheDir, name);
            
            File parentFile = file.getParentFile();
            if( !parentFile.exists() ){
                parentFile.mkdirs();
            }
            
            bitmap.compress(CompressFormat.JPEG, 100, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap readBitmap(String data) {
        try {
            String name = MD5Encoder.encode(data);
            
            File file = new File(localCacheDir, name);
            if( file.exists() ){
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
                memoryCache.cacheBitmap(data, bitmap);
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
