package com.tgb.activemq;

import java.util.ArrayList;
import java.util.Date;

public class A{
	private String id;
	private String text;
	private Date time;
	private ArrayList<B> list;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
	public ArrayList<B> getList() {
		return list;
	}
	public void setList(ArrayList<B> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "A [id=" + id + ", text=" + text + ", time=" + time + "]";
	}
}
