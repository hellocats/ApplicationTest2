package com.example.administrator.applicationtest2.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.administrator.applicationtest2.R;
import com.example.administrator.applicationtest2.view.test.badge.BadgeNumberActivity1;

/**
 * Author:hepeng
 * Date:2018-09-28
 * Description:前台服务
 */

public class ForeGroundService extends Service {

	public static final int NOTIFICATION_ID = 123456;//如果id为0，将导致不能设置为前台service

	private NotificationManager manager;

	private MyBinder myBinder=new MyBinder();//与外界交互

	public static final String NOTIFICATION_CHANNEL = "my_channel_01";
//	private int notificationId = 0;
//	private NotificationManager mNotificationManager;
//	private NotificationChannel channel;


	@Override
	public void onCreate() {
		super.onCreate();
		Log.e("个推","ForeGroundService,oncreate");
		try {
			manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			Notification.Builder builder = new Notification.Builder(this);
			PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, BadgeNumberActivity1.class), 0);
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
				RemoteViews remoteView = new RemoteViews(getPackageName(), R.layout.custom_notification);
				remoteView.setTextViewText(R.id.notification_title, "标题");
				remoteView.setTextViewText(R.id.notification_content, "前台服务已启动");
				builder.setCustomContentView(remoteView);
			}
			builder.setSmallIcon(R.mipmap.ic_launcher);
			builder.setContentIntent(contentIntent);
			builder.setContentTitle("标题");
			builder.setContentText("前台服务已启动");
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				builder.setChannelId(NOTIFICATION_CHANNEL);
			}
			Notification notification = builder.build();
			startForeground(NOTIFICATION_ID, notification);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		stopForeground(true);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return myBinder;
	}

	//在Activity获取Service实例
	public class MyBinder extends Binder {
		public ForeGroundService getService(){
			return ForeGroundService.this;
		}
	}
}
