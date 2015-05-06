package com.benshen.lee.hacknews.database;

import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;



public class TopStoryModel extends Model {
	
	public static void add(String id) {
		//Logger.d("[TopStoryModel] thread id: " + Thread.currentThread().getId()); 
		// it is running under the UI thread
		TopStory item = new TopStory(id);
		item.save();
	}
	
	public static List<TopStory> all() {
		return new Select().from(TopStory.class).execute();
	}
	
	public static boolean isExists(String id) {
		TopStory item = new Select().from(TopStory.class).where("idNum = ?", id).executeSingle();
		if (item == null) {
			return false;
		} else {
			return true;
		}
	}
	
		
	@Table(name="TopStory")
	public static class TopStory extends Model {
		@Column(name = "idNum")
		public String idNum;
		
		public TopStory() {
			super();
		}
		
		public TopStory(String id) {
			super();
			this.idNum = id;
		}
	}
}