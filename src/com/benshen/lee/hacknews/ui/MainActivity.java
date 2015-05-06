package com.benshen.lee.hacknews.ui;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.benshen.lee.hacknews.util.Logger;
import com.benshen.lee.hacknews.adapter.Item;
import com.benshen.lee.hacknews.adapter.ItemAdapter;
import com.benshen.lee.hacknews.database.ItemModel;
import com.benshen.lee.hacknews.service.HNServiceHelper;
import com.benshen.lee.hacknews.R;
import com.benshen.lee.hacknews.ui.WebViewActivity;

public class MainActivity extends Activity {
	
	private List<Item> mItemList = null;
	private ItemAdapter mItemAdapter = null;
	private ListView mListView = null;
	private BroadcastReceiver mRequestReceiver = null;
	private long mRequestId;
	private HNServiceHelper mHNServiceHelper = null;
	private static UpdatingUIHandler handler = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Logger.d("MainActivity onCreate");
		
		mItemList = new ArrayList<Item>();		
		mListView = (ListView)this.findViewById(R.id.list_view);		
		mItemAdapter = new ItemAdapter(this, mItemList);
		mListView.setAdapter(mItemAdapter);
		
		mListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				// TODO Auto-generated method stub
				int id = arg2;
				Item item = mItemList.get(id);
				String url = item.getUrl();
				
				Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
				intent.putExtra("URL", url);
				MainActivity.this.startActivity(intent);				
			}
			
		});
		
		handler = new UpdatingUIHandler(this);
		handler.sendEmptyMessage(0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.action_refresh:
			Logger.d("action refresh clicked");			
			mRequestId = mHNServiceHelper.getItems();
			return true;
		default:
			return super.onContextItemSelected(item);
		}
		
	}

	@Override
	public void onResume() {
		super.onResume();

		Logger.d("[MainActivity] thread id: " + Thread.currentThread().getId());
		
		
		// register broadcast receiver
		IntentFilter filter = new IntentFilter(HNServiceHelper.ACTION_REQUEST_RESULT);
		mRequestReceiver = new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				long resultRequestId = intent.getLongExtra(HNServiceHelper.EXTRA_REQUEST_ID, 0);

				Logger.d("Received intent" + intent.getAction() + ", request id: " + resultRequestId);

				if (resultRequestId == mRequestId) {
					Logger.d("result is for our request id");

					int resultCode = intent.getIntExtra(HNServiceHelper.EXTRA_RESULT_CODE, 0);
					Logger.d("result code: " + resultCode);

					if (resultCode == 200) {
						Logger.d("updating UI with new data");
						
						// let handler to update info
						handler.sendEmptyMessage(1);						
					} else {
						// do nothing
					}
				} else {
					Logger.d("result is not for our request id");
				}
			}
		};
		
		mHNServiceHelper = HNServiceHelper.getInstance(this);
		this.registerReceiver(mRequestReceiver, filter);

		// get items, this is the first time
		// mRequestId = mHNServiceHelper.getItems();

	}

	@Override
	public void onPause() {
		super.onPause();

		// unregister broadcast recevier
		if (mRequestReceiver != null) {
			try {
				this.unregisterReceiver(mRequestReceiver);
			} catch (IllegalArgumentException e) {
				Logger.e(e.getLocalizedMessage());
			}
		}	
	}

	public void getItemsFromContentProvider() {
	}

	
	static class UpdatingUIHandler extends Handler {
		private final WeakReference<MainActivity> activity;
		
		UpdatingUIHandler(MainActivity activity) {
			this.activity = new WeakReference<MainActivity>(activity);
		}
		
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			Logger.d("[UpdateUIHandler] thread id: " + Thread.currentThread().getId());
			Logger.d("I'm a handler, I received a message.what: " + msg.what);
			
			MainActivity activity = this.activity.get();
			
			if (activity != null) {
				// update ui here
				switch(msg.what) {
				case 0:
					//activity.addStubData(activity.mItemList);
					//break;
				case 1:
					activity.mItemList.clear();
					// get data from content provider
					activity.getItemsFromContentProvider();
					
					List<com.benshen.lee.hacknews.database.ItemModel.Item> items= ItemModel.all();
					
					Logger.d("items size: " + items.size());

					int i;
					for (i = 0; i < items.size(); i++) {
						com.benshen.lee.hacknews.database.ItemModel.Item item = items.get(i);
						
						
						Item it = new Item();
						it.setAuthor(item.by);
						it.setTime(item.time);
						it.setTitle(item.title);
						it.setId(item.idNum);
						it.setUrl(item.url);
						it.setType(item.type);
						it.setScore(item.score);
						
						activity.mItemList.add(it);
						
					}
					// update ui here
					// ...
					break;
				default:
					break;
				}
				
				activity.mItemAdapter.notifyDataSetChanged();
				
			} else {
				Logger.d("Oh, MainActivity is null");
			}
			
		}
	}


}
