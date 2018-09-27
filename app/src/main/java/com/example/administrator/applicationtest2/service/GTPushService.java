package com.example.administrator.applicationtest2.service;

import android.content.Context;
import android.util.Log;

import com.example.administrator.applicationtest2.utils.BadgeUtil;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;

/**
 * Author:hepeng
 * Date:2018-09-26
 * Description:
 */

public class GTPushService extends GTIntentService {
	private int nCount = 0;

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
		Log.e(TAG, "onReceiveClientId -> " + "clientid = " + clientid);
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
		nCount++;
		BadgeUtil.showNumber(context,nCount);
	}

	@Override
	public void onNotificationMessageClicked(Context context, GTNotificationMessage msg) {
	}
}
