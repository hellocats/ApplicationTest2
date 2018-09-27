package com.example.administrator.applicationtest2.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.administrator.applicationtest2.service.GTPushService;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.PushService;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * Author:hepeng
 * Date:2018-09-27
 * Description:
 */

public class BootCompletedReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
//		context.startService(new Intent(context,TestService.class));
		Log.e("test2","BootCompletedReceiver");
		// com.getui.demo.DemoPushService 为第三方自定义推送服务
		PushManager.getInstance().initialize(getApplicationContext(), PushService.class);
		// com.getui.demo.DemoIntentService 为第三方自定义的推送服务事件接收类
		PushManager.getInstance().registerPushIntentService(getApplicationContext(), GTPushService.class);
	}
}

