package com.example.locationupdates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;

import java.util.List;
import java.util.Locale;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class MainActivity extends AppCompatActivity {

    TextView tvCity, tvlongitude, tvCountry, tvlatitude, tvAddress;

    LocationRequest mLocationRequest;
    LocationCallback locationCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCity = findViewById(R.id.textview1);
        tvlongitude = findViewById(R.id.textview2);
        tvCountry = findViewById(R.id.textview3);
        tvlatitude = findViewById(R.id.textview4);
        tvAddress = findViewById(R.id.textview5);

        startLocationUpdates();

    }

    protected void startLocationUpdates() {
        // Create the location request to start receiving updates
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(10 * 1000);
        mLocationRequest.setFastestInterval(2000);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
            return;
        }

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                String msg = "Updated Location: " +
                        Double.toString(location.getLatitude()) + "," +
                        Double.toString(location.getLongitude());
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

                try {
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

//            tvCity.setText(addresses.get(0).getLocality());
//            tvState.setText(addresses.get(0).getAdminArea());
//            tvCountry.setText(addresses.get(0).getCountryName());
//            tvPin.setText(addresses.get(0).getPostalCode());
//            tvLocality.setText(addresses.get(0).getAddressLine(0));


                    //Set latitude on textview
                    tvlatitude.setText(Html.fromHtml("" +
                            "<font color='#6200EE'><b>Latitude :</b><br></font>"
                            + location.getLatitude()
                    ));

                    //Set longitude on textview
                    tvlongitude.setText(Html.fromHtml("" +
                            "<font color='#6200EE'><b>Longitude :</b><br></font>"
                            + location.getLongitude()
                    ));
                    //Set country name
                    tvCountry.setText(Html.fromHtml("" +
                            "<font color='#6200EE'><b>Country Name :</b><br></font>"
                            + addresses.get(0).getCountryName()
                    ));
                    //Set locality
                    tvCity.setText(Html.fromHtml("" +
                            "<font color='#6200EE'><b>Locality :</b><br></font>"
                            + addresses.get(0).getLocality()
                    ));
                    //Set longitude on textview
                    tvAddress.setText(Html.fromHtml("" +
                            "<font color='#6200EE'><b>Address :</b><br></font>"
                            + addresses.get(0).getAddressLine(0)
                    ));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        getFusedLocationProviderClient(this).requestLocationUpdates(
                mLocationRequest, locationCallback, null);
    }

}