package com.example.administrator.applicationtest2.view.test.record;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.administrator.applicationtest2.R;
import com.example.administrator.applicationtest2.common.baseClass.BaseClsActivity;
import com.example.administrator.applicationtest2.utils.CommentsUtils;
import com.example.administrator.applicationtest2.utils.CommonAdapter;
import com.example.administrator.applicationtest2.widget.DashView;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:hepeng
 * Date:2018-12-10
 * Description:
 */
@Route(path = "/record/RecordActivity")//添加路由注解 并且这里的路径需要注意的是至少需要有两级
public class RecordActivity extends BaseClsActivity {
	private PhoneListener phoneListener = new PhoneListener();
	private TelephonyManager tm = null;
	private Button btn_look;
	private EditText edt_number;
	private MediaRecorder recorder;
	private boolean isRecord;
	// private String number;
	private File file;
	private AudioRecordManager recordManager;
	private LinearLayout layout;
	private ListView listView;
	private CommonAdapter adapter;
	private List<String> dataList = new ArrayList<>();
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record);
		Button btnCall = findViewById(R.id.record_btn_call);
		btn_look = findViewById(R.id.record_btn_look);
		edt_number = findViewById(R.id.record_edt_number);
		layout = findViewById(R.id.lay_layout);
		listView = findViewById(R.id.list_view);
		listView.setDividerHeight(0);
		tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		tm.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
		ARouter.getInstance().inject(this);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			AndPermission.with(RecordActivity.this).permission(Permission.Group.MICROPHONE, Permission.Group.PHONE).onGranted(new Action() {
				@Override
				public void onAction(List<String> permissions) {
				}
			}).onDenied(new Action() {
				@Override
				public void onAction(List<String> permissions) {
					return;
				}
			}).start();
		}
		btnCall.setOnClickListener(new View.OnClickListener() {
									   @Override
									   public void onClick(View v) {
										   ARouter.getInstance().build("/custom/CustomControl").navigation();//发起无参数的路由操作
//										   ARouter.getInstance().build("/app/CustomControl").withString("name", "测试名称").navigation();//带参数
//										   ARouter.getInstance().build("/app/CustomControl").navigation(TestActivity.this,0X123);//相当于startActivityForResult

//										   String sNumber =edt_number.getText().toString();
//										   if(!"".equals(sNumber)){
//											   callPhone(sNumber);
//										   }else {
//											   callPhone("10086");
//										   }
									   }
								   }
		);
		btn_look.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (file != null) {
					CommentsUtils.openFile(RecordActivity.this, file.getAbsolutePath(), RecordActivity.this);
				}
			}
		});

//		String url = "http://www.baidu.com";
//		mWebView.getSettings().setJavaScriptEnabled(true);
//		//mWebView，使网页用WebView打开
//		mWebView.setWebViewClient(new WebViewClient(){
//			@Override
//			public boolean shouldOverrideUrlLoading(WebView view, String url) {
//				//返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
//				view.loadUrl(url);
//				return true;
//			}
//		});
//		mWebView.loadUrl(url);
		int lineHeight = 300;
		int marginTop = 80;
		DashView dashView = new DashView(RecordActivity.this);
		dashView.init(0,100,2,R.color.green,DashView.ORIENTATION_VERTICAL);
		ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,lineHeight);
		params.setMargins(0,marginTop,0,0);
		dashView.setLayoutParams(params);
		layout.addView(dashView);


		listView.setAdapter(adapter = new CommonAdapter<String>(RecordActivity.this,dataList,R.layout.item_record) {
			@Override
			public void convert(ViewHolder helper, String item) {
				LinearLayout lay_todo = helper.getView(R.id.dynamic_item_lay_todo);
				lay_todo.removeAllViews();
				int height = helper.getConvertView().getMeasuredHeight();
				int lineHeight = (int) (height*0.5);
				int marginTop = 80;
				DashView dashView = new DashView(RecordActivity.this);
				dashView.init(0,100,2,getResources().getColor(R.color.blue),DashView.ORIENTATION_VERTICAL);
				ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,lineHeight);
				params.setMargins(0,marginTop,0,0);
				dashView.setLayoutParams(params);
				lay_todo.addView(dashView);
			}
		});
		dataList.add("测试1");
		dataList.add("测试2");
		dataList.add("测试3");
		adapter.notifyDataSetChanged();
	}

	private void callPhone(String number) {
		if (!number.equals("")) {
			// 用intent启动拨打电话
			Intent intent = new Intent(Intent.ACTION_CALL, Uri
					.parse("tel:" + number));
			try {
				if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
					return;
				}
				startActivity(intent);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Toast.makeText(RecordActivity.this, "请输入电话号码",
					Toast.LENGTH_LONG).show();
		}
	}


	public class PhoneListener extends PhoneStateListener {

		private int previousState = TelephonyManager.CALL_STATE_IDLE;

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			super.onCallStateChanged(state, incomingNumber);
			switch (state) {
				case TelephonyManager.CALL_STATE_RINGING:
					// ToastUtils.showToast(MainActivity.this,"拨入电话,铃声响起");
					break;
				case TelephonyManager.CALL_STATE_OFFHOOK:
					// ToastUtils.showToast(MainActivity.this,"通话中");

					callOffhook(incomingNumber);

					break;
				case TelephonyManager.CALL_STATE_IDLE:

					callStateIdle(incomingNumber, previousState);

					break;
				default:
					break;
			}
			previousState = state;
		}
	}


	public void callOffhook(String phoneNumb) {
//		String recordTitle = tvUsername.getText().toString().trim() + "_" + phoneNumb;
		String recordTitle = phoneNumb;
		File fileDir = new File(Environment.getExternalStorageDirectory() + File.separator + "zdsmApkDownload");
		if (!fileDir.exists())
			fileDir.mkdirs();
//		file = new File(fileDir.getAbsolutePath(), recordTitle + ".amr");
		file = new File(fileDir.getAbsolutePath(), recordTitle + ".3gp");
//		file = new File(fileDir.getAbsolutePath(), recordTitle + ".m4a");
		recorder = new MediaRecorder();
		recorder.reset();
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);//存储格式
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);//设置编码
//		//设置保存文件格式为MP4
//		recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
//		//设置采样频率,44100是所有安卓设备都支持的频率,频率越高，音质越好，当然文件越大
//		recorder.setAudioSamplingRate(44100);
//		//设置声音数据编码格式,音频通用格式是AAC
//		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
//		//设置编码频率
//		recorder.setAudioEncodingBitRate(96000);

		recorder.setOutputFile(file.getAbsolutePath());
		recorder.setOnInfoListener(new MediaRecorder.OnInfoListener() {
			@Override
			public void onInfo(MediaRecorder mr, int what, int extra) {
				Log.i("CallRecorder", "RecordService got MediaRecorder onInfo callback with what: " + what + " extra: " + extra);
				isRecord = false;
			}
		});
		recorder.setOnErrorListener(new MediaRecorder.OnErrorListener() {
			@Override
			public void onError(MediaRecorder mr, int what, int extra) {
				Log.e("CallRecorder", "RecordService got MediaRecorder onError callback with what: " + what + " extra: " + extra);
				isRecord = false;
				mr.release();
			}
		});

		try {
			recorder.prepare();
			isRecord = true;
		} catch (IOException e) {
//			LogUtils.e("CallRecorder", "==" + e);
			recorder = null;
			return;
		}
		recorder.start(); // 开始刻录
	}

	public void callStateIdle(String phoneNumber, int previousState) {
		if (previousState != TelephonyManager.CALL_STATE_IDLE) {
			showToast("挂断电话");
			//  number = null;
			if (recorder != null && isRecord) {
				try {
					recorder.stop();//停止录音
				} catch (Exception e) {
					showToast("录音异常");
					return;
				}

				recorder.reset();
				recorder.release();
				isRecord = false;
				// recorder = null;
			 /*   if (activity == null) {
                    return;
                }*/
				//分享

			}
		} else {
			return;
		}
	}


}
