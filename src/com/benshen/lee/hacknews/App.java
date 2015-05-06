package com.benshen.lee.hacknews;

import android.app.Application;
import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.benshen.lee.hacknews.util.Logger;

public class App extends Application {
	private static Context mAppContext;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		mAppContext = this.getApplicationContext();
		ActiveAndroid.initialize(this);
		
		Logger.setTag("benshen");
		Logger.d("Application onCreate");
		
		Logger.d("[App] thread id: " + Thread.currentThread().getId());
	}
	
	public static Context getAppContext() {
		return mAppContext;
	}
}