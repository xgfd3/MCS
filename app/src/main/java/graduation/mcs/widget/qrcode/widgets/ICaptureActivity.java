package graduation.mcs.widget.qrcode.widgets;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import graduation.mcs.widget.qrcode.camera.CameraManager;

public interface ICaptureActivity {
    Handler getHandler();
    
    CameraManager getCameraManager();
    
    Rect getCropRect();
    
    void handleDecode(Bundle result);
}
