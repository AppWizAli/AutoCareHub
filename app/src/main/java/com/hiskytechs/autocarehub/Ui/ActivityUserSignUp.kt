package com.hiskytechs.autocarehub.Ui

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.carrepairapp.model.ModelUser
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityMainBinding
import com.hiskytechs.autocarehub.databinding.ActivitySparePartRequest2Binding
import com.hiskytechs.autocarehub.databinding.ActivityUserSignUpBinding
import java.util.regex.Pattern

class ActivityUserSignUp : AppCompatActivity() {
    private lateinit var binding: ActivityUserSignUpBinding
    private var db = Firebase.firestore
    private lateinit var dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserSignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            backArrow.setOnClickListener {
               finish()
            }

            registerLink.setOnClickListener {
                startActivity(Intent(this@ActivityUserSignUp, ActivityLoginUser::class.java))
                finish()
            }

            loginButton.setOnClickListener {
                showAnimation()

                val userEmail = useremail.text.toString().trim()
                val userName = username.text.toString().trim()
                val userPassword = userpswrd.text.toString().trim()
                val confirmPassword = cpswrd.text.toString().trim()
                val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
                val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=]).{6,}$"

                if (userEmail.isEmpty()) {
                    useremail.error = "Email is required"
                    closeAnimation()
                } else if (!Pattern.compile(emailPattern).matcher(userEmail).matches()) {
                    useremail.error = "Invalid email address"
                    closeAnimation()
                } else if (userName.isEmpty()) {
                    username.error = "Username is required"
                    closeAnimation()
                } else if (userPassword.isEmpty()) {
                    userpswrd.error = "Password is required"
                    closeAnimation()
                } else if (!Pattern.compile(passwordPattern).matcher(userPassword).matches()) {
                    userpswrd.error = "Password must be at least 6 characters long and include an upper case letter, a lower case letter, a number, and a special character"
                    closeAnimation()
                } else if (confirmPassword.isEmpty()) {
                    cpswrd.error = "Confirm password is required"
                    closeAnimation()
                } else if (userPassword != confirmPassword) {
                    cpswrd.error = "Passwords do not match"
                    closeAnimation()
                } else {
                    db.collection("User")
                        .whereEqualTo("email", userEmail)
                        .get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val querySnapshot = task.result
                                if (querySnapshot != null && !querySnapshot.isEmpty) {
                                    useremail.error = "Email is already registered"
                                    closeAnimation()
                                } else {
                                    var modelUser = ModelUser(
                                        userName = userName,
                                        email = userEmail,
                                        password = userPassword,
                                    )
                                    db.collection("User").add(modelUser)
                                        .addOnSuccessListener { documentRef ->
                                            modelUser.userID = documentRef.id
                                            db.collection("User").document(documentRef.id).set(modelUser)
                                                .addOnSuccessListener {
                                                    closeAnimation()
                                                    Toast.makeText(this@ActivityUserSignUp, "SignUp successful", Toast.LENGTH_SHORT).show()
                                                    startActivity(Intent(this@ActivityUserSignUp, MainActivity::class.java))
                                                    finish()
                                                }
                                                .addOnFailureListener { e ->
                                                    Toast.makeText(this@ActivityUserSignUp, "SignUp unsuccessful", Toast.LENGTH_SHORT).show()
                                                    closeAnimation()
                                                }
                                        }
                                        .addOnFailureListener {
                                            Toast.makeText(this@ActivityUserSignUp, "SignUp unsuccessful", Toast.LENGTH_SHORT).show()
                                            closeAnimation()
                                        }
                                }
                            } else {
                                Toast.makeText(this@ActivityUserSignUp, "Error checking email", Toast.LENGTH_SHORT).show()
                                closeAnimation()
                            }
                        }
                }
            }
        }


    }


    fun showAnimation() {
        dialog = Dialog(this@ActivityUserSignUp)
        dialog.setContentView(R.layout.dialog_anim_lodaing)
  dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }

    fun closeAnimation() {
        dialog.dismiss()
    }


}