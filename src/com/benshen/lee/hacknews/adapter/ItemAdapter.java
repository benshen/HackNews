package com.benshen.lee.hacknews.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.benshen.lee.hacknews.adapter.Item;
import com.benshen.lee.hacknews.R;
//import com.benshen.lee.hacknews.util.Logger;

public class ItemAdapter extends BaseAdapter {

	private List<Item> mItemList = null;
	private LayoutInflater mInflater = null;
	
	public ItemAdapter(Context context, List<Item> item) {
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mItemList = item;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		//Logger.d("adapter getCount");		
		return mItemList.size();
		
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		//Logger.d("adapter getItem");
		return mItemList.get(arg0);		
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		//Logger.d("adapter getItem");
		return 0;
	}

	@Override
	public View getView(int position, View convertview, ViewGroup group) {
		// TODO Auto-generated method stub
		//Logger.d("adapater getView");
		
		View view = null;
		ViewHolder holder = null;
		
		if (convertview == null || convertview .getTag() == null) {
			view = mInflater.inflate(R.layout.item, null);
			holder = new ViewHolder(view);
			view.setTag(holder);
		} else {
			view = convertview;
			holder = (ViewHolder)view.getTag();
		}
		
		Item item = (Item)getItem(position);
		holder.title.setText(item.getTitle());
		holder.author.setText(item.getAuthor());
		holder.score.setText(item.getScore());
		holder.time.setText(item.getTime());
		holder.id.setText(item.getId());
		
		return view;
	}
	
	
	class ViewHolder {
		TextView title;
		TextView author;
		TextView score;
		TextView time;
		TextView id;
		
		public ViewHolder(View view) {
			this.title = (TextView)view.findViewById(R.id.title);
			this.author = (TextView)view.findViewById(R.id.author);
			this.score = (TextView)view.findViewById(R.id.score);
			this.time = (TextView)view.findViewById(R.id.time);
			this.id = (TextView)view.findViewById(R.id.id);
		}
	}
}
