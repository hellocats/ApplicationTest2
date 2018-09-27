package com.example.administrator.applicationtest2.view.test.badge;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.applicationtest2.R;
import com.example.administrator.applicationtest2.common.baseClass.BaseClsActivity;

/**
 * Author:hepeng
 * Date:2018-09-19
 * Description:
 */

public class BadgeNumberActivity extends BaseClsActivity {
	private int MQTT_IM_NOTIFICATION_ID=1007;
	//    @BindView(R.id.linear_tv_content)
//    TextView tv_content;
//    @BindView(R.id.linear_tv_voice)
//    TextView tv_voice;
//    @BindView(R.id.linear_tv_photo1)
//    TextView tv_photo1;
//    @BindView(R.id.linear_tv_photo2)
//    TextView tv_photo2;
//    @BindView(R.id.linear_tv_photo3)
//    TextView tv_photo3;
//    @BindView(R.id.linear_tv_photo4)
//    TextView tv_photo4;
	private Button btnSetBadge;
	private int mCount = 13;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_linearlayout);
		init();
//		int badgeCount = 1;
//		ShortcutBadger.applyCount(BadgeNumberActivity.this, badgeCount); //for 1.1.4+
//		ShortcutBadger.with(getApplicationContext()).count(badgeCount); //for 1.1.3
	}

	private void init() {
		btnSetBadge = (Button) findViewById(R.id.btn_set_badge);



		btnSetBadge.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				show();
			}
		});
	}

	private void show(){
		String id = "my_channel_01";
		String name="我是渠道名字";
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Notification notification = null;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW);
			Toast.makeText(BadgeNumberActivity.this, mChannel.toString(), Toast.LENGTH_SHORT).show();
//					Log.i(TAG, mChannel.toString());
			notificationManager.createNotificationChannel(mChannel);
			notification = new Notification.Builder(BadgeNumberActivity.this)
					.setChannelId(id)
					.setContentTitle("5 new messages")
					.setContentText("hahaha")
					.setSmallIcon(R.mipmap.ic_launcher).build();
		} else {
			NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(BadgeNumberActivity.this)
					.setContentTitle("5 new messages")
					.setContentText("hahaha")
					.setSmallIcon(R.mipmap.ic_launcher)
					.setOngoing(true)
					.setChannelId(id);//无效
			notification = notificationBuilder.build();
		}
//				notificationManager.notify(111123, notification);
		if (mCount > 0) {
			DifferentNotifications.showBubble(BadgeNumberActivity.this,notification,MQTT_IM_NOTIFICATION_ID, mCount);
		}else {
			DifferentNotifications.hideBubble(BadgeNumberActivity.this,notification,MQTT_IM_NOTIFICATION_ID);
		}
	}



}
