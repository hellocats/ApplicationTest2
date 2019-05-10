package com.example.administrator.applicationtest2.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.administrator.applicationtest2.MainActivity;
import com.example.administrator.applicationtest2.R;
import com.example.administrator.applicationtest2.receiver.PlayerReceiver;

import static com.example.administrator.applicationtest2.receiver.PlayerReceiver.ACTION_LAST;
import static com.example.administrator.applicationtest2.receiver.PlayerReceiver.ACTION_NEXT;
import static com.example.administrator.applicationtest2.receiver.PlayerReceiver.ACTION_PAUSE;
import static com.example.administrator.applicationtest2.receiver.PlayerReceiver.ACTION_PLAY;

/**
 * Author:hepeng
 * Date:2019-05-10
 * Description:
 */

public class NewDeskService extends Service {
	private static final String CHANNEL_ID = "NewDeskService";
	private static final String CHANNEL_NAME = "NewDeskService";
	private static final String TAG = NewDeskService.class.getSimpleName();
	PlayerReceiver playerReceiver;

	// 动态注册广播
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate()");

		playerReceiver = new PlayerReceiver();
		IntentFilter mFilter = new IntentFilter();

		mFilter.addAction(ACTION_PLAY);
		mFilter.addAction(ACTION_PAUSE);
		mFilter.addAction(ACTION_LAST);
		mFilter.addAction(ACTION_NEXT);

		registerReceiver(playerReceiver, mFilter);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {


		Log.d(TAG, "onStartCommand()");
		Notification.Builder builder = null; //获取一个Notification构造器
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);;
			NotificationChannel channel = null;
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
//				channel.setGroup(GROUP_ID);
				channel.setShowBadge(true);
				channel.setLightColor(Color.RED);
				channel.enableLights(true);
				nm.createNotificationChannel(channel);
			}
			builder = new Notification.Builder(this.getApplicationContext(), CHANNEL_ID)
					.setContentText("这是一个前台服务")
					.setSmallIcon(R.mipmap.ic_launcher)
					.setWhen(System.currentTimeMillis());
		} else {
			builder = new Notification.Builder
					(this.getApplicationContext());
		}
		Intent nfIntent = new Intent(this, MainActivity.class);

		builder.setContentIntent(PendingIntent.
				getActivity(this, 0, nfIntent, 0)) // 设置PendingIntent
				.setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
						R.drawable.center_ico2)) // 设置下拉列表中的图标(大图标)
				.setContentTitle("下拉列表中的Title") // 设置下拉列表里的标题
				.setSmallIcon(R.mipmap.ic_launcher) // 设置状态栏内的小图标
				.setContentText("要显示的内容") // 设置上下文内容
				.setWhen(System.currentTimeMillis()); // 设置该通知发生的时间


		RemoteViews remoteViews = new RemoteViews(this.getPackageName(),
				R.layout.notification_layout);// 获取remoteViews（参数一：包名；参数二：布局资源）
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			builder.setCustomContentView(remoteViews);// 设置自定义的Notification内容
		}else{
			builder.setContent(remoteViews);// 设置自定义的Notification内容
		}
		builder.setWhen(System.currentTimeMillis())
				.setSmallIcon(R.mipmap.ic_launcher);


//		Notification notification = builder.build(); // 获取构建好的Notification
		Notification notification = builder.getNotification();// 获取构建好的通知--.build()最低要求在
		// API16及以上版本上使用，低版本上可以使用.getNotification()。
		notification.defaults = Notification.DEFAULT_SOUND;//设置为默认的声音
		startForeground(110, notification);// 开始前台服务

		return super.onStartCommand(intent, flags, startId);
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy()");
		stopForeground(true);// 停止前台服务--参数：表示是否移除之前的通知
		if (playerReceiver != null)
			unregisterReceiver(playerReceiver);// 解除广播注册
		super.onDestroy();
	}

}
