package ollendev.com.charlottespokespeople;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.HashMap;
import java.util.List;

import static android.support.v4.view.ViewPager.OnPageChangeListener;

public class MapActivity extends FragmentActivity implements OnPageChangeListener {

    private static final String TAG = MapActivity.class.getSimpleName();

//    // Fields for helping process mapFragment and location changes
    private HashMap<Marker, String> mMarkers; // TODO test this and make sure it's not a memory leak or manually null it out
    private String selectedObjectId;

    private MapFragment mapFragment;
    private GoogleMap mMap;
    private ViewPager mPager;
    private List<Deal> mDeals;
    private Boolean userSelectedMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");

        userSelectedMarker = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mMap = mapFragment.getMap();
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        LatLng myLocation = new LatLng(35.227406, -80.838959);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 11.0f));
        mMarkers = new HashMap<Marker, String>();
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                for (int i=0; i < mDeals.size(); i++) {
                    Deal deal = mDeals.get(i);
                    if (deal.getName().equals(marker.getTitle())) {
                        userSelectedMarker = true;
                        mPager.setCurrentItem(i);
                        break;
                    }
                }
                return false;
            }
        });

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setOnPageChangeListener(this);

        mDeals = DealManager.getInstance().getDeals();
        if (mDeals == null) {
            doMapQuery();
        } else {
            // TODO make a method to combine dup logic
            CardAdapter adapter = new CardAdapter(getSupportFragmentManager());
            adapter.mDeals = mDeals;
            DealManager.getInstance().setDeals(mDeals);
            mPager.setAdapter(adapter);

            // Loop through the results of the search
            BitmapDescriptor bitmapDescriptor =  BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
            for (Deal deal : mDeals) {
                // Set up the mapFragment marker's location
                MarkerOptions markerOpts =
                        new MarkerOptions().position(new LatLng(deal.getLocation().getLongitude(), deal
                                .getLocation().getLatitude()));

                // Display a green marker with the post information
                markerOpts = markerOpts.title(deal.getName()).icon(bitmapDescriptor);
                // Add a new marker
                Marker marker = mapFragment.getMap().addMarker(markerOpts);
                mMarkers.put(marker, deal.getObjectId());
                if (deal.getObjectId().equals(selectedObjectId)) {
                    marker.showInfoWindow();
                    selectedObjectId = null;
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_map, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_list) {
            Intent intent = new Intent(this, ListActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     * Set up the query to update the mapFragment view
     */
    private void doMapQuery() {
        Log.d(TAG, "doMapQuery");

        // Create the mapFragment Parse query
        ParseQuery<Deal> mapQuery = Deal.getQuery();
        // Set up additional query filters
        mapQuery.orderByDescending("createdAt");
        // Kick off the query in the background
        mapQuery.findInBackground(new FindCallback<Deal>() {
            @Override
            public void done(List<Deal> objects, ParseException e) {
                CardAdapter adapter = new CardAdapter(getSupportFragmentManager());
                adapter.mDeals = objects;
                mDeals = objects;
                DealManager.getInstance().setDeals(mDeals);
                mPager.setAdapter(adapter);

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
                            markerOpts.title(deal.getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
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
    public void onPageScrolled(int i, float v, int i2) {
//        Log.d(TAG, "onPageScrolled");
    }

    @Override
    public void onPageSelected(int i) {
        Log.d(TAG, "onPageSelected");

        if (userSelectedMarker) {
            userSelectedMarker = false;
        } else {
            Deal deal = mDeals.get(i);
            for (Marker marker : this.mMarkers.keySet()) {
                String objId = this.mMarkers.get(marker);
                if (objId.equals(deal.getObjectId())) {
                    marker.showInfoWindow();
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 12.0f));
                    break;
                }

            }
//            Marker marker = mMarkers.v;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {
    }
}