package com.hiskytechs.autocarehub.Ui
import android.app.Activity
import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hiskytechs.autocarehub.ActivityWorkshopProfile
import com.hiskytechs.autocarehub.Models.ModelWorkshop
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityWorkshopRegistrationBinding
import com.hiskytechs.autocarehub.workshophome
import java.util.regex.Pattern

class ActivityWorkshopRegistration : AppCompatActivity() {
    private lateinit var binding: ActivityWorkshopRegistrationBinding
    private val db = Firebase.firestore
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkshopRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
                binding.apply {
                    backArrow.setOnClickListener {
                        finish()
                    }

                    Register.setOnClickListener {
                        showAnimation()
                        val userEmail = useremail.text.toString().trim()
                        val userName = username.text.toString().trim()
                        val userAddress = useraddress.text.toString().trim()
                        val userPhone = phone.text.toString().trim()
                        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

                        if (userEmail.isEmpty()) {
                            useremail.error = "Email is required"
                            closeAnimation()
                        } else if (!Pattern.compile(emailPattern).matcher(userEmail).matches()) {
                            useremail.error = "Invalid email address"
                            closeAnimation()
                        } else if (userName.isEmpty()) {
                            username.error = "Username is required"
                            closeAnimation()
                        } else if (userAddress.isEmpty()) {
                            useraddress.error = "Address is required"
                            closeAnimation()
                        } else if (userPhone.isEmpty()) {
                            phone.error = "Phone Number is required"
                            closeAnimation()
                        } else {
                            val modelWorkshop = ModelWorkshop(
                                workshopName = userName,
                                workshopEmail = userEmail,
                                workshopAddress = userAddress,
                                workshopPhoneNumber = userPhone
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

                                            val intent = Intent(
                                                this@ActivityWorkshopRegistration,
                                                ActivityWorkshopProfile::class.java
                                            ).apply {
                                                putExtra("workshopId", modelWorkshop.workshopId)
                                                putExtra("workshopName", modelWorkshop.workshopName)
                                                putExtra("workshopEmail", modelWorkshop.workshopEmail)
                                                putExtra("workshopAddress", modelWorkshop.workshopAddress)
                                                putExtra("workshopPhoneNumber", modelWorkshop.workshopPhoneNumber)
                                            }
                                            Log.d("ActivityWorkshopRegistration", "Sending Workshop ID: ${modelWorkshop.workshopId}")
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
}
