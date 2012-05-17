package com.fr4gus.android.oammblo.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.os.Environment;

public class ExternalStorage {

	boolean sdDisponible = false;
	boolean sdAccesoEscritura = false;
	
	
	
	public ExternalStorage() {
		
		//Comprobamos el estado de la memoria externa (tarjeta SD)
		String estado = Environment.getExternalStorageState();
		 
		if (estado.equals(Environment.MEDIA_MOUNTED))
		{
		    sdDisponible = true;
		    sdAccesoEscritura = true;
		}
		else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY))
		{
		    sdDisponible = true;
		    sdAccesoEscritura = false;
		}
		else
		{
		    sdDisponible = false;
		    sdAccesoEscritura = false;
		}		
		
		// 
	}



	public boolean isSdDisponible() {
		return sdDisponible;
	}



	public boolean isSdAccesoEscritura() {
		return sdAccesoEscritura;
	}

	
	public void save_to_SD (Bitmap bm, String image_name){

		String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
		String meteoDirectory_path = extStorageDirectory + "/OAMBLO";

		OutputStream outStream = null;
		File file = new File(meteoDirectory_path, "/"+ image_name);
		try {
			outStream = new FileOutputStream(file);
			bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
			outStream.flush();
			outStream.close();


			  //Log.i("Hub", "OK, Image Saved to SD");
			  //Log.i("Hub", "height = "+ bm.getHeight() + ", width = " + bm.getWidth());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			//Log.i("Hub", "FileNotFoundException: "+ e.toString());

		} catch (IOException e) {
			e.printStackTrace();
			// Log.i("Hub", "IOException: "+ e.toString());
		}
	  	 
	}


	
}
