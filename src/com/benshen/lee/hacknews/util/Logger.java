package com.benshen.lee.hacknews.util;

import android.util.Log;

public class Logger {
	
	private static String tag = "default";
	
	public static void setTag(String t) {
		tag = t;
	}
	
	public static String getTag() {
		return tag;
	}
	
	public static void d(String msg) {
		Log.d(tag, msg);
	}
	
	public static void w(String msg) {
		Log.w(tag, msg);
	}
	
	public static void v(String msg) {
		Log.v(tag, msg);
	}
	
	public static void e(String msg) {
		Log.e(tag, msg);
	}
}