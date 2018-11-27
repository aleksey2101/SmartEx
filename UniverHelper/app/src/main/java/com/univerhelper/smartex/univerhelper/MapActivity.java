package com.univerhelper.smartex.univerhelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MapActivity extends Activity implements LocationListener{
    private LocationManager locationManager;

    int x = 0;
    int y = 0;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
    }

    @Override
    public void onLocationChanged(Location location) {
        TextView textView = findViewById(R.id.text_map);
        textView.setText(Double.toString(location.getLatitude()) + " / " + Double.toString(location.getLatitude()));
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
}
