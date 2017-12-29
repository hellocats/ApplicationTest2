package com.example.administrator.applicationtest2.view.test.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.administrator.applicationtest2.R;
import com.example.administrator.applicationtest2.common.ParamDefine;
import com.example.administrator.applicationtest2.common.ScreenUtils;
import com.example.administrator.applicationtest2.common.baseClass.BaseClsActivity;
import com.example.administrator.applicationtest2.service.LogService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;

/**
 * Created by hepeng on 2017-02-20.
 */
public class Camera extends BaseClsActivity implements SeekBar.OnSeekBarChangeListener {
    private final int TAKE_PICTURE = 1;

    @BindView(R.id.test_imgCamera)
    ImageView imgCamera;
    @BindView(R.id.test_btnCamera)
    Button btnCamera;

    @BindView(R.id.seekbar_height_edt)
    TextView edtSeekbarHeight;
    @BindView(R.id.seekbar_wight_edt)
    TextView edtSeekbarWight;
    @BindView(R.id.seekbar_zoom_edt)
    TextView edtSeekbarZoom;

    @BindView(R.id.seekbar_zoom)
    SeekBar seekbarZoom;
    @BindView(R.id.seekbar_wight)
    SeekBar seekbarWight;
    @BindView(R.id.seekbar_height)
    SeekBar seekbarHeight;

    @BindView(R.id.seekbar_cbEqualZoom)
    CheckBox cbEqualZoom;

    private TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_camera_main);
//        tv.setText("我没初始化");
        init();
        setEvent();
        Intent intent = new Intent(Camera.this,LogService.class);
        startService(intent);
    }

    private void init() {
        seekbarZoom.setMax(100);
        seekbarWight.setMax(ScreenUtils.getScreenWidth(this));
        seekbarHeight.setMax(ScreenUtils.getScreenHeight(this));
    }

    private void setEvent() {
        btnCamera.setOnClickListener(new ClickListner());
        cbEqualZoom.setOnClickListener(new ClickListner());
        //监听器
        seekbarZoom.setOnSeekBarChangeListener(this);
        seekbarWight.setOnSeekBarChangeListener(this);
        seekbarHeight.setOnSeekBarChangeListener(this);
        seekbarZoom.setProgress(90);
        seekbarWight.setProgress(ScreenUtils.getScreenWidth(this));
        seekbarHeight.setProgress(ScreenUtils.getScreenHeight(this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE:
                    Bitmap bitmap = getBitmapFromUrl(ParamDefine.projectDirectory + "/image.jpg",
                            Integer.parseInt(edtSeekbarWight.getText().toString()),
                            Integer.parseInt(edtSeekbarHeight.getText().toString()));
                    saveScalePhoto(bitmap);
                    imgCamera.setImageBitmap(bitmap);
                    break;
            }
        }
    }

    /**
     * 根据路径获取图片资源（已缩放）
     *
     * @param url    图片存储路径
     * @param width  缩放的宽度
     * @param height 缩放的高度
     * @return
     */
    private Bitmap getBitmapFromUrl(String url, double width, double height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 设置了此属性一定要记得将值设置为false
        Bitmap bitmap = BitmapFactory.decodeFile(url);
        // 防止OOM发生
        options.inJustDecodeBounds = false;
        int mWidth = bitmap.getWidth();
        int mHeight = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = 1;
        float scaleHeight = 1;
//        try {
//            ExifInterface exif = new ExifInterface(url);
//            String model = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        // 按照固定宽高进行缩放
        // 这里希望知道照片是横屏拍摄还是竖屏拍摄
        // 因为两种方式宽高不同，缩放效果就会不同
        // 这里用了比较笨的方式
        if (mWidth <= mHeight) {
            scaleWidth = (float) (width / mWidth);
            scaleHeight = (float) (height / mHeight);
        } else {
            scaleWidth = (float) (height / mWidth);
            scaleHeight = (float) (width / mHeight);
        }
//        matrix.postRotate(90); /* 翻转90度 */
        // 按照固定大小对图片进行缩放
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, mWidth, mHeight, matrix, true);
        // 用完了记得回收
        bitmap.recycle();
        return newBitmap;
    }

    /**
     * 存储缩放的图片
     *
     * @param bitmap 图片数据
     */
    private void saveScalePhoto(Bitmap bitmap) {
        // 照片全路径
        String fileName = "";
        // 文件夹路径
        String pathUrl = ParamDefine.projectDirectory + "/";
        String imageName = "imageScale.jpg";
        FileOutputStream fos = null;
        File file = new File(pathUrl);
        file.mkdirs();// 创建文件夹
        fileName = pathUrl + imageName;
        try {
            fos = new FileOutputStream(fileName);
            bitmap.compress(Bitmap.CompressFormat.JPEG, Integer.parseInt(edtSeekbarZoom.getText().toString()), fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Log.i("相机","测试");
        switch (seekBar.getId()) {
            case R.id.seekbar_zoom:
                edtSeekbarZoom.setText(String.valueOf(progress));
                break;
            case R.id.seekbar_wight:
                edtSeekbarWight.setText(String.valueOf(progress));
                if(cbEqualZoom.isChecked()){
                    int height=0;
                    height = Math.round(seekbarHeight.getMax()*progress/seekbarWight.getMax());
                    seekbarHeight.setProgress(height);
                    edtSeekbarHeight.setText(String.valueOf(height));
                }
                break;
            case R.id.seekbar_height:
                edtSeekbarHeight.setText(String.valueOf(progress));
                break;
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private class ClickListner implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.test_btnCamera:
                    try {
                        // 跳转至拍照界面
                        Intent intentPhote = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intentPhote.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                        File out = new File(ParamDefine.projectDirectory + "/image.jpg");
                        Uri uri = Uri.fromFile(out);
                        // 获取拍照后未压缩的原图片，并保存在uri路径中
                        intentPhote.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                intentPhote.putExtra(MediaStore.Images.Media.ORIENTATION, 180);
                        startActivityForResult(intentPhote, TAKE_PICTURE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.seekbar_cbEqualZoom:
                    if(cbEqualZoom.isChecked()){
                        seekbarHeight.setEnabled(false);
                        int current=seekbarWight.getProgress();
                        seekbarWight.setProgress(0);
                        seekbarWight.setProgress(current);
                    }else{
                        seekbarHeight.setEnabled(true);
                    }
                    break;
            }
        }
    }

}
