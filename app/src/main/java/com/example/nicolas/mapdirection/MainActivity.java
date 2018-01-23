package com.example.nicolas.mapdirection;

import android.Manifest;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ConstantInterface, OnMapReadyCallback, DirectionCallback,

        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener

{
    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    private TextView textAddress;
    private LatLng currentLatLng;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private AppUtility appUtility;
    private RunTimePermission runTimePermission;

    private static final LatLng punto1 = new LatLng(-12.072941, -77.165595);
    private static final LatLng punto2 = new LatLng(-12.072201, -77.164656);
    private static final LatLng punto3 = new LatLng(-12.071755, -77.164603);
    private static final LatLng punto4 = new LatLng(-12.072967, -77.163589);
    private static final LatLng punto5 = new LatLng(-12.073743, -77.163599);
    private static final LatLng punto6 = new LatLng(-12.073198, -77.162178);
    private static final LatLng punto7 = new LatLng(-12.073040, -77.162328);
    private static final LatLng punto8 = new LatLng(-12.071897, -77.162151);
    private static final LatLng punto9 = new LatLng(-12.071398, -77.162210);
    private static final LatLng punto10 = new LatLng(-12.070365, -77.163127);
    private static final LatLng punto11 = new LatLng(-12.069667, -77.163122);
    private static final LatLng punto12 = new LatLng(-12.069290, -77.162366);
    private static final LatLng punto13 = new LatLng(-12.069164, -77.162028);
    private static final LatLng punto14 = new LatLng(-12.070407, -77.161894);
    private static final LatLng punto15 = new LatLng(-12.069961, -77.161153);
    private static final LatLng punto16 = new LatLng(-12.069562, -77.160681);
    private static final LatLng punto17 = new LatLng(-12.068419, -77.160091);
    private static final LatLng punto18 = new LatLng(-12.068980, -77.158611);
    private static final LatLng punto19 = new LatLng(-12.073045, -77.167928);

    List<LatLng> ubicaciones = new ArrayList<LatLng>();
    List<Double> distancias = new ArrayList<Double>();
    double meters;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initControls();


    }

    @Override

    public void onStop() {

        if (googleApiClient != null && googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
        super.onStop();

    }


    private void initControls() {
        appUtility = new AppUtility(this);
        runTimePermission = new RunTimePermission(this);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        textAddress = (TextView) findViewById(R.id.textAddress);
        textAddress.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {
// This is to prevent user to click on the map under the distance text.
            }

        });
        if (appUtility.checkPlayServices()) {

            googleApiClient = new GoogleApiClient
                    .Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
            googleApiClient.connect();
        }
        ubicaciones.add(punto1);
        ubicaciones.add(punto2);
        ubicaciones.add(punto3);
        ubicaciones.add(punto4);
        ubicaciones.add(punto5);
        ubicaciones.add(punto6);
        ubicaciones.add(punto7);
        ubicaciones.add(punto8);
        ubicaciones.add(punto10);
        ubicaciones.add(punto11);
        ubicaciones.add(punto12);
        ubicaciones.add(punto13);
        ubicaciones.add(punto14);
        ubicaciones.add(punto15);
        ubicaciones.add(punto16);
        ubicaciones.add(punto17);
        ubicaciones.add(punto18);
        ubicaciones.add(punto19);

        showDistance2(ubicaciones);
    }

    @Override

    public void onMapReady(final GoogleMap googleMap) {

//        this.googleMap = googleMap;
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            googleMap.setMyLocationEnabled(true);
//            googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLatLng));
//            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f));
//        }
//
//        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//
//            @Override
//
//            public void onMapClick(LatLng latLng) {
//                googleMap.clear();
//                googleMap.addMarker(new MarkerOptions().position(latLng));
//                GoogleDirection.withServerKey(getString(R.string.map_direction_key))
//                        .from(currentLatLng)
//                        .to(new LatLng(latLng.latitude, latLng.longitude))
//                        .transportMode(TransportMode.DRIVING)
//                        .execute(MainActivity.this);
//                //showDistance(latLng);
//            }
//        });
//        //showDistance(punto1);
//        showDistance2(ubicaciones);

    }


    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {

        if (direction.isOK()) {
            ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
            googleMap.addPolyline(DirectionConverter.createPolyline(this, directionPositionList, 1, ContextCompat.getColor(this, R.color.colorPrimary)));
        }
    }

    @Override

    public void onDirectionFailure(Throwable t) {
    }

    private void showDistance(LatLng latLng) {
        Location locationA = new Location("Location A");
        locationA.setLatitude(latLng.latitude);
        locationA.setLongitude(latLng.longitude);
        Location locationB = new Location("Location B");
        locationB.setLatitude(currentLatLng.latitude);
        locationB.setLongitude(currentLatLng.longitude);
        textAddress.setText("Distance : " + new DecimalFormat("##.##").format(locationA.distanceTo(locationB)) + "m");
    }

    private void showDistance2(List<LatLng> arrayListlatLng) {
        for (int i = 0; i < arrayListlatLng.size(); i++) {
            Location locationA = new Location("Location A");
            locationA.setLatitude(arrayListlatLng.get(i).latitude);
            locationA.setLongitude(arrayListlatLng.get(i).longitude);
            Location locationB = new Location("Location B");
            locationB.setLatitude(currentLatLng.latitude);
            locationB.setLongitude(currentLatLng.longitude);
            meters = Double.parseDouble(new DecimalFormat("##.##").format(locationA.distanceTo(locationB)));
            distancias.add(i, meters);
        }
        Log.d("LISTA DE DISTANCIAS", String.valueOf(distancias));
        Object Min = Collections.min(distancias);
        Log.d("DISTANCIA MAS CERCANA", String.valueOf(Min));
        textAddress.setText("La ubicacion mas cercana esta a: " + String.valueOf(Min) + "m de su posicion");
    }

// checking Runtime permission
    private void getPermissions(String[] strings) {
        runTimePermission.requestPermission(strings, new RunTimePermission.RunTimePermissionListener() {
            @Override
            public void permissionGranted() {
                locationChecker(googleApiClient, MainActivity.this);
            }

            @Override
            public void permissionDenied() {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }


// Checking whether location service is enable or not.

    public void locationChecker(GoogleApiClient mGoogleApiClient, final MainActivity activity) {

// Creating location request object

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setSmallestDisplacement(DISPLACEMENT);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

            @Override

            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        getLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(activity, 1000);
                        } catch (IntentSender.SendIntentException e) {
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });

    }

    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {

            currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        if (googleMap == null) {
            mapFragment.getMapAsync(this);
        }
    }
}