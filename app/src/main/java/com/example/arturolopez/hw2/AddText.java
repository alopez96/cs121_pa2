package com.example.arturolopez.hw2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Service;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
//`import com.google.android.gms.location.LocationListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.app.PendingIntent.getActivity;

public class AddText extends AppCompatActivity {

    public JSONObject jo = null;
    public JSONArray ja = null;

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Button b, b2;
    private TextView t;
    private String abc = "hello world";
    double a;

    private final String TAG = "TESTGPS";
    public static TextView latText;
    public static TextView longText;


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Start up the Location Service
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_text);

        final EditText first = findViewById(R.id.editText);
        final EditText second = findViewById(R.id.editText2);


        b = (Button) findViewById(R.id.button);
        b2 = (Button) findViewById(R.id.button2);
        t = (TextView) findViewById(R.id.textView3);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);




//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
//                       , 10);
//          }
//            return;
//        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            //t.append("\n " + location.getLongitude() + " " + location.getLatitude());
                            a = location.getLongitude();
                            double b = location.getLatitude();
                        }
                    }
                });



//            LocationRequest mLocationRequest = new LocationRequest();
//            mLocationRequest.setInterval(10000);
//            mLocationRequest.setFastestInterval(5000);
//            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//
//
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
//                .addLocationRequest(mLocationRequest);
//
//
//
//        SettingsClient client = LocationServices.getSettingsClient(this);
//        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
//




        // Read the file
        try {
            File f = new File(getFilesDir(), "file.ser");
            FileInputStream fi = new FileInputStream(f);
            ObjectInputStream o = new ObjectInputStream(fi);
            // Notice here that we are de-serializing a String object (instead of
            // a JSONObject object) and passing the String to the JSONObject’s
            // constructor. That’s because String is serializable and
            // JSONObject is not. To convert a JSONObject back to a String, simply
            // call the JSONObject’s toString method.
            String j = null;
            try {
                j = (String) o.readObject();
            } catch (ClassNotFoundException c) {
                c.printStackTrace();
            }
            try {
                jo = new JSONObject(j);
                ja = jo.getJSONArray("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            // Here, initialize a new JSONObject
            jo = new JSONObject();
            ja = new JSONArray();
            try {
                jo.put("data", ja);
            } catch (JSONException j) {
                j.printStackTrace();
            }
        }



        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                t.append("\n " + location.getLongitude() + " " + location.getLatitude());
                a = location.getLongitude();
                double b = location.getLatitude();
            }

            //@Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            //@Override
            public void onProviderEnabled(String s) {

            }

            //@Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };


        b.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {


                // A reference to the location manager. The LocationManager has already
                // been set up in MyService, we're just getting a reference here.
                LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                List<String> providers = lm.getProviders(true);
                Location l;
                // Go through the location providers starting with GPS, stop as soon
                // as we find one.
                for (int i=providers.size()-1; i>=0; i--) {
                    l = lm.getLastKnownLocation(providers.get(i));


                    longText.setText(Double.toString(l.getLongitude()));
                    latText.setText(Double.toString(l.getLatitude()));
                    if (l != null) break;
                }




                String firstText = first.getText().toString();
                String secondText = second.getText().toString();

                Date now = new Date();
                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a");
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
                String setTime = timeFormat.format(now);
                String setDate = dateFormat.format(now);



                JSONObject temp = new JSONObject();
                try {
                    temp.put("first", firstText);
                    temp.put("second", secondText);
                    temp.put("third", a);
                    temp.put("forth", setTime);
                    temp.put("fifth", setDate);


                } catch (JSONException j) {
                    j.printStackTrace();
                }

                ja.put(temp);

                // write the file
                try {
                    File f = new File(getFilesDir(), "file.ser");
                    FileOutputStream fo = new FileOutputStream(f);
                    ObjectOutputStream o = new ObjectOutputStream(fo);
                    String j = jo.toString();
                    o.writeObject(j);
                    o.close();
                    fo.close();
                } catch (IOException e) {

                }

                //pop the activity off the stack
                Intent i = new Intent(AddText.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });




        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"location updated",Toast.LENGTH_SHORT).show();
                //locationManager.requestLocationUpdates("gps", 5000, 100, (android.location.LocationListener) locationListener);
            }
        });
        //configure_button();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                // If the permissions aren't set, then return. Otherwise, proceed.
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                                , 10);
                    }
                    Log.d(TAG, "returning program");
                    return;
                }
                else{
                    // Create Intent to reference MyService, start the Service.
                    Log.d(TAG, "starting service");
                    Intent i = new Intent(this, MyService.class);
                    if(i==null)
                        Log.d(TAG, "intent null");
                    else{
                        startService(i);
                    }

                }
                break;
            default:
                break;
        }
    }



    // Used for debugging. Below method is extraneous
    @SuppressLint("MissingPermission")
    public void doSomething(View view){
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = lm.getProviders(true);
        Location l;
        for (int i=providers.size()-1; i>=0; i--) {
            l = lm.getLastKnownLocation(providers.get(i));
            longText.setText(Double.toString(l.getLongitude()));
            latText.setText(Double.toString(l.getLatitude()));
            double a = l.getLatitude();
            double b = l.getLatitude();
            if (l != null) break;
        }
    }



}



