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

package com.example.administrator.applicationtest2.common.video;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * This object wraps the Camera service object and expects to be the only one
 * talking to it. The implementation encapsulates the steps needed to take
 * preview-sized images, which are used for both preview and decoding.
 * 
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class CameraManager implements MediaRecorder.OnErrorListener{

	private static final String TAG = CameraManager.class.getSimpleName();
	private MediaRecorder mMediaRecorder;
	private final Context context;
	private final CameraConfigurationManager configManager;
	private Camera camera;
	private boolean initialized;
	private boolean previewing;
	/**
	 * Autofocus callbacks arrive here, and are dispatched to the Handler which
	 * requested them.
	 */
	private final AutoFocusCallback autoFocusCallback;
	private File mRecordFile = null;// 文件
	private SurfaceHolder mSurfaceHolder;

	public CameraManager(Context context) {
		this.context = context;
		this.configManager = new CameraConfigurationManager(context);
		autoFocusCallback = new AutoFocusCallback();
	}

	/**
	 * Opens the camera driver and initializes the hardware parameters.
	 * 
	 * @param holder
	 *            The surface object which the camera will draw preview frames
	 *            into.
	 * @throws IOException
	 *             Indicates the camera driver failed to open.
	 */
	public void openDriver(SurfaceHolder holder) throws IOException {
		mSurfaceHolder=holder;
		Camera theCamera = camera;
		if (theCamera == null) {
			theCamera = Camera.open();
			if (theCamera == null) {
				throw new IOException();
			}
			camera = theCamera;
		}
		theCamera.setPreviewDisplay(holder);

		if (!initialized) {
			initialized = true;
			configManager.initFromCameraParameters(theCamera);
		}
		configManager.setDesiredCameraParameters(theCamera);
	}

	/**
	 * Closes the camera driver if still in use.
	 */
	public void closeDriver() {
		if (camera != null) {
			camera.release();
			camera = null;
		}
	}

	/**
	 * Asks the camera hardware to begin drawing preview frames to the screen.
	 */
	public void startPreview() {
		Camera theCamera = camera;
		if (theCamera != null && !previewing) {
			theCamera.startPreview();
			previewing = true;
		}
	}

	/**
	 * Tells the camera to stop drawing preview frames.
	 */
	public void stopPreview() {
		if (camera != null && previewing) {
			camera.stopPreview();
			autoFocusCallback.setHandler(null, 0);
			previewing = false;
		}
	}

	/**
	 * A single preview frame will be returned to the handler supplied. The data
	 * will arrive as byte[] in the message.obj field, with width and height
	 * encoded as message.arg1 and message.arg2, respectively.
	 *
	 * @param handler
	 *            The handler to send the message to.
	 * @param message
	 *            The what field of the message to be sent.
	public void requestPreviewFrame(Handler handler, int message) {
		Camera theCamera = camera;
		if (theCamera != null && previewing) {
			previewCallback.setHandler(handler, message);
			theCamera.setOneShotPreviewCallback(previewCallback);
		}
	}*/

	/**
	 * Asks the camera hardware to perform an autofocus.
	 * 
	 * @param handler
	 *            The Handler to notify when the autofocus completes.
	 * @param message
	 *            The message to deliver.
	 */
	public void requestAutoFocus(Handler handler, int message) {
		if (camera != null && previewing) {
			autoFocusCallback.setHandler(handler, message);
			try {
				camera.autoFocus(autoFocusCallback);
			} catch (RuntimeException re) {
				Log.w(TAG, "Unexpected exception while focusing", re);
			}
		}
	}

	public void openFlashLight() {
		if (camera != null) {
			Parameters parameter = camera.getParameters();
			parameter.setFlashMode(Parameters.FLASH_MODE_TORCH);
			camera.setParameters(parameter);
		}
	}

	public void offFlashLight() {
		if (camera != null) {
			Parameters parameter = camera.getParameters();
			parameter.setFlashMode(Parameters.FLASH_MODE_OFF);
			camera.setParameters(parameter);
		}
	}

	public void switchFlashLight() {
		if (camera != null) {
			Parameters parameter = camera.getParameters();
			if (parameter.getFlashMode().equals(Parameters.FLASH_MODE_TORCH)) {
				parameter.setFlashMode(Parameters.FLASH_MODE_OFF);
			} else {
				parameter.setFlashMode(Parameters.FLASH_MODE_TORCH);
			}

			camera.setParameters(parameter);
		}
	}

    public void takePicture(Camera.PictureCallback callback){
        if (camera!=null){
            Parameters parameters=camera.getParameters();
			List<Camera.Size> supportedPictureSizes = parameters.getSupportedPictureSizes();
			parameters.setPictureSize(1920,1080);
            camera.setParameters(parameters);
            camera.takePicture(null,null,callback);
        }
    }

	//Initialize video system recorder view
	public void initRecord() throws IOException {

		mMediaRecorder = new MediaRecorder();
		mMediaRecorder.reset();
		if (camera != null){
            camera.unlock();
            mMediaRecorder.setCamera(camera);
        }
		mMediaRecorder.setOnErrorListener(this);
		mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
		mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);// 视频源

		mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 音频源

		mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);// 视频输出格式
		mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);// 音频格式
		mMediaRecorder.setVideoSize(800, 480);// 设置分辨率：
		// mMediaRecorder.setVideoFrameRate(16);// 这个我把它去掉了，感觉没什么用
		mMediaRecorder.setVideoEncodingBitRate(1280 * 720);// 设置帧频率，然后就清晰了
		mMediaRecorder.setOrientationHint(90);// 输出旋转90度，保持竖屏录制
		mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);// 视频录制格式
		// mediaRecorder.setMaxDuration(Constant.MAXVEDIOTIME * 1000);
		File sampleDir = context.getExternalFilesDir(Environment.DIRECTORY_MOVIES);
		if (sampleDir==null){
			return;
		}
		String fileName="VIDEO_"+new SimpleDateFormat("yyyyMMDDHHmmss", Locale.CHINA).format(new Date());
		mRecordFile= File.createTempFile(fileName, ".mp4", sampleDir); //mp4格式
		mMediaRecorder.setOutputFile(mRecordFile.getAbsolutePath());
		try {
            mMediaRecorder.prepare();
            mMediaRecorder.start();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}

	public void clearRecord() {
		stopRecord();
		releaseRecord();
	}

	@Override
	public void onError(MediaRecorder mr, int what, int extra) {
		try {
			if (mr != null)
                mr.reset();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}

	private void stopRecord() {
		if (mMediaRecorder != null) {
			// 设置后不会崩
			mMediaRecorder.setOnErrorListener(null);
			try {
				mMediaRecorder.stop();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			}
			mMediaRecorder.setPreviewDisplay(null);
		}
	}
	private void releaseRecord() {
		if (mMediaRecorder != null) {
			mMediaRecorder.setOnErrorListener(null);
			try {
				mMediaRecorder.release();

			} catch (IllegalStateException e) {
				e.printStackTrace();
			}
		}
		mMediaRecorder = null;
	}

	public File getmRecordFile() {
		return mRecordFile;
	}
}
