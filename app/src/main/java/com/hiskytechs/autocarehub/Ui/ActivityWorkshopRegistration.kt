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
import com.google.firebase.storage.ktx.storage
import com.hiskytechs.autocarehub.Models.ModelWorkshop
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityWorkshopRegistrationBinding
import java.util.regex.Pattern

class ActivityWorkshopRegistration : AppCompatActivity() {
    private lateinit var binding: ActivityWorkshopRegistrationBinding
    private val db = Firebase.firestore
    private val storage = Firebase.storage
    private lateinit var dialog: Dialog
    private var selectedImageUri: Uri? = null

    companion object {
        const val PICK_IMAGE_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkshopRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            profileImage.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, PICK_IMAGE_REQUEST)
            }

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
                }  else if (selectedImageUri == null) {
                    Toast.makeText(
                        this@ActivityWorkshopRegistration,
                        "Please select an image",
                        Toast.LENGTH_SHORT
                    ).show()
                    closeAnimation()
                } else {
                    selectedImageUri?.let {
                        uploadImage(it) { imageUrl ->
                            val modelWorkshop = ModelWorkshop(
                                workshopName = userName,
                                workshopEmail = userEmail,
                                workshopAddress = userAddress,
                                workshopPhoneNumber = userPhone,
                                workshopImage = imageUrl
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
                                            startActivity(
                                                Intent(
                                                    this@ActivityWorkshopRegistration,
                                                    ActivityWorkShopHome::class.java
                                                )
                                            )
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
                    } ?: run {
                        Toast.makeText(
                            this@ActivityWorkshopRegistration,
                            "Please select an image",
                            Toast.LENGTH_SHORT
                        ).show()
                        closeAnimation()
                    }
                }
            }
        }
    }

    private fun uploadImage(imageUri: Uri, callback: (String) -> Unit) {
        val storageRef = storage.reference.child("workshop_images/${System.currentTimeMillis()}.jpg")
        val uploadTask = storageRef.putFile(imageUri)

        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let { throw it }
            }
            storageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                Log.d("ActivityWorkshopRegistration", "Image uploaded successfully: $downloadUri")
                callback(downloadUri.toString())
            } else {
                uploadTask.addOnFailureListener { e ->
                    Log.e("ActivityWorkshopRegistration", "Image upload failed: ${e.message}", e)
                    closeAnimation()
                    Toast.makeText(
                        this@ActivityWorkshopRegistration,
                        "Image upload failed: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            selectedImageUri = data.data
            Glide.with(this).load(selectedImageUri).into(binding.profileImage)
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
