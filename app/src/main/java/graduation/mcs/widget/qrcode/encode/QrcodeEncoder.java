package graduation.mcs.widget.qrcode.encode;

import java.util.EnumMap;
import java.util.Map;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

/**
 * @author: xucz
 * @date: 2016-3-23
 * @description:	
 *	
 */
public class QrcodeEncoder {
    
    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;
    
    public QrcodeEncoder() {
        
    }


    public Bitmap create(String data,int dimension) {
        String contentsToEncode = data;
        if (contentsToEncode == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contentsToEncode);
        if (encoding != null) {
            hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        BitMatrix result = null;
        try {
            result = new MultiFormatWriter().encode(contentsToEncode, BarcodeFormat.QR_CODE,
                    dimension, dimension, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
        
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        
        return bitmap;
    }
    
    
    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }

}
