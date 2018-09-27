package com.example.administrator.applicationtest2.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Author:hepeng
 * Date:2018-09-27
 * Description:
 */

public class ScreenLockReceiver  extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.e("test2","ScreenLockReceiver");

	}
}
