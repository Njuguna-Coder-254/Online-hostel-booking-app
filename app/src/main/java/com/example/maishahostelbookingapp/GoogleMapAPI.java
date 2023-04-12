package com.example.maishahostelbookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapAPI extends FragmentActivity implements OnMapReadyCallback {
    GoogleMap mapAPI;
    SupportMapFragment supportMapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map_api);
        supportMapFragment= (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapAPI);
        supportMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mapAPI=googleMap;
        LatLng Bomas=new LatLng(-0.3947107863982127, 36.96363685642713);

        mapAPI.addMarker(new MarkerOptions().position(Bomas).title("Maisha Hostels"));
        mapAPI.moveCamera(CameraUpdateFactory.newLatLng(Bomas));

    }}