package com.benshen.lee.hacknews.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import com.benshen.lee.hacknews.processor.ItemsProcessor;
import com.benshen.lee.hacknews.processor.ItemsProcessorCallback;
import com.benshen.lee.hacknews.util.Logger;

public class HNService extends IntentService {

	public static final String METHOD_EXTRA = "METHOD_EXTRA";
	public static final String RESOURCE_TYPE_EXTRA = "RESOURCE_TYPE_EXTRA";
	public static final String SERVICE_CALLBACK = "SERVICE_CALLBACK";
	public static final String ORIGINAL_INTENT_EXTRA = "ORIGINAL_INTENT_EXTRA";

	public static final String METHOD_GET = "GET";
	public static final String METHOD_POSt = "POST";
	public static final String METHOD_PUT = "PUT";

	public static final int RESOURCE_TYPE_ITEMS = 1;
	public static final int REQUEST_INVALID = 400;

	private Intent originalIntent;
	private String method;
	private int resourceType;
	private ResultReceiver callback;

	public HNService() {
		super("HNService");
		// TODO Auto-generated constructor stub
		Logger.d("HNService starts");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		Logger.d("[HNService] thread id: " + Thread.currentThread().getId());
		
		// TODO Auto-generated method stub
		originalIntent = intent;
		method = intent.getStringExtra(METHOD_EXTRA); 
		resourceType = intent.getIntExtra(RESOURCE_TYPE_EXTRA, -1);
		callback = intent.getParcelableExtra(SERVICE_CALLBACK);
		
		switch(resourceType) {
		case RESOURCE_TYPE_ITEMS:
			if (method.equals(METHOD_GET)) {
				ItemsProcessor processor = new ItemsProcessor(this.getApplication());
				processor.getItems(makeItemsProcessorCallback());
			} else {
				callback.send(REQUEST_INVALID, getOriginalIntentBundle());
			}
			break;
		default:
			break;
			
		}
	}	
	
	protected Bundle getOriginalIntentBundle(){		
		Bundle bundle = new Bundle();
		bundle.putParcelable(ORIGINAL_INTENT_EXTRA, originalIntent);
		return bundle;
	}
	
	private ItemsProcessorCallback makeItemsProcessorCallback() {
		ItemsProcessorCallback cb = new ItemsProcessorCallback() {

			@Override
			public void send(int resultCode) {
				// TODO Auto-generated method stub
				if (callback != null) {
					callback.send(resultCode, getOriginalIntentBundle());
				}
			}
			
		};
		return cb;
	}
}
