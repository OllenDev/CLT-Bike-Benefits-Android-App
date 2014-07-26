package ollendev.com.charlottespokespeople;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;
import java.util.WeakHashMap;

import icepick.Icepick;
import icepick.Icicle;

public class HomeActivity extends Activity {

    private static final String TAG = HomeActivity.class.getSimpleName();

    // Fields for helping process mapFragment and location changes
    private WeakHashMap<Marker, String> mMarkers;
    private String selectedObjectId;

    @Icicle String username;

    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_home);

        mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
        GoogleMap map = mapFragment.getMap();
        map.setMyLocationEnabled(true);
        LatLng myLocation = new LatLng(35.227406,-80.838959);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 11.0f));
        mMarkers = new WeakHashMap<Marker, String>();
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                String objectId = mMarkers.get(marker);
                if (objectId != null) {
                    ParseQuery<Deal> dealParseQuery = Deal.getQuery();
                    try {
                        Deal deal = dealParseQuery.get(objectId);
                        // TODO show card
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        doMapQuery();
//        mapFragment.getMap().setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
//            public void onCameraChange(CameraPosition position) {
//                // When the camera changes, update the query
//                doMapQuery();
//            }
//        });

//        ParseObject testObject = new ParseObject("TestObject");
//        testObject.put("foo", "bar");
//        testObject.saveInBackground();
//
//        ParseQuery<ParseObject> query = ParseQuery.getQuery("Placemarks");
//        query.findInBackground(new FindCallback<ParseObject>() {
//            public void done(List<ParseObject> scoreList, ParseException e) {
//                if (e == null) {
//                    Log.d(TAG, "Retrieved " + scoreList.size() + " scores");
//                } else {
//                    Log.d(TAG, "Error: " + e.getMessage());
//                }
//            }
//        });

    }

    /*
     * Set up the query to update the mapFragment view
     */
    private void doMapQuery() {
        // Create the mapFragment Parse query
        ParseQuery<Deal> mapQuery = Deal.getQuery();
        // Set up additional query filters
        mapQuery.orderByDescending("createdAt");
        // Kick off the query in the background
        mapQuery.findInBackground(new FindCallback<Deal>() {
            @Override
            public void done(List<Deal> objects, ParseException e) {
                if (e != null) {
                    Log.d(TAG, "An error occurred while querying for mapFragment posts.", e);
                    return;
                }
                // Loop through the results of the search
                for (Deal deal : objects) {
                        // Set up the mapFragment marker's location
                        MarkerOptions markerOpts =
                                new MarkerOptions().position(new LatLng(deal.getLocation().getLongitude(), deal
                                        .getLocation().getLatitude()));

                        // Display a green marker with the post information
                        markerOpts =
                                markerOpts.title(deal.getName()).snippet(deal.getDealText())
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                        // Add a new marker
                        Marker marker = mapFragment.getMap().addMarker(markerOpts);
                        mMarkers.put(marker, deal.getObjectId());
                        if (deal.getObjectId().equals(selectedObjectId)) {
                            marker.showInfoWindow();
                            selectedObjectId = null;
                        }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }
}
