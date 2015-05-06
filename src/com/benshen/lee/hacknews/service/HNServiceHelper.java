package com.benshen.lee.hacknews.service;

import java.util.UUID;

import android.content.Context;
import android.content.Intent;
import android.os.ResultReceiver;
import android.os.Bundle;

import com.benshen.lee.hacknews.service.HNService;
import com.benshen.lee.hacknews.util.Logger;

public class HNServiceHelper {
	private static HNServiceHelper mInstance = null;
	private Context mContext;
	private static Object lock = new Object();
	
	public static final String EXTRA_REQUEST_ID = "EXTRA_REQUEST_ID";
	public static final String EXTRA_RESULT_CODE = "EXTRA_RESULT_CODE";
	public static final String ACTION_REQUEST_RESULT = "ACTION_REQUEST_RESULT";
	
	
	private static final String REQUEST_ID = "REQUEST_ID";

	private HNServiceHelper(Context ctx) {
		mContext = ctx.getApplicationContext();
	}

	public static HNServiceHelper getInstance(Context ctx) {
		synchronized (lock) {
			if (mInstance == null) {
				mInstance = new HNServiceHelper(ctx);
			}
		}	
		return mInstance;
	}


	public long getItems() {
		Logger.d("[HNServiceHelper] thread id: " + Thread.currentThread().getId());
		
		long requestId = generateRequestId();
		
		ResultReceiver serviceCallback = new ResultReceiver(null) {
			@Override
			protected void onReceiveResult(int resultCode, Bundle resultData) {
				handleGetItemsResponse(resultCode, resultData);
			}
		};
		
		Intent intent = new Intent(this.mContext, HNService.class);
		intent.putExtra(HNService.METHOD_EXTRA, HNService.METHOD_GET);
		intent.putExtra(HNService.RESOURCE_TYPE_EXTRA, HNService.RESOURCE_TYPE_ITEMS);
		intent.putExtra(HNService.SERVICE_CALLBACK, serviceCallback);
		intent.putExtra(REQUEST_ID, requestId);
		
		this.mContext.startService(intent);
		
		return requestId;
	}
	
	public long generateRequestId() {
		long requestId = UUID.randomUUID().getLeastSignificantBits();
		return requestId;
	}
	
	public void handleGetItemsResponse(int resultCode, Bundle resultData) {
		Intent originalIntent = (Intent)resultData.getParcelable(HNService.ORIGINAL_INTENT_EXTRA);
		if (originalIntent != null) {
			long requestId = originalIntent.getLongExtra(REQUEST_ID, 0);
			
			Intent resultBroadcast = new Intent(ACTION_REQUEST_RESULT);
			resultBroadcast.putExtra(EXTRA_REQUEST_ID, requestId);
			resultBroadcast.putExtra(EXTRA_RESULT_CODE, resultCode);
			
			this.mContext.sendBroadcast(resultBroadcast);
		}
	}
}
