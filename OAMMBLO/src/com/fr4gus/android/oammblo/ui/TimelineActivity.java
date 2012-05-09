package com.fr4gus.android.oammblo.ui;

import java.util.Date;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.fr4gus.android.oammblo.OammbloApp;
import com.fr4gus.android.oammblo.R;
import com.fr4gus.android.oammblo.bo.Tweet;
import com.fr4gus.android.oammblo.data.TwitterService;
import com.fr4gus.android.oammblo.util.BackgroundTask;

public class TimelineActivity extends OammbloActivity {
    private ListView mTimeline;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeline);

        mTimeline = (ListView) findViewById(R.id.listaTweets);
        context = this;
        
       // TwitterService service = OammbloApp.getInstance().getTwitterService();
       // service.verifyCredentials(this);
        

        new BackgroundTask() {
            List<Tweet> tweets;
            
            @Override
            public void work() {
                TwitterService service = OammbloApp.getInstance().getTwitterService();
                service.verifyCredentials(context);
                tweets = service.getTimeline();                
            }
            
            @Override
            public void error(Throwable error) {
                toast("No es posible mostrar los Tweets...");
            }
            
            @Override
            public void done() {
                mTimeline.setAdapter(new TweetAdapter(tweets));                
            }
        };
                
                    
    }
    
    public void openTweet (View view){    	    	 
		/*Intent intent = new Intent ();
		intent.setClass (this, TweetActivity.class); 								
		startActivity(intent);*/
    	
    	final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.tweet);
		dialog.setTitle("Enviar Tweet...");

		

    	ImageButton dialogenviarButton = (ImageButton) dialog.findViewById(R.id.tweetImageButtonEnviar); 
    	
    	dialogenviarButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				
		    	EditText  editmensaje = (EditText) dialog.findViewById(R.id.tweetMensaje);    	
		    	final String mensaje = editmensaje.getText().toString();

		    	new BackgroundTask() {
		    		List<Tweet> tweets;
		    		
		            @Override
		            public void work() {
		                TwitterService service = OammbloApp.getInstance().getTwitterService();
		                service.verifyCredentials(context);
		                service.postStatus(mensaje);               
		            }
		            
		            @Override
		            public void error(Throwable error) {
		                toast("No se ha podido enviar el Tweet...");
		            }
		            
		            @Override
		            public void done() {
		            	toast("enviando tweet..."); 
		            	
		                TwitterService service = OammbloApp.getInstance().getTwitterService();
		                service.verifyCredentials(context);
		                tweets = service.getTimeline();  		            	
		            }
		        };  				
			}
		});
    	
		dialog.show();
    	
    }    

    private class TweetAdapter extends BaseAdapter {
        List<Tweet> tweets;

        public TweetAdapter(List<Tweet> timeline) {
            tweets = timeline;
        }

        @Override
        public int getCount() {
            return tweets.size();
        }

        @Override
        public Object getItem(int position) {
            return tweets.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TweetViewHolder holder = null;
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if ( convertView == null) {
                convertView = inflater.inflate(R.layout.timeline_tweet, null);
                holder = new TweetViewHolder();
                holder.message = (TextView) convertView.findViewById(R.id.tweet_mensaje);
                holder.author = (TextView) convertView.findViewById(R.id.tweet_usuario);
                holder.timestamp = (TextView) convertView.findViewById(R.id.tweet_fecha);
                
                convertView.setTag(holder);
            } else {
                holder = (TweetViewHolder) convertView.getTag();
                                
            }
            
            Tweet tweet = tweets.get(position);
            
            holder.message.setText( tweet.getMessage());
            holder.author.setText(tweet.getAuthor());
                        
            
            holder.timestamp.setText(android.text.format.DateFormat.format("yyyy-MM-dd hh:mm:ss",tweet.getTimestamp()));
            return convertView;
        }

    }
    
    private class TweetViewHolder {
        TextView message;
        TextView author;
        TextView timestamp;        
    }

}
