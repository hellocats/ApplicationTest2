package com.example.administrator.applicationtest2.view.test.video;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.administrator.applicationtest2.R;
import com.example.administrator.applicationtest2.common.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 短视频播放

 */

public class VPlayActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.downloadSize)
    TextView tvDownloadSize;
    @BindView(R.id.netSpeed)
    TextView tvNetSpeed;
    @BindView(R.id.tvProgress)
    TextView tvProgress;
    @BindView(R.id.rl)
    RelativeLayout rl;
//    @BindView(R.id.pbProgress)
//    NumberProgressBar pbProgress;
    @BindView(R.id.activity_play)
    RelativeLayout activityPlay;

    private VideoView videoView;
    private String videoPath,sPathStr,sFileNameStr;
    private MediaController mediaController;
    private Button UpBtn,DlBtn,DelBtn,RepBtn;
    private SPUtils spUtils;
    private int nId,nFileSize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);
        videoView = (VideoView) findViewById(R.id.videoview);
        DlBtn=(Button)findViewById(R.id.btn_download);
        UpBtn = (Button) findViewById(R.id.btn_up);
        DelBtn=(Button)findViewById(R.id.btn_delete);
        RepBtn=(Button)findViewById(R.id.btn_replace);

        videoPath = getIntent().getStringExtra("path");

        spUtils = new SPUtils(this);
        play(videoPath);

        UpBtn.setOnClickListener(this);
        DlBtn.setOnClickListener(this);
        DelBtn.setOnClickListener(this);
        RepBtn.setOnClickListener(this);
    }


    private void play(final String path) {


        mediaController = new MediaController(this);
        videoView.setVideoPath(path);
        // 设置VideView与MediaController建立关联
        videoView.setMediaController(mediaController);
//        // 设置MediaController与VideView建立关联
        mediaController.setMediaPlayer(videoView);
        mediaController.setVisibility(View.INVISIBLE);
        // 让VideoView获取焦点
//        videoView.requestFocus();
        // 开始播放

        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                mp.setLooping(true);
            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.setVideoPath(path);
                videoView.start();
            }
        });

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {


                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
       /* switch (v.getId()) {

            case R.id.btn_up://视频上传
                //拼接参数
                OkGo.post("111")//
                        .tag(this)//
                        .headers(Constant.HDADERS_KEY,spUtils.getBase64Str())
                        .headers("Content-Type", "multipart/form-data;boundary=---sdafkdafdkkdfkdfs")
                        .params("sNo", "")
                        .params("sPiontCode", "")
                        .params("sName", "")
                        .params("sUnitFlag", "")
                        .params("file", new File(videoPath))
                        .execute(new JsonCallback<BaseBean<UploadFileBean>>() {
                            @Override
                            public void onBefore(BaseRequest request) {
                                super.onBefore(request);
                                UpBtn.setText("正在上传中...");
                            }

                            @Override
                            public void onSuccess(BaseBean<UploadFileBean> responseData, Call call, Response response) {
                                nId = responseData.data.getData().getNID();
                                sPathStr=responseData.data.getData().getSPath();
                                sFileNameStr=responseData.data.getData().getSFileName();
                                nFileSize=responseData.data.getData().getNFileSize();
                                UpBtn.setText("上传完成");
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                e.toString();
                                UpBtn.setText("上传出错");
                            }

                            @Override
                            public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                                super.upProgress(currentSize, totalSize, progress, networkSpeed);
//                              System.out.println("upProgress -- " + totalSize + "  " + currentSize + "  " + progress + "  " + networkSpeed);
                                String downloadLength = Formatter.formatFileSize(getApplicationContext(), currentSize);
                                String totalLength = Formatter.formatFileSize(getApplicationContext(), totalSize);
                                tvDownloadSize.setText(downloadLength + "/" + totalLength);
                                String netSpeed = Formatter.formatFileSize(getApplicationContext(), networkSpeed);
                                tvNetSpeed.setText(netSpeed + "/S");
                                tvProgress.setText((Math.round(progress * 10000) * 1.0f / 100) + "%");
                                pbProgress.setMax(100);
                                pbProgress.setProgress((int) (progress * 100));

                            }
                        });
                break;

            case R.id.btn_download://视频下载
//                Logger.d(nId);
                OkGo.get("222")//
                        .tag(this)//
                        .headers(Constant.HDADERS_KEY,spUtils.getBase64Str())//
                        .params("nId", nId)//
                        .execute(new FileCallback("11111"+".mp4") {

                            @Override
                            public void onBefore(BaseRequest request) {
                                DlBtn.setText("正在下载中");
                            }

                            @Override
                            public void onSuccess(File file, Call call, Response response) {

                                DlBtn.setText(file.getPath());
                            }

                            @Override
                            public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
//                              System.out.println("downloadProgress -- " + totalSize + "  " + currentSize + "  " + progress + "  " + networkSpeed);
                                String downloadLength = Formatter.formatFileSize(getApplicationContext(), currentSize);
                                String totalLength = Formatter.formatFileSize(getApplicationContext(), totalSize);
                                tvDownloadSize.setText(downloadLength + "/" + totalLength);
                                String netSpeed = Formatter.formatFileSize(getApplicationContext(), networkSpeed);
                                tvNetSpeed.setText(netSpeed + "/S");
                                tvProgress.setText((Math.round(progress * 10000) * 1.0f / 100) + "%");
                                pbProgress.setMax(100);
                                pbProgress.setProgress((int) (progress * 100));
                            }
                            @Override
                            public void onError(Call call, @Nullable Response response, @Nullable Exception e) {
                                super.onError(call, response, e);

                                DlBtn.setText("下载出错");
                            }
                        });
                break;

            case R.id.btn_delete://视频删除
                 OkGo.delete("3333")
                         .tag(this)
                         .headers(Constant.HDADERS_KEY,spUtils.getBase64Str())
                         .params("nId", nId)
                         .execute(new DialogCallback<BaseBean>(this) {
                             @Override
                             public void onSuccess(BaseBean responseData, Call call, Response response) {
                                    Logger.d(responseData.code);
                                    DelBtn.setText("删除成功");
                             }

                             @Override
                             public void onError(Call call, Response response, Exception e) {
                                 super.onError(call, response, e);
                                 DelBtn.setText("删除失败");
                             }
                         });

                break;

            case R.id.btn_replace://视频更新
                HashMap<String, Object> params = new HashMap<>();
                params.put("sNo", "");
                params.put("sPiontCode", "");
                params.put("sPath", sPathStr);
                params.put("sName", "");
                params.put("sFileName",sFileNameStr);
                params.put("nFileSize",nFileSize);
                params.put("sFileType","");
                params.put("sUnitFlag","");
                params.put("dVerDateTime","2017-03-07T15:48:50.5217933+08:00");

                JSONObject jsonObject = new JSONObject(params);


                OkGo.put("4444")
                        .tag(this)
                        .headers(Constant.HDADERS_KEY,spUtils.getBase64Str())
                        .params("nId", 34)
                        .upJson(jsonObject)
                        .execute(new DialogCallback<String>(this) {

                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                   Logger.d("更新附件返回值",s);

                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                            }
                        });

                break;
        }*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity销毁时，取消网络请求

//        OkGo.getInstance().cancelTag(this);
    }
}
