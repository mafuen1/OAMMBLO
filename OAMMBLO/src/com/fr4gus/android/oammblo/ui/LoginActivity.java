package com.fr4gus.android.oammblo.ui;


import com.fr4gus.android.oammblo.R;
import android.view.Window;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity implements OnClickListener {

	Bundle bundle;		
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.login);
        
        Button boton_entrar = (Button) findViewById (R.id.Login_entrar);
        boton_entrar.setOnClickListener(this);
        
    }

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		open();
	}
    

	
    public void open()
    {	
		Intent intent = new Intent ();
		intent.setClass (this, DashBoardActivity.class); 
		bundle = new Bundle ();	
		intent.putExtras(bundle);							
		startActivity(intent);					    		
    }	
	
}
