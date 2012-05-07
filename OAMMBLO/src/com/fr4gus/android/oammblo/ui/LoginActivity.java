package com.fr4gus.android.oammblo.ui;



import com.fr4gus.android.oammblo.OammbloApp;
import com.fr4gus.android.oammblo.R;
import com.fr4gus.android.oammblo.data.TwitterService;
import com.fr4gus.android.oammblo.util.LogIt;
import android.view.Window;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class LoginActivity extends OammbloActivity  {
	Bundle bundle;
	TwitterService twitterService;
	
    public void onCreate(Bundle savedInstanceState) {    	      
    	super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);
        
        twitterService = OammbloApp.getInstance().getTwitterService();

    }

    public void authenticate(View view) {
    	    	    	
        // Si no existe autenticacion previa
        if (! twitterService.checkForSavedLogin(this) ) {
            LogIt.d(this, "Solicitando autenticacion...");
            try
            {
            	twitterService.OAuthAuthorize(this);            	
            }
            catch (Exception e){
            	TextView  txt = (TextView)findViewById(R.id.Login_txt_error);
            	txt.setText(e.getCause ().toString());
            }                       
        } 
        else 
        {
            LogIt.d(this, "Datos de autenticacion previa, existente, iniciando aplicacion con normalidad");
            startActivityByClass(DashBoardActivity.class);
            finish();
        }
        
    }

    @Override
    protected void onResume() {

    	super.onResume();
        Intent intent = getIntent();
        Uri uri = intent.getData();
        
        if (uri != null) {
            LogIt.d(this, "Posible authentication data received");
            if (twitterService.RetrieveAccessToken(this, uri) ) {
                startActivityByClass(DashBoardActivity.class);
                finish();
                
            } else {
                // Algun problema para autenticarnos
                toast(R.string.login_authentication_error);
            }
            
        } else {
            // En caso de que no exista el URi (por ejemplo que se 
            // inicie la actividad por primera vez,
            // forzar la autenticacion para verificar que exista
            authenticate(null);
        }
    }
    

}
