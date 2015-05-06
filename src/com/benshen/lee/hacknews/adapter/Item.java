package com.benshen.lee.hacknews.adapter;

public class Item {
	
	private String title;
	private String author;
	private String time;
	private String score;
	private String comments;
	private String url;
	private String id;
	private String type;
	
	public Item(String title, String author, String time, String score, String comments) {
		this.title = title;
		this.author = author;
		this.time = time;
		this.score = score;
		this.comments = comments;
	}
	
	public Item() {
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
