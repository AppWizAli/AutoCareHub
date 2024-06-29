package com.hiskytechs.autocarehub.Ui

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.carrepairapp.model.ModelUser
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityWorkShopAccess4Binding
import com.hiskytechs.autocarehub.databinding.ActivityWorkShopSignUpBinding
import java.util.regex.Pattern

class ActivityWorkShopSignUp : AppCompatActivity() {
    private lateinit var binding: ActivityWorkShopSignUpBinding
    private var db=Firebase.firestore
    private lateinit var dialog:Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityWorkShopSignUpBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
           var modelUser=ModelUser()
        binding.apply {
            backArrow.setOnClickListener {
                startActivity(Intent(this@ActivityWorkShopSignUp, ActivityUserChoice::class.java))
                finish()
            }

            binding.registerLink.setOnClickListener {
                startActivity(Intent(this@ActivityWorkShopSignUp, ActivityLoginWorkshop::class.java))

            }

            binding.loginButton.setOnClickListener {
                showAnimation()

                val userEmail = useremail.text.toString().trim()
                val userName = username.text.toString().trim()
                val userPassword = userpswrd.text.toString().trim()
                val confirmPassword = cpswrd.text.toString().trim()
                val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
                val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=]).{6,}$"

                if (userName.isEmpty()) {
                    username.error = "Username is required"
                    closeAnimation()
                } else if (userEmail.isEmpty()) {
                    useremail.error = "Email is required"
                    closeAnimation()
                } else if (!Pattern.compile(emailPattern).matcher(userEmail).matches()) {
                    useremail.error = "Invalid email address"
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

                    db.collection("WorkshopUser")
                        .whereEqualTo("email", userEmail)
                        .get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val querySnapshot = task.result
                                if (querySnapshot != null && !querySnapshot.isEmpty) {
                                    useremail.error = "Email is already registered"
                                    closeAnimation()
                                } else {
                                    val modelUser = ModelUser(
                                        userName = userName,
                                        email = userEmail,
                                        password = userPassword,
                                    )
                                    db.collection("WorkshopUser").add(modelUser)
                                        .addOnSuccessListener { documentRef ->
                                            modelUser.workshopOwnerID = documentRef.id
                                            db.collection("WorkshopUser").document(documentRef.id).set(modelUser)
                                                .addOnSuccessListener {
                                                    closeAnimation()
                                                    Toast.makeText(this@ActivityWorkShopSignUp, "SignUp successful", Toast.LENGTH_SHORT).show()
                                                    startActivity(Intent(this@ActivityWorkShopSignUp, ActivityLoginWorkshop::class.java))
                                                    finish()
                                                }
                                                .addOnFailureListener { e ->
                                                    Toast.makeText(this@ActivityWorkShopSignUp, "SignUp unsuccessful: ${e.message}", Toast.LENGTH_SHORT).show()
                                                    closeAnimation()
                                                }
                                        }
                                        .addOnFailureListener { e ->
                                            Toast.makeText(this@ActivityWorkShopSignUp, "SignUp unsuccessful: ${e.message}", Toast.LENGTH_SHORT).show()
                                            closeAnimation()
                                        }
                                }
                            } else {
                                Toast.makeText(this@ActivityWorkShopSignUp, "Error checking email: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                                closeAnimation()
                            }
                        }
                }
            }
        }

    }


    fun showAnimation() {
        dialog = Dialog(this@ActivityWorkShopSignUp)
        dialog.setContentView(R.layout.dialog_anim_lodaing)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }

    fun closeAnimation() {
        dialog.dismiss()

}
}