package com.example.administrator.applicationtest2.utils;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.example.administrator.applicationtest2.R;

import me.leolin.shortcutbadger.ShortcutBadger;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * Author:hepeng
 * Date:2018-09-27
 * Description:
 */

public class BadgeUtil {
	private static final String NOTIFICATION_CHANNEL = "me.leolin.shortcutbadger";
	private static String OSName=null;
	private static String SYSTEM_XIAOMI="XIAOMI";
	private static NotificationManager mNotificationManager;
	private static NotificationChannel channel;
	private static int notificationId = 0;

	public static void showNumber(Context cxt,int badgeCount){
		if(SYSTEM_XIAOMI.equals(OSName)){
			xiaomi(cxt,badgeCount);
		}else{
			ShortcutBadger.applyCount(cxt, badgeCount); //for 1.1.4+
		}

	}
	private  static void xiaomi(Context cxt,int badgeCount){

		mNotificationManager = (NotificationManager) cxt.getSystemService(Context.NOTIFICATION_SERVICE);


		mNotificationManager.cancel(notificationId);
		notificationId++;

		Notification.Builder builder = new Notification.Builder(getApplicationContext())
				.setContentTitle("123")
				.setContentText("322")
				.setSmallIcon(R.mipmap.ic_launcher);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			setupNotificationChannel();

			builder.setChannelId(NOTIFICATION_CHANNEL);
		}

		Notification notification = builder.build();

		ShortcutBadger.applyNotification(getApplicationContext(), notification, badgeCount);
		mNotificationManager.notify(notificationId, notification);

	}
	@TargetApi(Build.VERSION_CODES.O)
	private static void setupNotificationChannel() {
		String name="ShortcutBadger Sample";
		if(channel == null) {
			channel = new NotificationChannel(NOTIFICATION_CHANNEL, name,
					NotificationManager.IMPORTANCE_DEFAULT);
		}
		mNotificationManager.createNotificationChannel(channel);
	}
}
