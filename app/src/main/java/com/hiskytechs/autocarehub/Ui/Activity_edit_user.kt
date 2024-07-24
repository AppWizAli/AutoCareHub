package com.hiskytechs.autocarehub.Ui

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.carrepairapp.model.ModelUser
import com.google.firebase.firestore.FirebaseFirestore
import com.hiskytechs.autocarehub.Models.MySharedPref
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityEditUserBinding

class Activity_edit_user : AppCompatActivity() {
    private lateinit var binding: ActivityEditUserBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var mySharedPref: MySharedPref
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = FirebaseFirestore.getInstance()
        mySharedPref = MySharedPref(this)
        binding.backArrow.setOnClickListener {
            finish()
        }
        binding.saveButton.setOnClickListener {
            showAnimation()
            val updatedUser = ModelUser(
                userName = binding.username.text.toString(),
                email = binding.useremail.text.toString(),
                address = binding.address.text.toString(),
                phoneNumber = binding.phoneno.text.toString()
            )

            val userDocId = mySharedPref.getUserDocId()

            db.collection("User").document(userDocId)
                .set(updatedUser)
                .addOnSuccessListener {
                    closeAnimation()
                    Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                    // Finish this activity and return to the previous activity
                    finish()  // This will return to the previous activity
                }
                .addOnFailureListener { e ->
                    closeAnimation()
                    Toast.makeText(this, "Failed to update profile: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }}
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
