package com.fr4gus.android.oammblo;

import com.fr4gus.android.oammblo.data.DataBaseHelper;
import com.fr4gus.android.oammblo.data.Twitter4JService;
import android.app.Application;

/**
 * 
 * @author Franklin Garcia
 *
 */
public class OammbloApp extends Application {
    
	Twitter4JService twitterService;
	DataBaseHelper database;	
    static int noOfInstances;
    static OammbloApp _instance=null;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();  
        noOfInstances++;
        twitterService = new Twitter4JService();
        database = new DataBaseHelper (this);	        
        database.createDatabase();        
        database.openDataBase();      
        
        _instance = this;
    }

    @Override
    public void onLowMemory() {
        // TODO Auto-generated method stub
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        // TODO Auto-generated method stub
        super.onTerminate();
    }

    public Twitter4JService getTwitterService() {
        return twitterService;
    }

    public DataBaseHelper getDataBase() {
        return database;
    }    
    

    public static synchronized OammbloApp getInstance() {
        if (_instance == null) {
                _instance = new OammbloApp();
        }
        return _instance;
    }
    
}
