package com.example.ubuntu.maps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {
    private GoogleMap map;
    private LocationManager locationManager;
    private Marker marker;
    private MarkerOptions options;
    boolean isGPSEnabled= false;
    boolean isNetworkEnabled= false;
    boolean canGetLocation= false;
    Location location;
    Point point;
    boolean tracking=false;
    final int MINIMUM_ACCURACY=10;
    final int DIST_BETWEEN_POINTS=10;
    Location newLoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);
        locationManager= (LocationManager)getSystemService(LOCATION_SERVICE);
        //options= new MarkerOptions().position(new LatLng(0,0)).title("My current location");

        location= requestLocation();
        if(location==null){
            Toast.makeText(MainActivity.this, "Enable Location Permissions", Toast.LENGTH_SHORT).show();
        }else {
            point = new Point();
            point.latitude = location.getLatitude();
            point.longitude = location.getLongitude();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
            map=googleMap;
            if(point!=null) {
                LatLng cyrr = new LatLng(point.latitude, point.longitude);
                map.addMarker(new MarkerOptions().position(cyrr).title("You are here"));
                map.moveCamera(CameraUpdateFactory.newLatLng(cyrr));
                map.animateCamera(CameraUpdateFactory.zoomIn());
                map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {
                        Vibrator vibe = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                        vibe.vibrate(50);
                        if (tracking) {
                            Toast.makeText(MainActivity.this, "stop tracking", Toast.LENGTH_SHORT).show();
                            tracking = false;
                        } else {
                            Toast.makeText(MainActivity.this, "Start tracking", Toast.LENGTH_SHORT).show();
                            tracking = true;
                        }
                    }

                });
            }
    }

    @Override
    public void onLocationChanged(Location location) {
        location= requestLocation();
        if(tracking) {
            if (location.getAccuracy() < MINIMUM_ACCURACY) {
                if (newLoc == null || newLoc.distanceTo(location) > DIST_BETWEEN_POINTS) {
                    Polyline line = map.addPolyline(new PolylineOptions()
                            .add(new LatLng(this.location.getLatitude(), this.location.getLongitude()), new LatLng(location.getLatitude(),location.getLongitude()))
                            .width(5)
                            .color(Color.RED));
                }
            }
        }
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public Location requestLocation() {
        Location location=null;
        locationManager= (LocationManager) getSystemService(LOCATION_SERVICE);
        isGPSEnabled= locationManager.isProviderEnabled(locationManager.GPS_PROVIDER);
        isNetworkEnabled= locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER);

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            if(isGPSEnabled){
                if(location==null){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10000,10,MainActivity.this);
                    if(locationManager!=null){
                        location= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }
                }

            }
        }
        return location;
    }

    public  class Point{
        double latitude;
        double longitude;
    }

}
