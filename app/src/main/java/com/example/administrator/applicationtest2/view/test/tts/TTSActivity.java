package com.example.administrator.applicationtest2.view.test.tts;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.example.administrator.applicationtest2.R;
import com.example.administrator.applicationtest2.common.baseClass.BaseClsActivity;
import com.example.administrator.applicationtest2.service.NewDeskService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;

/**
 * Author:hepeng
 * Date:2019-05-08
 * Description:
 */

public class TTSActivity extends BaseClsActivity implements View.OnClickListener,MainHandlerConstant {

	// ================== 初始化参数设置开始 ==========================
	/**
	 * 发布时请替换成自己申请的appId appKey 和 secretKey。注意如果需要离线合成功能,请在您申请的应用中填写包名。
	 * 本demo的包名是com.baidu.tts.sample，定义在build.gradle中。
	 */
	protected String appId = "16201595";

	protected String appKey = "sU5jl3ltwkge2sXpZAOLuTYj";

	protected String secretKey = "WinKopyBMjg23hG8vLBDEovubEkGB9mM";

	// TtsMode.MIX; 离在线融合，在线优先； TtsMode.ONLINE 纯在线； 没有纯离线
	protected TtsMode ttsMode = TtsMode.MIX;

	// 离线发音选择，VOICE_FEMALE即为离线女声发音。
	// assets目录下bd_etts_common_speech_m15_mand_eng_high_am-mix_v3.0.0_20170505.dat为离线男声模型；
	// assets目录下bd_etts_common_speech_f7_mand_eng_high_am-mix_v3.0.0_20170512.dat为离线女声模型
	protected String offlineVoice = OfflineResource.VOICE_MALE;

	// ===============初始化参数设置完毕，更多合成参数请至getParams()方法中设置 =================

	// 主控制类，所有合成控制方法从这个类开始
	protected MySyntherizer synthesizer;

	@BindView(R.id.tts_edt_content)
	EditText editText;
	@BindView(R.id.tts_btn_play)
	Button btn_play;
	private TextToSpeech textToSpeech = null;//创建自带语音对象
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tts);
		btn_play.setOnClickListener(this);
//		initTTS();
//		initialTts();

		Intent startIntent = new Intent(TTSActivity.this, NewDeskService.class);
		//startService启动形式
		startService(startIntent);
	}
	private void initTTS() {
		//实例化自带语音对象
		textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
			@Override
			public void onInit(int status) {
				if (status == textToSpeech.SUCCESS) {
					// Toast.makeText(MainActivity.this,"成功输出语音",
					// Toast.LENGTH_SHORT).show();
					// Locale loc1=new Locale("us");
					// Locale loc2=new Locale("china");

					textToSpeech.setPitch(1.0f);//方法用来控制音调
					textToSpeech.setSpeechRate(1.0f);//用来控制语速

					//判断是否支持下面两种语言
					int result1 = textToSpeech.setLanguage(Locale.US);
					int result2 = textToSpeech.setLanguage(Locale.
							SIMPLIFIED_CHINESE);
					boolean a = (result1 == TextToSpeech.LANG_MISSING_DATA || result1 == TextToSpeech.LANG_NOT_SUPPORTED);
					boolean b = (result2 == TextToSpeech.LANG_MISSING_DATA || result2 == TextToSpeech.LANG_NOT_SUPPORTED);

					Log.i("zhh_tts", "US支持否？--》" + a +
							"\nzh-CN支持否》--》" + b);
				} else {
					Toast.makeText(TTSActivity.this, "数据丢失或不支持", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	/**
	 * speak 实际上是调用 synthesize后，获取音频流，然后播放。
	 * 获取音频流的方式见SaveFileActivity及FileSaveListener
	 * 需要合成的文本text的长度不能超过1024个GBK字节。
	 */
	private void speak() {
//		mShowText.setText("");
		String text = editText.getText().toString();
		// 需要合成的文本text的长度不能超过1024个GBK字节。
		if (TextUtils.isEmpty(text)) {
			text = "百度语音，面向广大开发者永久免费开放语音合成技术。";
//			mInput.setText(text);
		}
		// 合成前可以修改参数：
		// Map<String, String> params = getParams();
		// synthesizer.setParams(params);
		if(synthesizer!=null) {
			int result = synthesizer.speak(text);
//		checkResult(result, "speak");
			Log.e("speak", result + "");
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.tts_btn_play) {
//			startAuto(editText.getText().toString());
			speak();
		}

	}

	private void startAuto(String data) {
		// 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
		textToSpeech.setPitch(1.0f);
		// 设置语速
		textToSpeech.setSpeechRate(0.3f);
		textToSpeech.speak(data,//输入中文，若不支持的设备则不会读出来
				TextToSpeech.QUEUE_FLUSH, null);

	}

	@Override
	protected void onStop() {
		super.onStop();
		if(textToSpeech!=null) {
			textToSpeech.stop(); // 不管是否正在朗读TTS都被打断
			textToSpeech.shutdown(); // 关闭，释放资源
		}
	}
	/**
	 * 初始化引擎，需要的参数均在InitConfig类里
	 * <p>
	 * DEMO中提供了3个SpeechSynthesizerListener的实现
	 * MessageListener 仅仅用log.i记录日志，在logcat中可以看见
	 * UiMessageListener 在MessageListener的基础上，对handler发送消息，实现UI的文字更新
	 * FileSaveListener 在UiMessageListener的基础上，使用 onSynthesizeDataArrived回调，获取音频流
	 */
	protected void initialTts() {
		LoggerProxy.printable(true); // 日志打印在logcat中
		// 设置初始化参数
		// 此处可以改为 含有您业务逻辑的SpeechSynthesizerListener的实现类
		SpeechSynthesizerListener listener = new UiMessageListener(mainHandler);

		Map<String, String> params = getParams();


		// appId appKey secretKey 网站上您申请的应用获取。注意使用离线合成功能的话，需要应用中填写您app的包名。包名在build.gradle中获取。
		InitConfig initConfig = new InitConfig(appId, appKey, secretKey, ttsMode, params, listener);

		// 如果您集成中出错，请将下面一段代码放在和demo中相同的位置，并复制InitConfig 和 AutoCheck到您的项目中
		// 上线时请删除AutoCheck的调用
		AutoCheck.getInstance(getApplicationContext()).check(initConfig, new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 100) {
					AutoCheck autoCheck = (AutoCheck) msg.obj;
					synchronized (autoCheck) {
						String message = autoCheck.obtainDebugMessage();
						toPrint(message); // 可以用下面一行替代，在logcat中查看代码
						// Log.w("AutoCheckMessage", message);
					}
				}
			}

		});
		synthesizer = new NonBlockSyntherizer(this, initConfig, mainHandler); // 此处可以改为MySyntherizer 了解调用过程
	}
	/**
	 * 合成的参数，可以初始化时填写，也可以在合成前设置。
	 *
	 * @return
	 */
	protected Map<String, String> getParams() {
		Map<String, String> params = new HashMap<String, String>();
		// 以下参数均为选填
		// 设置在线发声音人： 0 普通女声（默认） 1 普通男声 2 特别男声 3 情感男声<度逍遥> 4 情感儿童声<度丫丫>
		params.put(SpeechSynthesizer.PARAM_SPEAKER, "0");
		// 设置合成的音量，0-9 ，默认 5
		params.put(SpeechSynthesizer.PARAM_VOLUME, "9");
		// 设置合成的语速，0-9 ，默认 5
		params.put(SpeechSynthesizer.PARAM_SPEED, "5");
		// 设置合成的语调，0-9 ，默认 5
		params.put(SpeechSynthesizer.PARAM_PITCH, "5");

		params.put(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);
		// 该参数设置为TtsMode.MIX生效。即纯在线模式不生效。
		// MIX_MODE_DEFAULT 默认 ，wifi状态下使用在线，非wifi离线。在线状态下，请求超时6s自动转离线
		// MIX_MODE_HIGH_SPEED_SYNTHESIZE_WIFI wifi状态下使用在线，非wifi离线。在线状态下， 请求超时1.2s自动转离线
		// MIX_MODE_HIGH_SPEED_NETWORK ， 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线
		// MIX_MODE_HIGH_SPEED_SYNTHESIZE, 2G 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线

		// 离线资源文件， 从assets目录中复制到临时目录，需要在initTTs方法前完成
		OfflineResource offlineResource = createOfflineResource(offlineVoice);
		// 声学模型文件路径 (离线引擎使用), 请确认下面两个文件存在
		params.put(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, offlineResource.getTextFilename());
		params.put(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE,
				offlineResource.getModelFilename());
		return params;
	}
	protected OfflineResource createOfflineResource(String voiceType) {
		OfflineResource offlineResource = null;
		try {
			offlineResource = new OfflineResource(this, voiceType);
		} catch (IOException e) {
			// IO 错误自行处理
			e.printStackTrace();
			toPrint("【error】:copy files from assets failed." + e.getMessage());
		}
		return offlineResource;
	}

//	protected TextView mShowText;
//	protected EditText mInput;
	protected Handler mainHandler  = new Handler() {
            /*
             * @param msg
             */
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			handle(msg);
		}

	};
	protected void handle(Message msg) {
//		int what = msg.what;
//		switch (what) {
//			case PRINT:
//				print(msg);
//				break;
//			case UI_CHANGE_INPUT_TEXT_SELECTION:
//				if (msg.arg1 <= mInput.getText().length()) {
//					mInput.setSelection(0, msg.arg1);
//				}
//				break;
//			case UI_CHANGE_SYNTHES_TEXT_SELECTION:
//				SpannableString colorfulText = new SpannableString(mInput.getText().toString());
//				if (msg.arg1 <= colorfulText.toString().length()) {
//					colorfulText.setSpan(new ForegroundColorSpan(Color.GRAY), 0, msg.arg1, Spannable
//							.SPAN_EXCLUSIVE_EXCLUSIVE);
//					mInput.setText(colorfulText);
//				}
//				break;
//			default:
//				break;
//		}
	}

	protected void toPrint(String str) {
		Message msg = Message.obtain();
		msg.obj = str;
		mainHandler.sendMessage(msg);
	}

	private void print(Message msg) {
		String message = (String) msg.obj;
		if (message != null) {
//			scrollLog(message);
		}
	}


}
