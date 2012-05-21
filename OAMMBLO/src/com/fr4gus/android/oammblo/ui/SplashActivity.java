package com.fr4gus.android.oammblo.ui;



import com.fr4gus.android.oammblo.OammbloApp;
import com.fr4gus.android.oammblo.R;
import com.fr4gus.android.oammblo.data.DataBaseHelper;
import com.fr4gus.android.oammblo.data.Twitter4JService;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

/**
 * Shows App logo for few seconds.
 * 
 * @author Franklin Garcia
 * Created Mar 25, 2012
 */
public class SplashActivity extends OammbloActivity {

	Context ctx;
	Twitter4JService twitterService;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        this.ctx = this;
        
        twitterService = OammbloApp.getInstance().getTwitterService();
        
        LoginAsyncTask task = new LoginAsyncTask();
        task.execute();
    }
    
    
    class LoginAsyncTask extends AsyncTask<Void, Void, Void> {
    	
        @Override
        protected Void doInBackground(Void... params) {
            
        	long startTime = System.currentTimeMillis();
        	
            try 
            {
/*
            	database = new DataBaseHelper (ctx);	        
    	        database.createDatabase();        
    	        database.openDataBase();        

    	        
    	        if (twitterService.checkForSavedLogin(ctx) ) {
    	        	twitterService.connectTwitter(ctx);
    	        
    	        	twitterService.getTimeline();
    	        }
            	
                         

            */
            
	            long endTime = System.currentTimeMillis();
	            long delay = endTime-startTime;	            
	            long sleep=0;
	            
	            if (delay < 2000) 
	            {
	            	sleep = 2000 - delay;
	            	Thread.sleep(sleep);     
	            }

            } 
            catch (InterruptedException e) 
            {
            	e.printStackTrace();
            }
            
            
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }        
    }        
}
