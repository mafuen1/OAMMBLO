package com.fr4gus.android.oammblo.ui;

import java.util.List;

import com.fr4gus.android.oammblo.OammbloApp;
import com.fr4gus.android.oammblo.R;

import com.fr4gus.android.oammblo.data.Twitter4JService;
import com.fr4gus.android.oammblo.util.BackgroundTask;

import android.content.Context;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class TweetActivity extends OammbloActivity  {
    private Context context;
    String mensaje;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweet);
        context=this;           
    }
    
    public void sendTweet (View view){    	    	 
      	
    	EditText  editmensaje = (EditText) findViewById(R.id.tweetMensaje);    	
    	mensaje = editmensaje.getText().toString();
    	
    	new BackgroundTask() {
            
            @Override
            public void work() {
                Twitter4JService service = OammbloApp.getInstance().getTwitterService();
                service.connectTwitter(context);
                service.postStatus(mensaje);               
            }
            
            @Override
            public void error(Throwable error) {
                toast("No se ha podido enviar el Tweet...");
            }
            
            @Override
            public void done() {
            	toast("enviando tweet...");               
            }
        };    	
    
    }   
}
