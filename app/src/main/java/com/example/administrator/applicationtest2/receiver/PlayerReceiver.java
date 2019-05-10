package com.example.administrator.applicationtest2.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Author:hepeng
 * Date:2019-05-10
 * Description:
 */

public class PlayerReceiver extends BroadcastReceiver {
	public static final String ACTION_PLAY = "1";
	public static final String ACTION_PAUSE = "2";
	public static final String ACTION_LAST = "3";
	public static final String ACTION_NEXT = "4";

	private static final String TAG = PlayerReceiver.class.getSimpleName();

	public PlayerReceiver() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO: This method is called when the BroadcastReceiver is receiving
		String action = intent.getAction();// 获取对应Action
		Log.d(TAG, "action:" + action);

		if (action.equals(ACTION_PLAY)) {
		// 进行对应操作
		} else if (action.equals(ACTION_PAUSE)) {

		} else if (action.equals(ACTION_LAST)) {

		} else if (action.equals(ACTION_NEXT)) {

		}

	}
}
