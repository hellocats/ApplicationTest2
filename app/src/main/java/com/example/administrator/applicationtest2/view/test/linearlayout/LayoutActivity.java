package com.example.administrator.applicationtest2.view.test.linearlayout;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.applicationtest2.MainActivity;
import com.example.administrator.applicationtest2.R;
import com.example.administrator.applicationtest2.common.baseClass.BaseClsActivity;
import com.example.administrator.applicationtest2.view.test.badge.BadgeNumberManager;
import com.example.administrator.applicationtest2.view.test.badge.BadgeNumberManagerXiaoMi;
import com.example.administrator.applicationtest2.view.test.badge.DifferentNotifications;
import com.example.administrator.applicationtest2.view.test.badge.MobileBrand;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * Date:2018-02-06
 * Author:he
 * Description:
 */

public class LayoutActivity extends BaseClsActivity {
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
	private int mCount = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_linearlayout);
        init();
//		int badgeCount = 1;
//		ShortcutBadger.applyCount(LayoutActivity.this, badgeCount); //for 1.1.4+
//		ShortcutBadger.with(getApplicationContext()).count(badgeCount); //for 1.1.3
    }

    private void init() {
		btnSetBadge = (Button) findViewById(R.id.btn_set_badge);



		btnSetBadge.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//设置应用在桌面上显示的角标
				if (!Build.MANUFACTURER.equalsIgnoreCase(MobileBrand.XIAOMI)) {
					BadgeNumberManager.from(LayoutActivity.this).setBadgeNumber(10);
					Toast.makeText(LayoutActivity.this, "设置桌面角标成功", Toast.LENGTH_SHORT).show();
				} else {
					btnSetBadge.postDelayed(new Runnable() {
						@Override
						public void run() {
							setXiaomiBadgeNumber();
						}
					},3000);
					//小米手机如果在应用内直接调用设置角标的方法，设置角标会不生效,因为在退出应用的时候角标会自动消除
					//这里先退出应用，延迟3秒后再进行角标的设置，模拟在后台收到推送并更新角标的情景
					moveTaskToBack(true);
				}
				setNumber();
			}
		});
	}

	private void setNumber(){
		try {
			String content = "您有" + mCount + "条消息";
			NotificationCompat.Builder builder = new NotificationCompat.Builder(LayoutActivity.this);
			builder.setSmallIcon(R.drawable.ic_check_black_36dp);
			builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
			builder.setTicker("收到" + mCount + "条消息");
			builder.setWhen(System.currentTimeMillis());
			Intent intent = new Intent(LayoutActivity.this, MainActivity.class);

			intent.setAction(LayoutActivity.this.getPackageName());
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			PendingIntent pi = PendingIntent.getActivity(LayoutActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			builder.setContentIntent(pi);
			builder.setContentTitle(getResources().getText(R.string.app_name));
			builder.setContentText(content);

			final Notification n = builder.build();
			int defaults = Notification.DEFAULT_LIGHTS;

			defaults |= Notification.DEFAULT_SOUND;

			defaults |= Notification.DEFAULT_VIBRATE;


			n.defaults = defaults;
			n.flags = Notification.FLAG_SHOW_LIGHTS | Notification.FLAG_AUTO_CANCEL;
			if (mCount > 0) {
				DifferentNotifications.showBubble(LayoutActivity.this,n,MQTT_IM_NOTIFICATION_ID, mCount);
			}else {
				DifferentNotifications.hideBubble(LayoutActivity.this,n,MQTT_IM_NOTIFICATION_ID);
			}
		} catch (Resources.NotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 小米
	 * @param notification
	 * @param number
	 * 在调用NotificationManager.notify(notifyID, notification)这个方法之前先设置角标显示的数目
	 */
	public static void setBadgeNumber(Notification notification, int number) {
		try {
			Field field = notification.getClass().getDeclaredField("extraNotification");
			Object extraNotification = field.get(notification);
			Method method = extraNotification.getClass().getDeclaredMethod("setMessageCount", int.class);
			method.invoke(extraNotification, number);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setXiaomiBadgeNumber() {
		NotificationManager notificationManager = (NotificationManager) LayoutActivity.this.
				getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new NotificationCompat.Builder(LayoutActivity.this)
				.setSmallIcon(LayoutActivity.this.getApplicationInfo().icon)
				.setWhen(System.currentTimeMillis())
				.setContentTitle("推送标题")
				.setContentText("我是推送内容")
				.setTicker("ticker")
				.setAutoCancel(true)
				.build();
		//相邻的两次角标设置如果数字相同的话，好像下一次会不生效
		BadgeNumberManagerXiaoMi.setBadgeNumber(notification, mCount++);
		notificationManager.notify(1000, notification);
		Toast.makeText(LayoutActivity.this, "设置桌面角标成功", Toast.LENGTH_SHORT).show();

	}

}
