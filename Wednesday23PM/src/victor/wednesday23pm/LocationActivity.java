package victor.wednesday23pm;

import android.app.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import java.util.List;
import java.util.Iterator;

import android.location.*;
import android.widget.Toast;
import android.widget.TextView;

import android.text.format.Time;
import android.os.SystemClock;

import android.widget.EditText;

import victor.wednesday23pm.SqlActivity;


public class LocationActivity extends Activity {

    LocationListener mlocListener;
    Boolean mState = false;

    static public long waitTime_ = 5000;

    static public double latitude_ = 0.;
    static public double longitude_ = 0.;
    static public double altitude_ = 0.;
    static public double accuracy_ = 0.;
    static public long time_ = 0;
    static public double speed_ = 0.;

    private TextView tView = null;
    private EditText inputView;

    static public String storeLocAndGetString(Location loc)
    {
        latitude_ = loc.getLatitude();
        longitude_ = loc.getLongitude();
        altitude_ = loc.getAltitude();
        accuracy_ = loc.getAccuracy();
        time_ = loc.getTime();
        speed_ = loc.getSpeed();

        String located = "My current location is: "
        + ", Latitude = " + latitude_
        + ", Longitude = " + longitude_
        + ", Altitude = " + altitude_
        + ", Accuracy = " + accuracy_
        + ", Time = " + time_
        + ", Speed = " + speed_
        + "Decalage Time : " + Long.toString(System.currentTimeMillis() - loc.getTime());

        return located;
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_activity_layout);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        tView = (TextView) findViewById(R.id.textViewLocation);
        inputView = (EditText) findViewById(R.id.editTextTimeDiscretization);

        inputView.setText(Long.toString(waitTime_));

        final Button buttonLocation = (Button) findViewById(R.id.ButtonLocation);
        buttonLocation.setOnClickListener(new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("LBL", "Taped");


            String maString = inputView.getText().toString();
            if (!maString.equals(""))
            {
            	waitTime_ = 5000;
                Long maLong = 5000L;
            	maLong.parseLong(maString);
            	waitTime_ = maLong;
            }
            LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);

            List<String> providers = lm.getAllProviders();

            mlocListener = new MyLocationListener();

            mState = true;
            lm.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);

            Iterator it = providers.iterator();

            while (it.hasNext())
            {
              String value=(String)it.next();
              System.out.println("Value :"+value);
// providers.get(0)
              Location loc = lm.getLastKnownLocation(value);
            if (loc != null)
            {
                String Text = storeLocAndGetString(loc);

                Log.d("LBL", Text);

                Log.d("Located Tap", Text);
                tView.setText("Taped \n" + Text);
                SqlActivity.record();
            }
            else
                Log.d("LBL", "loc is null");

            //GPS_PROVIDER
            
            }
            


        }
    });

		final Button buttonStopLocation = (Button) findViewById(R.id.ButtonStopLocation);
		buttonStopLocation.setOnClickListener(new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("LBSL", "Taped");

            if (mState)
            {
                LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
                lm.removeUpdates(mlocListener);
            }

            mState = false;
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

    /* Class My Location Listener */

    public class MyLocationListener implements LocationListener

    {

    @Override
    public void onLocationChanged(Location loc)
    {
    	String Text = storeLocAndGetString(loc);

    	if (waitTime_ >= 5000)
    		SqlActivity.record();

        Log.d("Located", Text);
        tView.setText(Text);
        {
            LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
            lm.removeUpdates(mlocListener); mState = false; // VR todo 16-11-12 : pas garanti du tout si ca crashe
    	SystemClock.sleep(waitTime_);
        lm.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);  mState = true ;
        }

    //Toast.makeText( getApplicationContext(), Text, Toast.LENGTH_SHORT).show();

    }


    @Override

    public void onProviderDisabled(String provider)

    {

    Toast.makeText( getApplicationContext(),

    "Gps Disabled",

    Toast.LENGTH_SHORT ).show();

    }


    @Override

    public void onProviderEnabled(String provider)

    {

    Toast.makeText( getApplicationContext(),

    "Gps Enabled",

    Toast.LENGTH_SHORT).show();

    }


    @Override

    public void onStatusChanged(String provider, int status, Bundle extras)

    {


    }

    }/* End of Class MyLocationListener */

}
