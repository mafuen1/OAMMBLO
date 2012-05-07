package com.fr4gus.android.oammblo.ui;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.fr4gus.android.oammblo.R;

public class DashBoardActivity extends OammbloActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        
    }
    
    
    public void openTimeLine (View view){    	    	 
		Intent intent = new Intent ();
		intent.setClass (this, TimelineActivity.class); 								
		startActivity(intent);	    	
    }
    
    
    
    
}
