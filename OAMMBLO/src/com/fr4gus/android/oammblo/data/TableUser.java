package com.fr4gus.android.oammblo.data;

import android.content.ContentValues;
import android.content.Context;


public class TableUser extends DataBaseHelper {
	
	//NOMBRE DE LA TABLA.
	public static final String TABLE_NAME="user";
	//CAMPOS DE LA TABLA.
	public static final String ID="user_id";
	public static final String SCREEN_NAME="screen_name";
	public static final String NAME="name";
	public static final String IMAGE_URL="image_url";
	public static final String IMAGE_LAST_MODIFICATION="image_last_modification";
	
	String [] columns  = new String []{ID, SCREEN_NAME, NAME, IMAGE_URL, IMAGE_LAST_MODIFICATION};
		
	String _id="";
	String screen_name="";
	String name="";
	String image_url = "";
	String image_last_modification="";
	
	
	public TableUser(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}	
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getScreen_name() {
		return screen_name;
	}
	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public String getImage_last_modification() {
		return image_last_modification;
	}
	public void setImage_last_modification(String image_last_modification) {
		this.image_last_modification = image_last_modification;
	}
	
	
	public void insert (){
		ContentValues nuevoRegistro = new ContentValues();
			
		nuevoRegistro.put(TableUser.SCREEN_NAME, this.getScreen_name() );
		nuevoRegistro.put(TableUser.NAME, this.getName());
		nuevoRegistro.put(TableUser.IMAGE_URL, this.getImage_url());
		nuevoRegistro.put(TableUser.IMAGE_LAST_MODIFICATION, this.getImage_last_modification());

		this.insert(TableUser.TABLE_NAME, null, nuevoRegistro);
				
	}	
	
}
