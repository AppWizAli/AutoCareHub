package com.hiskytechs.autocarehub.Ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.hiskytechs.autocarehub.Models.ModelWorkshop
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityMapView1Binding

class ActivityMapView1 : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMapView1Binding
    private var mGoogleMap: GoogleMap? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapView1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        db = FirebaseFirestore.getInstance()

        initMap()
    }

    private fun initMap() {
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap

        // Check for location permissions
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }

        // Enable user's location on the map
        mGoogleMap?.isMyLocationEnabled = true

        // Move camera to user's current location if available
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                val currentLatLng = LatLng(location.latitude, location.longitude)
                mGoogleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
            }
        }

        // Fetch and display workshop markers
        fetchWorkshops()
    }

    private fun fetchWorkshops() {
        db.collection("WorkshopRegistration")
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    val workshop = document.toObject(ModelWorkshop::class.java)
                    workshop?.let {
                        if (it.workshopLatitude != null && it.workshopLongitude != null) {
                            val workshopLatLng = LatLng(it.workshopLatitude!!, it.workshopLongitude!!)
                            mGoogleMap?.addMarker(
                                MarkerOptions().position(workshopLatLng).title(it.workshopName)
                            )
                        }
                    }
                }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error getting workshops", e)
            }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val TAG = "ActivityMapView1"
    }
}
