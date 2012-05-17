package com.fr4gus.android.oammblo.data;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_PATH = "/data/data/ice.go.cr/databases/";
	private static final String DATABASE_NAME = "MQReasonCodes.db";
	//private static final int SCHEME_VERSION = 1;
	
	//Nombres de las columnas.
	static final String ID = "_id";
	static final String REASONCODE = "reasonCode";
	static final String DESCRIPTION = "description";
	static final String EXPLANATION = "explanation";
	static final String COMPLETION_CODE = "completionCode";
	static final String PROGRAMMER_RESPONSE = "programmerResponse";
	
	
	public static final String TABLE_NAME = "codes";
	

	
	public SQLiteDatabase dbSQLite;
	private final Context myContext;

	
	public DataBaseHelper (Context context){
		super (context,DATABASE_NAME,null,3);	
		this.myContext = context;
	}
	
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    } 
	
	/**
     * Creates a empty database on the system and rewrites it with your own
	database.
     * */ 	
	public void createDatabase()throws SQLException{
		boolean dbExits = DBExist();
		
		if (!dbExits){
			this.getReadableDatabase();
			try
			{
				copyDBFromResource();
			} 
			catch (IOException e) 
			{
				throw new Error("Error copying database"); 
			}              
          } 		
	}
	

    /**
     * Check if the database already exist to avoid re-copying the file each
	time you open the application.
     * @return true if it exists, false if it doesn't
     */ 		
	private boolean DBExist() {
		SQLiteDatabase db = null;
		
		try{
			String databasepath = DATABASE_PATH  + DATABASE_NAME;
			db = SQLiteDatabase.openDatabase(databasepath, null, SQLiteDatabase.OPEN_READONLY);
		}catch (SQLiteException e)
		{
			Log.e("SQLError", "database not found.");
		}
		
		if (db != null)
			db.close();
		
		return db != null ? true : false;
		
	}
	
    /**
     * Copies your database from your local assets-folder to the just
	created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transferring bytestream.
     * */ 	
	private void copyDBFromResource()throws IOException{
		InputStream  inputStream = null;
		OutputStream outputStream  = null;
		String dbFilePath = DATABASE_PATH + DATABASE_NAME;
		
		inputStream =  myContext.getAssets().open(DATABASE_NAME);
		outputStream = new FileOutputStream  (dbFilePath);
		
        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer))>0){
        	outputStream.write(buffer, 0, length);
        } 
		
		outputStream.flush();
		outputStream.close();
		inputStream.close();		
	}
	
	
	public void openDataBase ()throws  SQLException{
		String myPath = DATABASE_PATH + DATABASE_NAME;
		dbSQLite = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	}
	
	public synchronized void close()
	{
		
		if (dbSQLite != null)
		{
			dbSQLite.close();
		}
		super.close();
	}
	
	
	public Cursor getCursor (){
		String [] asColumnstoReturn  = new String []{ID, REASONCODE, DESCRIPTION, EXPLANATION, PROGRAMMER_RESPONSE};
				
		//Cursor c = dbSQLite.rawQuery("SELECT REASONCODE,DESCRIPTION FROM CODES ORDER BY REASONCODE ASC",null);
		Cursor c = dbSQLite.query (TABLE_NAME, asColumnstoReturn, null, null, null, null, "_id ASC");
		return c;
	}

	public Cursor getCursor (String reason_code){		
		String[] args={reason_code};				
		Cursor c = dbSQLite.rawQuery("SELECT _ID, REASONCODE, DESCRIPTION, EXPLANATION, PROGRAMMERRESPONSE FROM CODES WHERE REASONCODE=?",args);
		return c;
	}	
	
	public String getName (Cursor c){
		return c.getString(1);
	}
	
	
}
