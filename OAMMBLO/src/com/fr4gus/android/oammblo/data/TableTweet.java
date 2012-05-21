package com.fr4gus.android.oammblo.data;

import android.content.ContentValues;
import android.content.Context;


public class TableTweet {

	//NOMBRE DE LA TABLA.
	public static final String TABLE_NAME="tweet";
	//CAMPOS DE LA TABLA.	
	public static final String ID="_id";
	public static final String USER_ID="user_id";
	public static final String TEXT="text";
	public static final String DATETIME="datetime";
	
	String [] columns  = new String []{ID, USER_ID, TEXT, DATETIME};
	
	String _id="";
	String user_id="";
	String text="";
	long datetime = 0;
	

	
	public TableTweet() {		
		// TODO Auto-generated constructor stub
	}	
	
	public String[] getColumns() {
		return columns;
	}
	public void setColumns(String[] columns) {
		this.columns = columns;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public long getDatetime() {
		return datetime;
	}
	public void setDatetime(long datetime) {
		this.datetime = datetime;
	}
	
	/*
	public void insert (){
		ContentValues nuevoRegistro = new ContentValues();
			
		nuevoRegistro.put(TableTweet.USER_ID, this.getUser_id());
		nuevoRegistro.put(TableTweet.TEXT, this.getText());
		nuevoRegistro.put(TableTweet.DATETIME, this.getDatetime());

		this.insert(TableTweet.TABLE_NAME, null, nuevoRegistro);
				
	}		
*/

	
	
}
