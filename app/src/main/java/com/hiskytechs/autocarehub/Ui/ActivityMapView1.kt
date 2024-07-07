package com.hiskytechs.autocarehub.Ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityMapView1Binding

class ActivityMapView1 : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityMapView1Binding
    private var mGoogleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapView1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        initMap()
    }
    private fun initMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
        // Customize your map here, for example:
        // val sydney = LatLng(-34.0, 151.0)
        // mGoogleMap?.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        // mGoogleMap?.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}
