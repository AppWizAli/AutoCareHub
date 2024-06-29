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
import com.hiskytechs.autocarehub.databinding.ActivityLoginUserBinding

class ActivityLoginUser : AppCompatActivity() {
    private lateinit var binding: ActivityLoginUserBinding
    private lateinit var dialog: Dialog
    private var db=Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityLoginUserBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.apply {
            backArrow.setOnClickListener(){
                finish()
            }
            forgotPassword.setOnClickListener(){
                startActivity(Intent(this@ActivityLoginUser,ActivityForgetPassword::class.java))
                finish()
            }
            registerLink.setOnClickListener(){
                startActivity(Intent(this@ActivityLoginUser,ActivityUserSignUp::class.java))
                finish()
            }
            loginButton.setOnClickListener {
                if (useremail.text.toString().isEmpty()) {
                    useremail.error = "Email is required"}
                else if (userpswrd.text.toString().isEmpty()) {
                    userpswrd.error = "Password is required"}
                    else {
                    db.collection("User")
                        .whereEqualTo("email", useremail.text.toString())
                        .whereEqualTo("password", userpswrd.text.toString())
                        .get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val querySnapshot = task.result
                                if (querySnapshot != null && !querySnapshot.isEmpty) {
                                    val documentId = querySnapshot.documents[0].id

                                    val sharedPreferences = getSharedPreferences("Preference", Context.MODE_PRIVATE)
                                    val editor = sharedPreferences.edit()
                                    editor.putBoolean("isLog",true)
                                    editor.apply()
                                    editor.putString("userID", documentId)
                                    editor.apply()

                                    Toast.makeText(this@ActivityLoginUser, "Login Successful", Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this@ActivityLoginUser, MainActivity::class.java))
                                    finish()
                                } else {
                                    Toast.makeText(this@ActivityLoginUser, "Invalid email or password", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(this@ActivityLoginUser, "Login Unsuccessful", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }




        }
    }
}