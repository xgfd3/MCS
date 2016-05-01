package graduation.mcs.widget.qrcode.decode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

public class QrcodeDecoder {
    public static final String BUNDLE_BITMAP = "bitmap";
    public static final String BUNDLE_RESULT = "result";
    
    public static final int BARCODE_MODE = 0X100;
    public static final int QRCODE_MODE = 0X200;
    public static final int ALL_MODE = 0X300;
    
    public static interface DecoderListener{
        void onStart();
        void onFinish(String resultRaw);
    }
    
    private MultiFormatReader multiFormatReader;
    
    public QrcodeDecoder(int decodeMode){
        multiFormatReader = new MultiFormatReader();
        multiFormatReader.setHints(getHints(decodeMode));
    }
    
    
    public String decode(LuminanceSource luSource){
        if( luSource != null ){
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(luSource));
            if(bitmap!=null ){
                try {
                    Result rawResult = multiFormatReader.decodeWithState(bitmap);
                    if( rawResult != null ){
                        return rawResult.getText();
                    }
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }finally{
                    multiFormatReader.reset();
                }
            }
        }
        return null;
    }
    
    
    private Map<DecodeHintType, Object> getHints(int decodeMode) {
        Map<DecodeHintType, Object> hints = new EnumMap<DecodeHintType, Object>(DecodeHintType.class);
        Collection<BarcodeFormat> decodeFormats = new ArrayList<BarcodeFormat>();
        decodeFormats.addAll(EnumSet.of(BarcodeFormat.AZTEC));
        decodeFormats.addAll(EnumSet.of(BarcodeFormat.PDF_417));
        
        switch (decodeMode) {
            case ALL_MODE:
                decodeFormats.addAll(DecodeFormatManager.getBarCodeFormats());
                decodeFormats.addAll(DecodeFormatManager.getQrCodeFormats());
                break;

            case QRCODE_MODE:
                decodeFormats.addAll(DecodeFormatManager.getQrCodeFormats());
                break;

            case BARCODE_MODE:
                decodeFormats.addAll(DecodeFormatManager.getBarCodeFormats());
                break;
        }
        hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);
        return hints;
    }
}
