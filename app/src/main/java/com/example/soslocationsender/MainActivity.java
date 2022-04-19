package com.example.soslocationsender;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    double latitude = 0;
    double longitude = 0;
    private LocationManager manager;
    private GPSReciever reciever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonListenerMethod();
        reciever = new GPSReciever();
        manager = (LocationManager)
                this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 1.0F, reciever);
    }

    public void buttonListenerMethod(){
        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SmsManager sms = SmsManager.getDefault();
                String phone = "01631500374";
                String messagebody = "Please take me from Longitude: "+ Double.toString(longitude) +" and Latitude "+ Double.toString(latitude);
                try{
                    sms.sendTextMessage(phone,null,messagebody,null,null);
                    Toast.makeText(MainActivity.this, "S.O.S message send!", Toast.LENGTH_SHORT).show();

                } catch (Exception e){
                    Toast.makeText(MainActivity.this, "Message sending failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public class GPSReciever implements LocationListener{

        @Override
        public void onLocationChanged(@NonNull Location location) {
            if( location != null ){
               longitude = location.getLongitude();
               latitude = location.getLatitude();
                Toast.makeText(MainActivity.this, "Ready to send!", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(MainActivity.this, "Not Ready Yet!", Toast.LENGTH_LONG).show();
            }

        }

        @Override public void onProviderEnabled(String s) {
            Toast.makeText(getApplicationContext(),
                    "GPS Enabled!", Toast.LENGTH_LONG).show();
        }

        @Override public void onProviderDisabled(String s) {
            Toast.makeText(getApplicationContext(),
                    "Please enable GPS!", Toast.LENGTH_LONG).show();
        }

    }

}