package com.fr4gus.android.oammblo.ui;

import com.fr4gus.android.oammblo.R;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

/**
 * Shows App logo for few seconds.
 * 
 * @author Franklin Garcia
 * Created Mar 25, 2012
 */
public class SplashActivity extends OammbloActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        LoginAsyncTask task = new LoginAsyncTask();
        task.execute();
    }
    
    
    class LoginAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            
            try 
            {
                Thread.sleep(2000);
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
