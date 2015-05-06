package com.benshen.lee.hacknews.ui;

import com.benshen.lee.hacknews.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebViewActivity extends Activity {
	
	private WebView webView = null;
	private WebSettings webSettings = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.webview);
		
		webView = (WebView)findViewById(R.id.webview);
		webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		
		//webSettings.setUseWideViewPort(true);
		//webSettings.setLoadWithOverviewMode(true);
		webSettings.setBuiltInZoomControls(true);
		webSettings.setDisplayZoomControls(false);
		
		
		
		Intent intent = this.getIntent();
		if (intent != null) {
			String url = intent.getStringExtra("URL");
			webView.loadUrl(url);
		}
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	
	@Override
	protected void onStart() {
		super.onStart();
	}
}