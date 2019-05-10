package com.example.administrator.applicationtest2.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.administrator.applicationtest2.MainActivity;
import com.example.administrator.applicationtest2.R;

/**
 * Author:hepeng
 * Date:2018-09-27
 * Description:前台服务
 */

public class DeskService extends Service {

	private static final String TAG = "DaemonService";
	public static final int NOTICE_ID = 100;

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
//		//设置为前台服务
//		Notification.Builder builder = new Notification.Builder(this);
//		builder.setContentTitle("播放音乐中..").setContentText("Diamonds")
//				.setWhen(System.currentTimeMillis()).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
//		Notification notifation = builder.getNotification();
//		startForeground(2, notifation);

		return null;

	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "DaemonService---->onCreate被调用，启动前台service");
		//如果API大于18，需要弹出一个可见通知
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
			Notification.Builder builder = new Notification.Builder(this);
			builder.setSmallIcon(R.mipmap.ic_launcher);
			builder.setContentTitle("KeepAppAlive");
			builder.setContentText("DaemonService is runing...");
			startForeground(NOTICE_ID, builder.build());
			// 如果觉得常驻通知栏体验不好
			// 可以通过启动CancelNoticeService，将通知移除，oom_adj值不变
			Intent intent = new Intent(this, CancelNoticeService.class);
			startService(intent);
		} else {
			startForeground(NOTICE_ID, new Notification());
		}

	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStartCommand()");
		// 在API11之后构建Notification的方式
		Notification.Builder builder = new Notification.Builder
				(this.getApplicationContext()); //获取一个Notification构造器
		Intent nfIntent = new Intent(this, MainActivity.class);

		builder.setContentIntent(PendingIntent.getActivity(this, 0, nfIntent, 0)) // 设置PendingIntent
				.setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher)) // 设置下拉列表中的图标(大图标)
				.setContentTitle("下拉列表中的Title") // 设置下拉列表里的标题
				.setSmallIcon(R.mipmap.ic_launcher) // 设置状态栏内的小图标
				.setContentText("要显示的内容") // 设置上下文内容
				.setWhen(System.currentTimeMillis()); // 设置该通知发生的时间

		Notification notification = builder.build(); // 获取构建好的Notification
		notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音
		// 如果Service被终止
		// 当资源允许情况下，重启service
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// 如果Service被杀死，干掉通知
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
			NotificationManager mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			mManager.cancel(NOTICE_ID);
		}
		Log.d(TAG, "DaemonService---->onDestroy，前台service被杀死");
		// 重启自己
		Intent intent = new Intent(getApplicationContext(), DeskService.class);
		startService(intent);
	}

}