package com.benshen.lee.hacknews.database;

import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.benshen.lee.hacknews.database.TopStoryModel.TopStory;


public class ItemModel extends Model {
	
	public static void add(String id, String by, String score, String title, String time, String url, String type) {
		Item item = new Item(id, by, score, title, time, url, type);
		item.save();
	}
	
	public static List<Item> all() {
		return new Select().from(Item.class).execute();
	}
	
	public static Item getById(String id) {
		return new Select().from(Item.class).where("idNum = ?", id).executeSingle();
	}
	
	public static boolean isExists(String id) {
		TopStory item = new Select().from(Item.class).where("idNum = ?", id).executeSingle();
		if (item == null) {
			return false;
		} else {
			return true;
		}
	}
	
	
	@Table(name="Item")
	public static class Item extends Model {
		
		@Column(name = "idNum")
		public String idNum;
		
		@Column(name = "by")
		public String by;
		
		@Column(name = "score")
		public String score;
		
		@Column(name = "title")
		public String title;
		
		@Column(name = "time")
		public String time;
		
		@Column(name = "url")
		public String url;
		
		@Column(name = "type")
		public String type;
		
		public Item() {
			super();
		}
		
		public Item(String id, String by, String score, String title, String time, String url, String type) {
			super();
			
			this.idNum = id;
			this.by = by;
			this.score = score;
			this.title = title;
			this.time = time;
			this.url = url;
			this.type = type;
		}
		
		public List<Item> items() {
			return getMany(Item.class, "Item");
		}
		
	}
}
