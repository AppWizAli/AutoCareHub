package com.hiskytechs.autocarehub.Ui

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityIntro1Binding
import com.hiskytechs.autocarehub.databinding.ActivityLoginWorkshopBinding

class ActivityLoginWorkshop : AppCompatActivity() {
    private lateinit var binding: ActivityLoginWorkshopBinding
    private lateinit var dialog: Dialog
    private var db= Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginWorkshopBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.apply {
                backArrow.setOnClickListener(){
                    startActivity(Intent(this@ActivityLoginWorkshop,ActivityUserChoice::class.java))
                }
                forgotPassword.setOnClickListener(){
                    startActivity(Intent(this@ActivityLoginWorkshop,ActivityForgetPassword::class.java))
                }
                register.setOnClickListener(){
                    startActivity(Intent(this@ActivityLoginWorkshop,ActivityUserSignUp::class.java))
                }
            loginButton.setOnClickListener {
                if (username.text.toString().isEmpty()) {
                    username.error = "Email is required"}
                else if (password.text.toString().isEmpty()) {
                    password.error = "Password is required"}
                 else {
                    db.collection("WorkshopUser")
                        .whereEqualTo("userName", username.text.toString())
                        .whereEqualTo("password", password.text.toString())
                        .get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val querySnapshot = task.result
                                if (querySnapshot != null && !querySnapshot.isEmpty) {
                                    val documentId = querySnapshot.documents[0].id

                                    val sharedPreferences = getSharedPreferences("preference", Context.MODE_PRIVATE)
                                    val editor = sharedPreferences.edit()
                                    editor.putBoolean("IsLog",true)
                                    editor.apply()
                                    editor.putString("workshopOwnerID", documentId)
                                    editor.apply()

                                    Toast.makeText(this@ActivityLoginWorkshop, "Login Successful", Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this@ActivityLoginWorkshop, MainActivity::class.java))
                                    finish()
                                } else {
                                    Toast.makeText(this@ActivityLoginWorkshop, "Invalid email or password", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(this@ActivityLoginWorkshop, "Login Unsuccessful", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }}




        }
    }
