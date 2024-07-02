package com.hiskytechs.autocarehub.Ui

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.hiskytechs.autocarehub.Models.MySharedPref
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityIntro1Binding
import com.hiskytechs.autocarehub.databinding.ActivityLoginUserBinding

class ActivityLoginUser : AppCompatActivity() {
    private lateinit var binding: ActivityLoginUserBinding

  private lateinit var mySharedPref: MySharedPref

    private lateinit var dialog: Dialog
    private var db=Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityLoginUserBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


       mySharedPref=MySharedPref(this@ActivityLoginUser)



        binding.apply {
            backArrow.setOnClickListener(){
                finish()
            }
            forgotPassword.setOnClickListener(){
                startActivity(Intent(this@ActivityLoginUser,ActivityForgetPassword::class.java))

            }
            registerLink.setOnClickListener(){
                startActivity(Intent(this@ActivityLoginUser,ActivityUserSignUp::class.java))

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
mySharedPref.saveUserDocId(documentId)
                                    Toast.makeText(this@ActivityLoginUser, "Login Successful", Toast.LENGTH_SHORT).show()
                                mySharedPref.saveuserLogin()


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