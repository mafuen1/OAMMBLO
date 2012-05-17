package com.fr4gus.android.oammblo.data;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageHelper {
	Bitmap bitmap;
	
	
	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public ImageHelper() {
		super();		
	}
	
	public ImageHelper(Bitmap bitmap) {
		super();
		this.bitmap = bitmap;
	}

	public Bitmap loadImage(String URL, BitmapFactory.Options options)
	{      
	 Bitmap bitmap = null;
	 InputStream in = null;      
	    try {
	        in = openHttpConnection(URL);
	        bitmap = BitmapFactory.decodeStream(in, null, options);
	        in.close();
	    } catch (IOException e1) {
	    }
	    this.bitmap = bitmap;
	    return bitmap;              
	}

	public Bitmap loadImage(String URL)
	{      
	 Bitmap bitmap = null;
	 InputStream in = null;      
	    try {
	        in = openHttpConnection(URL);
	        bitmap = BitmapFactory.decodeStream(in);
	        in.close();
	    } catch (IOException e1) {
	    }
	    this.bitmap = bitmap;
	    return bitmap;              
	}	
	
	
	private InputStream openHttpConnection(String strURL) throws IOException{
	 InputStream inputStream = null;
	 URL url = new URL(strURL);
	 URLConnection conn = url.openConnection();

	 try{
	  HttpURLConnection httpConn = (HttpURLConnection)conn;
	  httpConn.setRequestMethod("GET");
	  httpConn.connect();

	  if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
	   inputStream = httpConn.getInputStream();
	  }
	 }
	 catch (Exception ex)
	 {
	 }
	 return inputStream;
	}
	
	
}
