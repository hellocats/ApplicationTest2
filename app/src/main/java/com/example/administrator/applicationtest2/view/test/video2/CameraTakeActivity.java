package com.example.administrator.applicationtest2.view.test.video2;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.applicationtest2.R;
import com.example.administrator.applicationtest2.common.baseClass.BaseClsActivity;
import com.example.administrator.applicationtest2.common.mediautils.VideoProgressBar;
import com.example.administrator.applicationtest2.common.video.CameraManager;
import com.example.administrator.applicationtest2.common.video.CaptureActivityHandler;
import com.example.administrator.applicationtest2.common.video.MessageIDs;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class CameraTakeActivity extends BaseClsActivity implements Callback {

    @BindView(R.id.activity_camera_picture)
    ImageButton activityCameraPicture;
    @BindView(R.id.activity_camera_video)
    VideoProgressBar activityCameraVideo;
    private CaptureActivityHandler handler;
    @BindView(R.id.activity_camera_surfaceview)
    SurfaceView surfaceView;
    @BindView(R.id.activity_camera_longitude)
    TextView longitudeText;
    @BindView(R.id.activity_camera_latitude)
    TextView latitudeText;
    @BindView(R.id.activity_camera_time)
    TextView timeText;
    private MediaPlayer mediaPlayer;
    private boolean hasSurface;
    private String characterSet;
    private boolean playBeep;
    private boolean vibrate;
    CameraManager cameraManager;
    public static final int CAMERA_OK = 2;
    private String activityFrom;
    private Subscription subscription;
    private int mTimeCount;// 时间计数

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        activityFrom = getIntent().getStringExtra("from");
//        if ("PatrolMapActivity".equals(activityFrom)) {
            activityCameraVideo.setVisibility(View.VISIBLE);
            activityCameraVideo.setEnabled(true);
//        }else{
//            activityCameraVideo.setVisibility(View.INVISIBLE);
//            activityCameraVideo.setEnabled(false);
//        }
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        hasSurface = false;
        longitudeText.setText("经度：" );
        latitudeText.setText("纬度：" );
        timeText.setText(String.format(Locale.CHINA, "时间：%tF %tT", System.currentTimeMillis(), System.currentTimeMillis()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        // CameraManager.init(getApplication());
        cameraManager = new CameraManager(getApplication());

        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        hasSurface = false;
        cameraManager.closeDriver();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            cameraManager.openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
        cameraManager.closeDriver();
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public void restartPreviewAfterDelay(long delayMS) {
        if (handler != null) {
            handler.sendEmptyMessageDelayed(MessageIDs.restart_preview, delayMS);
        }
    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            try {
                AssetFileDescriptor fileDescriptor = getAssets().openFd("qrbeep.ogg");
                this.mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(),
                        fileDescriptor.getLength());
                this.mediaPlayer.setVolume(0.1F, 0.1F);
                this.mediaPlayer.prepare();
            } catch (IOException e) {
                this.mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(RESULT_CANCELED);
            finish();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_FOCUS || keyCode == KeyEvent.KEYCODE_CAMERA) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.activity_camera_cancel)
    void onCancel() {
        cameraManager.clearRecord();
        finish();
    }

    @OnClick(R.id.activity_camera_surfaceview)
    void onClickSurface() {
        cameraManager.requestAutoFocus(handler, MessageIDs.auto_focus);
    }

    public class TakePictureCallBack implements Camera.PictureCallback {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            // TODO Auto-generated method stub
            if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                Toast.makeText(CameraTakeActivity.this, "没有存储权限", Toast.LENGTH_SHORT).show();
            }
            File dirs = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "RoadNet");
            if (!dirs.exists() && !dirs.mkdir()) {
                Toast.makeText(CameraTakeActivity.this, "创建目录失败", Toast.LENGTH_SHORT).show();
            }
            String fileName = "IMG_" + new SimpleDateFormat("yyyyMMDDHHmmss", Locale.US).format(new Date()) + ".jpg";
            File newFile = new File(dirs, fileName);
            try {
//                    Logger.d("CameraTakeActivity try block");

                Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
                if (!newFile.exists() && newFile.createNewFile()) {
                    BufferedOutputStream bos
                            = new BufferedOutputStream(new FileOutputStream(newFile));
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                    bos.flush();
                    bos.close();
                }
            } catch (Exception e) {
                //   Logger.d("CameraTakeActivity catch block");
                e.printStackTrace();
                Toast.makeText(CameraTakeActivity.this, "FAILED", Toast.LENGTH_SHORT).show();
            }
            Intent intent = null;
//            if (activityFrom.equalsIgnoreCase("RescueReportStartActivity")) {
//                intent = new Intent(me, RescueReportStartActivity.class);
//            } else if (activityFrom.equalsIgnoreCase("RescueReportOnthewayActivity")) {
//                intent = new Intent(me, RescueReportOnthewayActivity.class);
//            } else if (activityFrom.equalsIgnoreCase("RescueReportObstacleActivity")) {
//                intent = new Intent(me, RescueObstacleActivity.class);
//            } else if (activityFrom.equalsIgnoreCase("RescueUpActivity")) {
//                intent = new Intent(me, RescueUpActivity.class);
//            } else if (activityFrom.equalsIgnoreCase("RescueReorderActivity")) {
//                intent = new Intent(me, RescueReorderActivity.class);
//            } else if (activityFrom.equals("PatrolMapActivity")) {
//                intent = new Intent(me, PatrolMapActivity.class);
//            }
            if (intent != null) {
                intent.putExtra("filePath", newFile.getPath());
                setResult(CAMERA_OK, intent);
            }
            finish();
        }
    }

    @OnClick(R.id.activity_camera_picture)
    void onSubmit() {
        cameraManager.takePicture(new TakePictureCallBack());
    }

    @OnTouch(R.id.activity_camera_video)
    boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            try {
                initCamera(surfaceView.getHolder());
                cameraManager.initRecord();
                mTimeCount = 0;// 时间计数器重新赋值


                subscription = Observable.interval(1000, 1000, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                if (mTimeCount >= 10) {
                                    showToast("finished!");
                                    cameraManager.clearRecord();
                                    setVideoResult();
                                    if (subscription != null && !subscription.isUnsubscribed())
                                        subscription.unsubscribe();
                                    return;
                                }
                                mTimeCount++;
                                activityCameraVideo.setProgress(mTimeCount * 10);
                            }
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (subscription != null && !subscription.isUnsubscribed()) subscription.unsubscribe();
            cameraManager.clearRecord();
            activityCameraVideo.setProgress(0);
            if (mTimeCount > 1) {
                showToast("finished!");
                setVideoResult();
            } else {
                if (cameraManager.getmRecordFile() != null)
                    cameraManager.getmRecordFile().delete();
                showToast("视频录制时间太短");
            }
        }
        return true;
    }

    //Pass video uri result
    private void setVideoResult() {
        Intent intent = null;
//        if (activityFrom.equalsIgnoreCase("P")) {
//            intent = new Intent(me, RescueReportStartActivity.class);
//        } else if (activityFrom.equalsIgnoreCase("PatrolMapActivity")) {
//            intent = new Intent(me, PatrolMapActivity.class);
//        } else if (activityFrom.equalsIgnoreCase("RescueReportObstacleActivity")) {
//            intent = new Intent(me, RescueObstacleActivity.class);
//        } else if (activityFrom.equalsIgnoreCase("RescueUpActivity")) {
//            intent = new Intent(me, RescueUpActivity.class);
//        } else if (activityFrom.equalsIgnoreCase("RescueReorderActivity")) {
//            intent = new Intent(me, RescueReorderActivity.class);
//        }
        if (intent != null) {
            intent.putExtra("filePath", cameraManager.getmRecordFile().getPath());
            intent.putExtra("fileType", "video");
            setResult(CAMERA_OK, intent);
        }
        finish();
    }

}