package com.example.administrator.applicationtest2.service;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.administrator.applicationtest2.utils.BadgeUtil;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;

/**
 * Author:hepeng
 * Date:2018-09-26
 * Description:个推服务
 */

public class GTPushService extends GTIntentService {
	private int nCount = 0;
	private MyBinder myBinder=new MyBinder();//与外界交互
	public GTPushService() {

	}

	@Override
	public void onReceiveServicePid(Context context, int pid) {
	}

	@Override
	public void onReceiveMessageData(Context context, GTTransmitMessage msg) {

	}

	@Override
	public void onReceiveClientId(Context context, String clientid) {
		Log.e(TAG, "个推onReceiveClientId -> " + "clientid = " + clientid);
	}

	@Override
	public void onReceiveOnlineState(Context context, boolean online) {
	}

	@Override
	public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
	}


	@Override
	public void onNotificationMessageArrived(Context context, GTNotificationMessage msg) {
//		EventBus.getDefault().post(new MessageEvent("个推消息"));
		int count =1;
		try {
			count = Integer.parseInt(msg.getContent());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		BadgeUtil.showNumber(context,count);

//		Log.e("GTPushService", "onNotificationMessageArrived");
//		Notification.Builder builder = new Notification.Builder(this);
//		builder.setSmallIcon(R.mipmap.ic_launcher);
//		builder.setContentText("前台服务已启动" + msg.getContent());
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//			builder.setChannelId(ForeGroundService.NOTIFICATION_CHANNEL);
//		}
//		Notification notification = builder.build();
//		startForeground(ForeGroundService.NOTIFICATION_ID, notification);
	}

	@Override
	public void onNotificationMessageClicked(Context context, GTNotificationMessage msg) {
	}

	@Override
	public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
		flags = START_STICKY;
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				boolean isServiceRunning = false;
//				ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//				for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
////					if ("com.example.administrator.applicationtest2.service.PushServic".equals(service.service.getClassName())) {
////						isServiceRunning = true;
////					}
//					if("com.igexin.sdk.PushService".equals(service.service.getClassName()) && "com.example.administrator.applicationtest2:pushservice".equals(service.process)){
//						isServiceRunning = true;
//					}
//					if ("com.igexin.sdk.PushService".equals(service.service.getClassName())) {
//						Log.e("GTPushService", "PushService");
//					}
//				}
//			if (!isServiceRunning) {
////				startService(new Intent(GTPushService.this, GTPushService.class));
//				Log.e("个推GTPushService", "onDestroy,startService");
//			}
//
//			}
//		}).start();
//		Log.e("个推GTPushService", "onDestroy");
	}
	@Override
	public IBinder onBind(Intent intent) {
		return myBinder;
	}
	//在Activity获取Service实例
	public class MyBinder extends Binder {
		public GTPushService getService(){
			return GTPushService.this;
		}
	}
}
