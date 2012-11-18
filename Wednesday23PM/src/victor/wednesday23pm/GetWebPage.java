package victor.wednesday23pm;

import android.app.Activity;


import android.support.v4.app.FragmentActivity;

import android.content.Intent;

import android.os.Bundle;
// used for interacting with user interface
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.view.MenuItem;
import android.view.View;
// used for passing data 
import android.os.Handler;
import android.os.Message;
// used for connectivity
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;

import android.content.Intent;
import android.util.Log;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import android.support.v4.app.NavUtils;
import android.telephony.gsm.*;

import android.os.Vibrator;

import victor.wednesday23pm.FirstItemListActivity;
import victor.wednesday23pm.FirstItemDetailActivity;


public class GetWebPage extends Activity {
    /** Called when the activity is first created. */

    Handler h;

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getwebpage);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        final EditText eText = (EditText) findViewById(R.id.address);
        final TextView tView = (TextView) findViewById(R.id.pagetext);

        this.h = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                // process incoming messages here
                switch (msg.what) {
                    case 0:
                    	tView.append((String) msg.obj);
                    	break;
                }
                super.handleMessage(msg);
            }
        };
        final Button button = (Button) findViewById(R.id.ButtonGo);
        button.setOnClickListener(new Button.OnClickListener() 
        {
            public void onClick(View v) {
            	tView.setText("");
            	
            	new Thread(new Runnable() {
            		   public void run() {
            		   
            		        // your logic
            	HttpURLConnection urlConnection = null;
            	try	{
                	URL url = new URL(eText.getText().toString());
                	urlConnection = (HttpURLConnection) url.openConnection();

                // Perform action on click

                	if (!url.getHost().equals(urlConnection.getURL().getHost())) {
                	       // we were redirected! Kick the user out to the browser to sign on?
                		Message lmsg;
                        lmsg = new Message();
                        lmsg.obj = "On va pas faire la requete parce que le host est : " + url.getHost();
                        lmsg.what = 0;
                        GetWebPage.this.h.sendMessage(lmsg);
                	}

        		    // Get the response
       		    // InputStream in = new BufferedInputStream(urlConnection.getInputStream());
       		     //readStream(in);

                    InputStreamReader isr = new InputStreamReader(urlConnection.getInputStream());
                    BufferedReader rd = new BufferedReader(isr);
                    String line = "";
                    while ((line = rd.readLine()) != null) {
                		Message lmsg;
                        lmsg = new Message();
                        lmsg.obj = line;
                        lmsg.what = 0;
                        GetWebPage.this.h.sendMessage(lmsg);
                    }
//                    SmsManager sm = SmsManager.getDefault();
                    //sm.sendDataMessage("test",null,(short)5556,"hello there".getBytes(),null,null);
//                    sm.sendDataMessage("test",null,"hello there",null,null);

            	}
            	catch (Exception e)
            	{
            		String str = e.getMessage() == null? "" : e.getMessage() + e.getCause(); 
            		Log.d("Get", str + e.toString());
            		Message lmsg;
                    lmsg = new Message();
                    lmsg.obj = "La requete a crashe parce que : " + str;
                    lmsg.what = 0;
                    GetWebPage.this.h.sendMessage(lmsg);
            	}
            	finally
            	{
            		if (urlConnection != null)
            		    urlConnection.disconnect();
            	}
            }
     		}).start();
            }
            
        });

        final Button buttonBack = (Button) findViewById(R.id.ButtonBack);
        buttonBack.setOnClickListener(new Button.OnClickListener() {
    	    @Override
        	public void onClick(View v) {
    	    	Log.d("BB", "Taped");

                Intent detailIntent = new Intent(v.getContext(), FirstItemDetailActivity.class);
                detailIntent.putExtra(FirstItemDetailFragment.ARG_ITEM_ID, "On vient de GWP");
                startActivity(detailIntent);

    	    	//Intent intentParent = NavUtils.getParentActivityIntent(this);
    	    	//NavUtils.navigateUpTo(this, );
    	    	//NavUtils.navigateUpTo(this, new Intent(this, FirstItemListActivity.class));
    	    	
        	}
        });

        final Button buttonScan = (Button) findViewById(R.id.ButtonScan);
        buttonScan.setOnClickListener(new Button.OnClickListener() {
    	    @Override
        	public void onClick(View v) {
    	    	Log.d("BS", "Taped");

        	}
        });

        final Button buttonVibe = (Button) findViewById(R.id.ButtonVibe);
        buttonVibe.setOnClickListener(new Button.OnClickListener() {
    	    @Override
        	public void onClick(View v) {
    	    	Log.d("BV", "Taped");
    	    	// Get instance of Vibrator from current Context
    	    	Vibrator vibe = (Vibrator) getSystemService(VIBRATOR_SERVICE);
    	    	 
    	    	// Vibrate for 300 milliseconds
    	    	vibe.vibrate(300);
//                Intent detailIntent = new Intent(VIBRATOR)
                //detailIntent.putExtra(FirstItemDetailFragment.ARG_ITEM_ID, id);
//                startActivity(detailIntent);

    	    	//Intent intentParent = NavUtils.getParentActivityIntent(this);
    	    	//NavUtils.navigateUpTo(this, );
    	    	//NavUtils.navigateUpTo(this, new Intent(this, FirstItemListActivity.class));
    	    	
        	}
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, FirstItemListActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
