package victor.wednesday23pm;

import android.os.Bundle;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class MyMapActivity extends MapActivity {

	@Override
    protected boolean isRouteDisplayed() {
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        //mMapView = new MapView(this, "0apaAuDHJlMfmcapHtigiSwP7UYpC6pcxnLUtog");
        /*
        try
        {
        MapView mMapView = new MapView(this, "0apaAuDHJlMfmcapHtigiSwP7UYpC6pcxnLUtog");
        mMapView.setBuiltInZoomControls(true);
        }
		  catch (Exception e)
        {
            return;
        }

//        MapView mapView = (MapView) findViewById(R.layout.map_activity);
        //mapView.setBuiltInZoomControls(true);
         * */
    }

}
