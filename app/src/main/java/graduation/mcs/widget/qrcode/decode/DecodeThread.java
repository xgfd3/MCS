/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package graduation.mcs.widget.qrcode.decode;

import android.os.Handler;
import android.os.Looper;
import graduation.mcs.widget.qrcode.widgets.ICaptureActivity;
import java.util.concurrent.CountDownLatch;

/**
 * This thread does all the heavy lifting of decoding the images.
 * 
 * @author dswitkin@google.com (Daniel Switkin)
 */
public class DecodeThread extends Thread {

	public static final int BARCODE_MODE = 0X100;
	public static final int QRCODE_MODE = 0X200;
	public static final int ALL_MODE = 0X300;

	private final ICaptureActivity activity;
	private Handler handler;
	private final CountDownLatch handlerInitLatch;

    private int decodeMode;

	public DecodeThread(ICaptureActivity activity, int decodeMode) {

		this.activity = activity;
        this.decodeMode = decodeMode;
		handlerInitLatch = new CountDownLatch(1);

	}

	public Handler getHandler() {
		try {
			handlerInitLatch.await();
		} catch (InterruptedException ie) {
			// continue?
		}
		return handler;
	}

	@Override
	public void run() {
		Looper.prepare();
		handler = new DecodeHandler(activity, decodeMode);
		handlerInitLatch.countDown();
		Looper.loop();
	}

}
