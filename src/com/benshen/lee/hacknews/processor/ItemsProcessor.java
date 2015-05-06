package com.benshen.lee.hacknews.processor;

import android.content.Context;

import com.benshen.lee.hacknews.processor.ItemsProcessorCallback;
import com.benshen.lee.hacknews.rest.GetItemsRestMethod;
import com.benshen.lee.hacknews.util.Logger;


public class ItemsProcessor {
	private Context context = null;

	public ItemsProcessor(Context ctx){
		context = ctx;		
	}
	
	public Context getContext() {
		return context;
	}
	
	public void getItems(ItemsProcessorCallback callback) {
		Logger.d("[ItemsProcessor] thread id: " + Thread.currentThread().getId());
		
		GetItemsRestMethod getItemsRestMethod = new GetItemsRestMethod(context);
		getItemsRestMethod.excute(callback);
	}
}