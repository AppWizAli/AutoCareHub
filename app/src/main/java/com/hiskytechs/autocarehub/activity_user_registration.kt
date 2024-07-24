package com.hiskytechs.autocarehub

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.carrepairapp.model.ModelUser
import com.google.firebase.firestore.FirebaseFirestore
import com.hiskytechs.autocarehub.Models.MySharedPref
import com.hiskytechs.autocarehub.Ui.Activity_edit_user
import com.hiskytechs.autocarehub.Ui.workshophome
import com.hiskytechs.autocarehub.databinding.ActivityUserRegistrationBinding

class activity_user_registration : AppCompatActivity() {
    private lateinit var binding: ActivityUserRegistrationBinding
    private lateinit var dialog: Dialog
    private lateinit var db: FirebaseFirestore
    private lateinit var mySharedPref: MySharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backArrow.setOnClickListener {
           finish()
        }
        showAnimation()
        db = FirebaseFirestore.getInstance()
        mySharedPref = MySharedPref(this)

        // Initial data load
        loadUserData()

        binding.add.setOnClickListener {
            startActivity(Intent(this, Activity_edit_user::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        loadUserData()  // Reload user data when coming back from the edit activity
    }

    private fun loadUserData() {
        val userDocId = mySharedPref.getUserDocId()

        db.collection("User")
            .document(userDocId)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                closeAnimation()
                if (documentSnapshot.exists()) {
                    val modelUser = documentSnapshot.toObject(ModelUser::class.java)
                    modelUser?.let {
                        binding.apply {
                            username.text = it.userName
                            useremail.text = it.email
                            useraddress.text = it.address
                            phone.text = it.phoneNumber
                        }
                    }
                    Toast.makeText(this, "User Info Loaded", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "User details not found", Toast.LENGTH_SHORT).show()
                    Log.d("Firestore", "Document with ID $userDocId not found")
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to fetch user details: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun showAnimation() {
        dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_anim_lodaing)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }

    private fun closeAnimation() {
        dialog.dismiss()
    }
}
