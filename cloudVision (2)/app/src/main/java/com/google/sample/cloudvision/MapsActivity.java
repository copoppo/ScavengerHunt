package com.google.sample.cloudvision;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.location.Location;
import com.google.android.gms.location.LocationListener;
import android.location.Geocoder;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationRequest;



import  android.content.IntentSender;

import android.util.Log;

import  android.support.v4.content.ContextCompat;

import  android.support.v4.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.location.Address;
import java.util.List;
import java.util.Locale;
import java.io.IOException;




import com.google.android.gms.common.api.GoogleApiClient;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener,
        ActivityCompat.OnRequestPermissionsResultCallback
{

    //INSTANCE VARIABLES
    private GoogleMap mMap;
    private static GoogleApiClient mGoogleApiClient;
    public static final String TAG = MapsActivity.class.getSimpleName();
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private static LocationRequest mLocationRequest;

    private static final int MY_PERMISSIONS_REQUEST = 1;
    private static String entirely;

    @Override
    /**
     * Creates activity
     */
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //set up GoogleAPIClient in order to connect to network and allow for GPS
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

        //make sure the GoogleApiClient is connected
        mGoogleApiClient.connect();

    }


    /**
     * This was a method given to me but I ended up not using it because it was set up to provide an automatic map and location
     * marker but because I ended up using GoogleApiClient, this method wasn't used
     */
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        //initialize map
        mMap = googleMap;

    }

    @Override
    /**
     * This method is called once the GoogleApiClient is connected and it then calls methods to check for permission or calls methods
     * to get current location
     */
    public void onConnected(@Nullable Bundle bundle)
    {
        //get the last location
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        //the last location could be null so then this if statement calls for updates in the current locaiton
        if (location == null)
        {

            Log.d("SOMETHING", "Content");

            //check for permission to access location
            int permissionCheck = ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION);

            //permission has to be granted befpre we can get location updates
            if (permissionCheck == PackageManager.PERMISSION_GRANTED)
            {
                Log.d("AHHHHHH", "Content");

                //Execute location service call if user has explicitly granted ACCESS_FINE_LOCATION.
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

            }

            //since permission hasn't been granted, we need to ask for permission by the user
            else
            {
                Log.d("GAH", "MAP permission has NOT been granted. Requesting permission.");


                //Show explanation to grant location
                //I didn't know what to put in here so I just put in a log statement to check where the app currently is
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION))
                {

                    Log.d("PLEASE",
                            "Need location permission to go through app so check that.");

                }

                //otherwise we can just request permission without explaining why
                else
                {
                    Log.d("PLEASE", "I have no idea why it's here but it's here.");
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST);

                    Log.d("PLEASE",
                            "is it here now.");

                }

            }
        }

        //if it hasn't gone through those long if-loops, then the location was not null and we can just pass it in
        else
        {
            Log.d("AHHHHHH!!!!!!!!!", "Content");
            handleNewLocation(location);
        }

    }

    /**
     * This helps create the map and update to current location
     * @param location
     */
    private void handleNewLocation(Location location)
    {
        //the location should not be null when this is called but just checking in case of error
        if(location == null)
        {
            Log.d("WHY", "give me a break");
        }

        //otherwise print out location
        Log.d(TAG, location.toString());

        //the map fragment is used in order to call a googlemap object which can't be instantiated normally
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);;
        mMap = mapFragment.getMap();
//        mMap.setMyLocationEnabled(true);

        //these two variables will hold the coordinates of the location
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);

        //this will be used to get the current address (street address, state, zipcode, and country
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try{

            addresses = geocoder.getFromLocation(currentLatitude, currentLongitude, 1); //  1 represent max location result to returned, by documents it recommended 1 to 5

            String address = addresses.get(0).getAddressLine(0);
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();

            entirely = address + ", " + city + ", " + postalCode + ", " + state + ", " + country;

            //add a marker to where the user currently is
            mMap.addMarker(new MarkerOptions().position(latLng).title(entirely));

            //we wanted the app to automatically zoom in on the location so we included these two lines
            float zoomLevel = (float)16.0; //This goes up to 21
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    /**
     * This method is called when the connection is suspended
     */
    public void onConnectionSuspended(int i)
    {
        Log.i(TAG, "Location services suspended. Please reconnect.");

    }

    @Override
    /**
     * This method is called when the connection fails
     */
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //reconnect googleapiclient
        mGoogleApiClient.isConnected();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    /**
     * Called when location has changed after update was called
     */
    public void onLocationChanged(Location location)
    {
        handleNewLocation(location);
    }


    @Override
    /**
     * Called to get permission from the user
     */
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case MY_PERMISSIONS_REQUEST:
            {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Log.d("AHHHHHH", "Permission GRANTED LET'S GO");
                    Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

                    //if location is null then we get permission to access gps of user
                    if (location == null) {

                        Log.d("SOMETHING", "Content");
                        int permissionCheck = ContextCompat.checkSelfPermission(this,
                                android.Manifest.permission.ACCESS_FINE_LOCATION);

                        //permission should be granted at this point but it doesn't hurt to check
                        if (permissionCheck == PackageManager.PERMISSION_GRANTED)
                        {
                            Log.d("AHHHHHH", "Content");
                            mGoogleApiClient.connect();

                            //Execute location service call if user has explicitly granted ACCESS_FINE_LOCATION.
                            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                            Log.d("DEBUG", "current location: " + location.toString());

                            //get the latitude and longitude of the current location
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                            handleNewLocation(location);

                        }

                        else {
                            Log.d("AHHHHHH", "UM IT SHOULD BE GRANTED");



                        }
                    }
                    else {
                        Log.d("AHHHHHH!!!!!!!!!", "Content");
                        handleNewLocation(location);
                    }

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                }
                else
                {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }

                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    /**
     * Get the location address
     * @return address
     */
    public static String getLocationAddress()
    {
        //make sure the app is connected
        mGoogleApiClient.connect();
        return entirely;

    }
}
