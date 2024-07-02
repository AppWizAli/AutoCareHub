package com.hiskytechs.autocarehub

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hiskytechs.autocarehub.Models.ModelWorkshop
import com.hiskytechs.autocarehub.databinding.ActivityWorkshopProfileBinding

class ActivityWorkshopProfile : AppCompatActivity() {
    private lateinit var binding: ActivityWorkshopProfileBinding
    private val db = Firebase.firestore
    private lateinit var workshopId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkshopProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

                binding.editButton.setOnClickListener {
                    startActivity(Intent(this@ActivityWorkshopProfile, ActivityWorkshopEditProfile::class.java))
                }

                workshopId = intent.getStringExtra("workshopId") ?: ""

                if (workshopId.isNotEmpty()) {
                    Log.d("ActivityWorkshopProfile", "Workshop ID: $workshopId")
                    fetchWorkshopDetails()
                } else {
                    Toast.makeText(this, "Workshop ID is missing", Toast.LENGTH_SHORT).show()
                    Log.e("ActivityWorkshopProfile", "Workshop ID is missing")
                }
            }

            private fun fetchWorkshopDetails() {
                Log.d("ActivityWorkshopProfile", "Fetching details for Workshop ID: $workshopId")
                db.collection("WorkshopRegistration").document(workshopId)
                    .get()
                    .addOnSuccessListener { documentSnapshot ->
                        if (documentSnapshot.exists()) {
                            val modelWorkshop = documentSnapshot.toObject(ModelWorkshop::class.java)
                            modelWorkshop?.let {
                                binding.apply {
                                    username.text = it.workshopName
                                    email.text = it.workshopEmail
                                    Address.text = it.workshopAddress
                                    phone.text = it.workshopPhoneNumber
                                }
                            }
                            Log.d("ActivityWorkshopProfile", "Workshop details fetched successfully")
                        } else {
                            Toast.makeText(this, "Workshop details not found", Toast.LENGTH_SHORT).show()
                            Log.e("ActivityWorkshopProfile", "Workshop details not found")
                        }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Failed to fetch workshop details: ${e.message}", Toast.LENGTH_SHORT).show()
                        Log.e("ActivityWorkshopProfile", "Failed to fetch workshop details", e)
                    }
            }
        }
