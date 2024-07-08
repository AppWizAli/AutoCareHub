package com.hiskytechs.autocarehub.Ui

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.hiskytechs.autocarehub.Models.ModelWorkshop
import com.hiskytechs.autocarehub.Models.MySharedPref
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityWorkshopRegistrationBinding
import java.util.regex.Pattern

class ActivityWorkshopRegistration : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityWorkshopRegistrationBinding
    private val db = Firebase.firestore
    private val storage = Firebase.storage
    private lateinit var dialog: Dialog
    private lateinit var mySharedPref: MySharedPref
    private var selectedImageUri: Uri? = null
    private var selectedLatLng: LatLng? = null
    private var map: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkshopRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mySharedPref = MySharedPref(this)

        binding.backArrow.setOnClickListener {
            finish()
        }

        binding.profileImage.setOnClickListener {
            selectImage()
        }

        binding.btnSelectLocation.setOnClickListener {
            showMapDialog()
        }

        binding.Register.setOnClickListener {
            registerWorkshop()
        }
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }

    private fun showMapDialog() {
        dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_map)
        dialog.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        dialog.show()

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val btnAddLocation = dialog.findViewById<Button>(R.id.btnAddLocation)
        btnAddLocation.setOnClickListener {
            if (selectedLatLng != null) {
                dialog.dismiss()
                Toast.makeText(this, "Location selected: $selectedLatLng", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please select a location", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map?.setOnMapClickListener { latLng ->
            map?.clear()
            map?.addMarker(MarkerOptions().position(latLng))
            map?.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            selectedLatLng = latLng
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data
            binding.profileImage.setImageURI(selectedImageUri)
        }
    }

    private fun registerWorkshop() {
        val userEmail = binding.useremail.text.toString().trim()
        val userName = binding.username.text.toString().trim()
        val userAddress = binding.useraddress.text.toString().trim()
        val userPhone = binding.phone.text.toString().trim()
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        if (userEmail.isEmpty()) {
            binding.useremail.error = "Email is required"
        } else if (!Pattern.compile(emailPattern).matcher(userEmail).matches()) {
            binding.useremail.error = "Invalid email address"
        } else if (userName.isEmpty()) {
            binding.username.error = "Username is required"
        } else if (userAddress.isEmpty()) {
            binding.useraddress.error = "Address is required"
        } else if (userPhone.isEmpty()) {
            binding.phone.error = "Phone Number is required"
        } else if (selectedImageUri == null) {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
        } else if (selectedLatLng == null) {
            Toast.makeText(this, "Please select a location", Toast.LENGTH_SHORT).show()
        } else {
            showAnimation()
            val storageRef = storage.reference.child("workshopImages/${System.currentTimeMillis()}.jpg")
            val uploadTask = storageRef.putFile(selectedImageUri!!)
            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    throw task.exception!!
                }
                storageRef.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    val modelWorkshop = ModelWorkshop(
                        workshopName = userName,
                        workshopEmail = userEmail,
                        workshopAddress = userAddress,
                        workshopPhoneNumber = userPhone,
                        workshopImage = downloadUri.toString(),
                        workshopLatitude = selectedLatLng!!.latitude,
                        workshopLongitude = selectedLatLng!!.longitude
                    )
                    db.collection("WorkshopRegistration").add(modelWorkshop)
                        .addOnSuccessListener { documentRef ->
                            modelWorkshop.workshopId = documentRef.id

                            db.collection("WorkshopRegistration").document(documentRef.id)
                                .set(modelWorkshop)
                                .addOnSuccessListener {
                                    closeAnimation()
                                    Log.d("ActivityWorkshopRegistration", "Registration successful")
                                    Toast.makeText(
                                        this@ActivityWorkshopRegistration,
                                        "Registration successful",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    val intent = Intent(this@ActivityWorkshopRegistration, ActivityActivityWorkShopHome::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                                .addOnFailureListener { e ->
                                    Log.e("ActivityWorkshopRegistration", "Failed to set document", e)
                                    Toast.makeText(
                                        this@ActivityWorkshopRegistration,
                                        "Registration unsuccessful",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    closeAnimation()
                                }
                        }
                        .addOnFailureListener { e ->
                            Log.e("ActivityWorkshopRegistration", "Failed to add document", e)
                            Toast.makeText(
                                this@ActivityWorkshopRegistration,
                                "Registration unsuccessful",
                                Toast.LENGTH_SHORT
                            ).show()
                            closeAnimation()
                        }
                } else {
                    Log.e("ActivityWorkshopRegistration", "Image upload failed", task.exception)
                    Toast.makeText(this, "Image upload failed", Toast.LENGTH_SHORT).show()
                    closeAnimation()
                }
            }
        }
    }

    private fun showAnimation() {
        dialog = Dialog(this@ActivityWorkshopRegistration)
        dialog.setContentView(R.layout.dialog_anim_lodaing)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }

    private fun closeAnimation() {
        dialog.dismiss()
    }

    companion object {
        private const val REQUEST_IMAGE_PICK = 1
    }
}
