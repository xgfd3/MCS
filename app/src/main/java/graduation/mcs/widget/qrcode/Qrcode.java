package graduation.mcs.widget.qrcode;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import graduation.mcs.widget.qrcode.decode.ImageDecoder;
import graduation.mcs.widget.qrcode.encode.EncodeCache;
import graduation.mcs.widget.qrcode.encode.LocalCache;
import graduation.mcs.widget.qrcode.encode.MemoryCache;
import graduation.mcs.widget.qrcode.widgets.ICaptureActivity;

/**
 * @author: xucz
 * @date: 2016-3-23
 * @description:	
 *	用于生成二维码，使用到了内存缓存和本地缓存
 */
public class Qrcode {
    
    private static final String TAG = Qrcode.class.getSimpleName();
    
    private MemoryCache memoryCache;
    private LocalCache localCache;
    private EncodeCache encodeCache;

    private ImageDecoder imageDecoder;

    private static volatile Qrcode qrcode = null;

    /* 私有构造方法，防止被实例化 */  
    private Qrcode(String cachePath){
        memoryCache = new MemoryCache();
        localCache = new LocalCache(memoryCache,cachePath);
        encodeCache = new EncodeCache(memoryCache, localCache);
        
        imageDecoder = new ImageDecoder();
    }

    public static Qrcode getInstance(String cachePath){
        if (qrcode == null) {
            synchronized (Qrcode.class) {
                if (qrcode == null) {
                    qrcode = new Qrcode(cachePath);
                }
            }
        }

        return qrcode;
    }
    
    public void encodeAnddisplay(String data, ImageView container, int dimension){
        // 设置默认显示图片
        //container.setImageResource(R.drawable.def_image);
        
        Bitmap bitmap = null;
        // 从内存中读取
        bitmap = memoryCache.readBitmap(data);
        if(bitmap!=null){
            Log.e(TAG, "从内存中读取");
            container.setImageBitmap(bitmap);
            return;
        }
        
        // 从本地缓存中读取
        bitmap = localCache.readBitmap(data);
        if(bitmap!=null){
            Log.e(TAG, "从本地缓存中读取");
            container.setImageBitmap(bitmap);
            return;
        }
        
        // 生成二维码
        Log.e(TAG, "生成新的二维码");
        encodeCache.display(data, container, dimension);
    }
    
    public void decodeImageUrl(String imageUrl, ICaptureActivity iCaptureActivity){
        imageDecoder.decode(imageUrl, iCaptureActivity);
    }
}
