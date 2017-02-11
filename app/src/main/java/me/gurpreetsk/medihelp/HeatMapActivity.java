package me.gurpreetsk.medihelp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class HeatMapActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    CameraUpdate cu;
    private Marker ali,ali2,pitamMarker1,pitamMarker2;
    DrawerLayout navDrawer;
    ListView navDrawerList;
    ActionBarDrawerToggle mDrawerToggle;
    String[] elements;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heat_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        navDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navDrawerList = (ListView) findViewById(R.id.left_drawer);

        elements = new String[]{
                "Calculate Heart Rate",
                "Go to Hospital",
                "Tumor check",
                "Fracture Detection",
                "Log Out"
        };

        navDrawerList.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, elements));
        navDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                navDrawer,             /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        // Set the drawer toggle as the DrawerListener
        navDrawer.setDrawerListener(mDrawerToggle);

        try {
//            ((FragmentActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);    //TODO:
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    startActivity(new Intent(HeatMapActivity.this, HeartRateActivity.class));
                    break;
                case 1:
                    startActivity(new Intent(HeatMapActivity.this, QRActivity.class));
                    break;
                case 2:
                    startActivity(new Intent(HeatMapActivity.this, TumorActivity.class));
                    break;
                case 3:
                    startActivity(new Intent(HeatMapActivity.this,BoneFractureActivity.class));
                    break;
            }
        }
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                if (navDrawer.isDrawerOpen(Gravity.LEFT))
                    navDrawer.closeDrawer(Gravity.LEFT);      // OPEN DRAWER
                else
                    navDrawer.openDrawer(Gravity.LEFT);
                return true;
        }
        return true;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(28.750075, 77.117665);
        LatLng dtu2=new LatLng(28.750075,77.1000);
        LatLng dtu3=new LatLng(28.805465,77.1100);
        final LatLng alipur=new LatLng(28.805465,77.1000);
        final LatLng alipur2=new LatLng(28.805465,77.110222);
        LatLng pitampura=new LatLng(28.698998,77.138417);
        LatLng pitampura1=new LatLng(28.698998,77.1000);
        List<Marker> markersList = new ArrayList<Marker>();
        Marker Delhi = mMap.addMarker(new MarkerOptions().position(sydney));
        Marker Chaandigarh = mMap.addMarker(new MarkerOptions().position(dtu2));
        Marker SriLanka = mMap.addMarker(new MarkerOptions().position(dtu3));
        pitamMarker1 = mMap.addMarker(new MarkerOptions().position(pitampura));
        pitamMarker2 = mMap.addMarker(new MarkerOptions().position(pitampura1));
        ali = mMap.addMarker(new MarkerOptions().position(alipur));
        ali2= mMap.addMarker(new MarkerOptions().position(alipur2));
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        markersList.add(Delhi);
        markersList.add(SriLanka);
        markersList.add(Chaandigarh);
        markersList.add(pitamMarker1);
        markersList.add(pitamMarker2);
        markersList.add(ali);
        markersList.add(ali2);
        for (Marker m : markersList) {
            builder.include(m.getPosition());
        }
        int padding = 50;
        /**create the bounds from latlngBuilder to set into map camera*/
        LatLngBounds bounds = builder.build();
        cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        /*for(int i=0;i<listLatlong.size();i++){
            //mMap.addMarker(new MarkerOptions().position(listLatlong.get(i)));
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(listLatlong.get(i),200));
            MarkerOptions markerOptions = new MarkerOptions();
            // Setting latitude and longitude for the marker
            markerOptions.position(listLatlong.get(i));
            // Adding marker on the Google Map
            mMap.addMarker(markerOptions);
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,2));*/
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                /**set animated zoom camera into map*/
                mMap.animateCamera(cu);

            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                if(marker.equals(ali) || marker.equals(ali2) || marker.equals(pitamMarker1) || marker.equals(pitamMarker2)){
                    Log.d("Heat","clicked");
                    Intent intent=new Intent(HeatMapActivity.this,DiseaseActivity.class);
                    startActivity(intent);
                }
                //Using position get Value from arraylist
                return false;
            }
        });
    }
}
