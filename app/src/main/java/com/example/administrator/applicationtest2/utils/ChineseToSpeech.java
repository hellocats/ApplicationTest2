package com.example.administrator.applicationtest2.utils;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

/**
 * Author:hepeng
 * Date:2019-05-08
 * Description:
 */

public class ChineseToSpeech {
	private TextToSpeech textToSpeech;



	public ChineseToSpeech(Context mContext) {
		this.textToSpeech = new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {
			@Override
			public void onInit(int status) {
				if (status == TextToSpeech.SUCCESS) {
					int result = textToSpeech.setLanguage(Locale.CHINA);
					if (result == TextToSpeech.LANG_MISSING_DATA
							|| result == TextToSpeech.LANG_NOT_SUPPORTED) {

//						new CoolToast(Application.getContext()).show("不支持朗读功能");
					}
				}
			}
		});
	}

	public void speech(String text) {
		textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	}

	public void destroy() {
		if (textToSpeech != null) {
			textToSpeech.stop();
			textToSpeech.shutdown();
		}
	}

}
