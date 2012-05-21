package com.fr4gus.android.oammblo.data;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;

import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;

import com.fr4gus.android.oammblo.OammbloApp;
import com.fr4gus.android.oammblo.bo.Tweet;

import com.fr4gus.android.oammblo.util.LogIt;


public class Twitter4JService   {
    public static final String STORE_KEY = "twitter-store";
    public static final String STORE_TOKEN = "token";
    public static final String STORE_SECRET_TOKEN = "secret-token";

    //Parametros obtenidos de Twitters Developers.
    public static final String CONSUMER_KEY = "A05ex8tdy7tSMLNUTQuFqA";
    public static final String CONSUMER_SECRET = "iQk2uQ4ZdmeQWbkGJPpvV3Gf51IXAKumJaea5xVqlWA";
    public static final String OAUTH_CALLBACK_SCHEME = "x-oauth-twitter";
    private static final String OAUTH_CALLBACK_URL = OAUTH_CALLBACK_SCHEME + "://callback";

    private static final String REQUEST_URL = "https://api.twitter.com/oauth/request_token";											  
    private static final String AUTHORIZE_URL = "https://api.twitter.com/oauth/authorize";
    private static final String ACCESS_URL = "https://api.twitter.com/oauth/access_token";
    
    Context ctx;
    
    // Current user keys
    private static AccessToken accessToken;

    static Twitter twitter;
    
    // Manuel Fuentes
    private OAuthConsumer consumer;
    
    private OAuthProvider provider;
    
    String token;
    String tokenSecret;

    
    public Twitter4JService ()
    {
    	twitter = new TwitterFactory().getInstance();	
    }
    
    
    public boolean checkForSavedLogin(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(STORE_KEY, Context.MODE_PRIVATE);
        String token = prefs.getString(STORE_TOKEN, null);
        String secret = prefs.getString(STORE_SECRET_TOKEN, null);
        
        if (token != null && secret != null) {
            accessToken = new AccessToken(token, secret);
        }
        return accessToken != null;
    }

    
    public void OAuthAuthorize (Context context) {
        try {
            consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);

            provider = new CommonsHttpOAuthProvider(REQUEST_URL,ACCESS_URL, AUTHORIZE_URL);
           
			this.ctx = context;
			
			new OAuthAuthorizeTask().execute();
        
        } catch (Exception e) {
            LogIt.e(this, e, "ERRROR" + e.getMessage());
        }
    }

    public boolean RetrieveAccessToken(Context context, Uri uriData) {
        String verifier = null;
        if (uriData != null && uriData.getScheme().equals(OAUTH_CALLBACK_SCHEME)) {
            LogIt.d(this, "callback: " + uriData.getPath());
            verifier = uriData.getQueryParameter(OAuth.OAUTH_VERIFIER);
            LogIt.d(this, "verifier: " + verifier);
            
            this.ctx = context;
        	new RetrieveAccessTokenTask().execute(verifier);  
        	return true;
        }
        else{
            return false;
        }
    }

    public boolean saveSession(Context context,String token, String secret_token) {
    	
    	try {
        Editor editor = context.getSharedPreferences(STORE_KEY, Context.MODE_PRIVATE).edit();
        editor.putString(STORE_TOKEN, token);
        editor.putString(STORE_SECRET_TOKEN, secret_token);
        return editor.commit();
    	}
    	catch (Exception e){
    		e.printStackTrace();
    		e.getMessage();
    		System.out.print( e.getCause().toString());
    		return false;
    	}
    }
    
    

    public void postStatus (String status){
    	try 
    	{
            twitter.updateStatus(status);           
         } 
    	catch (TwitterException e) 
    	{
    		LogIt.e(this, e, e.getMessage());
        }
    } 
    
    
    
    public List<Tweet> getTimeline() {
        List<Tweet> tweets = new ArrayList<Tweet>();

        DataBaseHelper database = OammbloApp.getInstance().getDataBase();

        try {        	     	

        	List<Status> statuses = twitter.getHomeTimeline();//twitter.getHomeTimeline(new Paging());
            
            for(Status status : statuses) {

            	User user = status.getUser();            	
            	ImageHelper image = new ImageHelper ();
            	
            	Bitmap bm = image.loadImage(user.getProfileImageURL().toString());
            	
            	
            	ExternalStorage sd = new ExternalStorage ();            	
            	if (sd.isSdDisponible() && sd.isSdAccesoEscritura()){
            		sd.save_to_SD(bm, status.getUser().getScreenName());
            	}
            	
            	
            	Tweet tweet = new Tweet(status.getCreatedAt().getTime(), status.getUser().getScreenName(), status.getText(), bm );            	
            	
            	
            	TableTweet tabletweet = new TableTweet ();
            	tabletweet.setUser_id(status.getUser().getScreenName());
            	tabletweet.setText(status.getText());
            	tabletweet.setDatetime(status.getCreatedAt().getTime());
            	database.insertTweet(tabletweet);
            	
            	
                tweets.add(tweet);
            }
            
        } catch (TwitterException e) {
            LogIt.e(this, e, e.getCause().toString() );
        }
        return tweets;
    }
    
    public List<Tweet> getTimeline(Context context)
    {
    	   		 
    	List<Tweet> timeline = new LinkedList<Tweet>();
		Twitter twitter = new TwitterFactory().getInstance();		
		
	    try {	        
	        SharedPreferences prefs = context.getSharedPreferences(STORE_KEY, Context.MODE_PRIVATE);	       
	    		    	
	    	AccessToken accessToken = new AccessToken(prefs.getString(STORE_TOKEN, null), prefs.getString(STORE_SECRET_TOKEN, null));
	    	twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
	    	twitter.setOAuthAccessToken(accessToken);
	    	
	        List<Status> statuses = twitter.getUserTimeline();
	        
	        for (Status status : statuses) {
	            System.out.println("@" + status.getUser().getScreenName() + " + " + status.getText());
	            timeline.add( new Tweet(System.currentTimeMillis(), status.getUser().getScreenName(), status.getText()));
	        }
	       
	    } catch (TwitterException e) {
	        e.getCause().toString();
	        System.out.println("Failed to get timeline: " + e.getMessage());
	        System.exit(-1);
	    }
	    return timeline;
	    
    }
    
    
    public void connectTwitter (Context context)
    {
	    SharedPreferences prefs = context.getSharedPreferences(STORE_KEY, Context.MODE_PRIVATE);	       
		LogIt.d(this, "-------------->"+prefs.getString(STORE_TOKEN, null));	    	
		AccessToken accessToken = new AccessToken(prefs.getString(STORE_TOKEN, null), prefs.getString(STORE_SECRET_TOKEN, null));
		twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		twitter.setOAuthAccessToken(accessToken);
    }

    
    
    /* Responsible for starting the Twitter authorization */
    class OAuthAuthorizeTask extends AsyncTask<Void, Void, String> {
  	    
        protected String doInBackground(Void... params) {
          String authUrl;
          String message = null;
          try {

        	authUrl = provider.retrieveRequestToken(consumer, OAUTH_CALLBACK_URL);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(authUrl));
            ctx.startActivity(intent);
            
          } catch (OAuthMessageSignerException e) {
            message = "OAuthMessageSignerException";
            e.printStackTrace();
          } catch (OAuthNotAuthorizedException e) {
            message = "OAuthNotAuthorizedException";
            e.printStackTrace();
          } catch (OAuthExpectationFailedException e) {
            message = "OAuthExpectationFailedException";
            e.printStackTrace();
          } catch (OAuthCommunicationException e) {
            message = "OAuthCommunicationException";
            e.printStackTrace();
          }
          return message;
        }

        @Override
        protected void onPostExecute(String result) {
          super.onPostExecute(result);
          
        }
      }    
    
    
    /* Responsible for retrieving access tokens from twitter */
    class RetrieveAccessTokenTask extends AsyncTask<String, Void, String> {

      @Override
      protected String doInBackground(String... params) {
        String message = null;
        String verifier = params[0];
        
        try {
          // Get the token
         
          LogIt.d("consumer: "+consumer);
          LogIt.d("provider: "+provider);
          provider.retrieveAccessToken(consumer, verifier);
          token = consumer.getToken();
          tokenSecret = consumer.getTokenSecret();
          consumer.setTokenWithSecret(token, tokenSecret);

          LogIt.d(String.format("verifier: %s, token: %s, tokenSecret: %s", verifier,token, tokenSecret));

          saveSession(ctx, token, tokenSecret);

        } catch (OAuthMessageSignerException e) {
          message = "OAuthMessageSignerException";
          e.printStackTrace();
        } catch (OAuthNotAuthorizedException e) {
          message = "OAuthNotAuthorizedException";
          e.printStackTrace();
        } catch (OAuthExpectationFailedException e) {
          message = "OAuthExpectationFailedException";
          e.printStackTrace();
        } catch (OAuthCommunicationException e) {
          message = "OAuthCommunicationException";
          e.printStackTrace();
        }
        return message;
      }

      @Override
      protected void onPostExecute(String result) {
        super.onPostExecute(result);
        
      }
    }    
     
    
    
     
}
