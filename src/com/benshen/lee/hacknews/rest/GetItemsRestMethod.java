package com.benshen.lee.hacknews.rest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.benshen.lee.hacknews.processor.ItemsProcessorCallback;
import com.benshen.lee.hacknews.util.Logger;
import com.benshen.lee.hacknews.rest.RestfulMethodHelper;
import com.benshen.lee.hacknews.database.*;

public class GetItemsRestMethod {
	
	private Context context;
	private ItemsProcessorCallback callback;
	
	public GetItemsRestMethod(Context ctx) {
		context = ctx;
	}
	
	public void excute(ItemsProcessorCallback cb) {
		Logger.d("[GetItemsRestMethod] thread id: " + Thread.currentThread().getId());
		
		callback = cb;
		
		// do the network requests
		RequestQueue queue = RestfulMethodHelper.getInstance(context).getRequestQueue();
		String url = "https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty";
		
		JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

					@Override
					public void onResponse(JSONArray response) {
						// TODO Auto-generated method stub
						Logger.d("Json response: " + response.toString());
						
						int i;
						for (i = 0; i < response.length(); i++) {
							try {
								String id = response.getString(i);
								//int id = response.getInt(i);
								//Logger.d("id: " + id);
								
								if (!TopStoryModel.isExists(id)) {
									TopStoryModel.add(id); // block the UI thread
									getItemsDetail(id);
								}
								
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						// update content provider
						
						
						// when job done, do call callback to service
						callback.send(200);
						
					}
			}, new Response.ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError arg0) {
					// TODO Auto-generated method stub
					Logger.d("json response error: " + arg0.toString());
					callback.send(400);
				}

		});
		
		queue.add(request);
	}
	
	
	protected void getItemsDetail(String id) {
		// do the network requests
		RequestQueue queue = RestfulMethodHelper.getInstance(context).getRequestQueue();
		String url = "https://hacker-news.firebaseio.com/v0/item/" + id +  ".json?print=pretty";
		JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				// TODO Auto-generated method stub
				
				String id=null, title=null, by=null, score=null, time = null, url=null, type=null;				
				
				try {
					id = arg0.getString("id");
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					score = arg0.getString("score");
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					time = arg0.getString("time");
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					url = arg0.getString("url");
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					type = arg0.getString("type");
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					title = arg0.getString("title");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					by = arg0.getString("by");
					Logger.d("by: " + by);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				ItemModel.add(id, by, score, title, time, url, type);
				
			}
			
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		queue.add(request);
	}
	
}