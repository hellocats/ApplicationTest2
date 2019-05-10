package com.example.administrator.applicationtest2.view.test.badge;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.applicationtest2.R;
import com.example.administrator.applicationtest2.bean.MessageEvent;
import com.example.administrator.applicationtest2.common.baseClass.BaseClsActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * Author:hepeng
 * Date:2018-09-25
 * Description:桌面角标
 */

public class BadgeNumberActivity1 extends BaseClsActivity {
	private static String OSName = null;
	private static String SYSTEM_XIAOMI = "XIAOMI";

	private static final String NOTIFICATION_CHANNEL = "me.leolin.shortcutbadger";
	private int notificationId = 10;
	private NotificationManager mNotificationManager;
	private NotificationChannel channel;

	private int badgeCount = 22;
	@BindView(R.id.btn_set_badge)
	TextView btn;
	@BindView(R.id.btn_cancel_badge)
	TextView cancel;
	@BindView(R.id.btn_check)
	TextView check;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e("个推","BadgeNumberActivity1，onCreate");
		setContentView(R.layout.test_badge_number1);

		OSName = android.os.Build.BRAND.trim().toUpperCase();
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showNumber();
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ShortcutBadger.removeCount(BadgeNumberActivity1.this); //for 1.1.4+
			}
		});
		EventBus.getDefault().register(this);
		check.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				check();
			}
		});
//		startService(new Intent(this, DeskService.class));
//		Intent intent = new Intent(this, DeskService.class);
//		intent.setAction("com.service.DeskService");

//		Intent intent = new Intent(getApplicationContext(), ForeGroundService.class);
//		startService(intent);

	}
	private void showNumber() {
		badgeCount++;
//		ShortcutBadger.removeCount(BadgeNumberActivity1.this);
		if (SYSTEM_XIAOMI.equals(OSName)) {
			xiaomi();
		} else {
			ShortcutBadger.applyCount(BadgeNumberActivity1.this, badgeCount); //for 1.1.4+
		}

	}

	private void xiaomi() {
		mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


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
	private void setupNotificationChannel() {
		String name = "ShortcutBadger Sample";
		if (channel == null) {
			channel = new NotificationChannel(NOTIFICATION_CHANNEL, name,
					NotificationManager.IMPORTANCE_DEFAULT);
		}
		mNotificationManager.createNotificationChannel(channel);
	}

	private void init() {
		String id = "my_channel_01";
		String name = "我是渠道名字";
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Notification notification = null;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW);
			Toast.makeText(BadgeNumberActivity1.this, mChannel.toString(), Toast.LENGTH_SHORT).show();
//					Log.i(TAG, mChannel.toString());
			notificationManager.createNotificationChannel(mChannel);
			notification = new Notification.Builder(BadgeNumberActivity1.this)
					.setChannelId(id)
					.setContentTitle("5 new messages")
					.setContentText("hahaha")
					.setSmallIcon(R.mipmap.ic_launcher).build();
		} else {
			NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(BadgeNumberActivity1.this)
					.setContentTitle("5 new messages")
					.setContentText("hahaha")
					.setSmallIcon(R.mipmap.ic_launcher)
					.setOngoing(true)
					.setChannelId(id);//无效
			notification = notificationBuilder.build();
		}
		ShortcutBadger.applyNotification(getApplicationContext(), notification, 32);
	}


	@Subscribe(threadMode = ThreadMode.MAIN)
	public void Event(MessageEvent messageEvent) {
//		mText.setText(messageEvent.getMessage());
		showNumber();
	}

	@Override
	protected void onDestroy() {
		try {
			super.onDestroy();
			if (EventBus.getDefault().isRegistered(this)) {
				EventBus.getDefault().unregister(this);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void check() {
		boolean isServiceRunning = false;
		String result = "";
//			//检查Service状态  
//			ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//			for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
//				if ("com.igexin.sdk.PushService".equals(service.service.getClassName())) {
//					result+="1";
////					showToast("1");
////					isServiceRunning = true;
//				}
//				if (".service.BadgeIntentService".equals(service.service.getClassName())) {
////					showToast("2");
//					result+="2";
//				}
//				if (".service.PushServic".equals(service.service.getClassName())) {
////					showToast("3");
//					result+="3";
//				}
//				if (".service.GTPushService".equals(service.service.getClassName())) {
////					showToast("4");
//					result+="4";
//				}
//			}
////			if(!result.contains("1")){
////				startService(new Intent(BadgeNumberActivity1.this,MainService.class));
////			}
//			showToast(result);
////			if (!isServiceRunning) {
////				Intent i = new Intent(BadgeNumberActivity1.this, xxxService.class);
////				startService(i);
////			}


	}


}
