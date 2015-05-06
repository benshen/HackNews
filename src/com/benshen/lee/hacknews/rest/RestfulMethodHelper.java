package com.benshen.lee.hacknews.rest;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import android.content.Context;


public class RestfulMethodHelper {
	private static Context context;
	private static RestfulMethodHelper instance;
	private static final Object lock = new Object();
	private RequestQueue requestQueue;
	
	private RestfulMethodHelper(Context ctx) {
		context = ctx;
	}
	
	public static RestfulMethodHelper getInstance(Context context) {
		synchronized(lock) {
			if (instance == null) {
				instance = new RestfulMethodHelper(context);
			}
		}
		return instance;
	}
	
	public RequestQueue getRequestQueue() {
		if (requestQueue == null) {
			requestQueue = Volley.newRequestQueue(context);
		}

		return requestQueue;
	}
}