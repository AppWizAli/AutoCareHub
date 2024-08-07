package com.hiskytechs.autocarehub.Ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
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

        workshopId = intent.getStringExtra("workshopId") ?: ""

        fetchWorkshopDetails()
    }

    private fun fetchWorkshopDetails() {
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
                } else {
                    Toast.makeText(
                        this@ActivityWorkshopProfile,
                        "Workshop details not found",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this@ActivityWorkshopProfile,
                    "Failed to fetch workshop details: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}
