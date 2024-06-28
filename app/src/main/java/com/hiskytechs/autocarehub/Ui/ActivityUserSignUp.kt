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
        var modelUser=ModelUser()

binding.apply {
    backArrow.setOnClickListener(){
        startActivity(Intent(this@ActivityUserSignUp,ActivityUserChoice::class.java))
            finish()
    }
    registerLink.setOnClickListener(){
        startActivity(Intent(this@ActivityUserSignUp,ActivityLoginUser::class.java))
        finish()
    }
       loginButton.setOnClickListener()
        {
            showAnimation()

            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
            val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=]).{6,}$"
            if (useremail.text.toString().isEmpty()) {
                useremail.error = "Email is required"
            } else if (!Pattern.compile(emailPattern).matcher(useremail.text.toString()).matches()) {
                useremail.error = "Invalid email address"
            } else if (username.text.toString().isEmpty()) {
                username.error = "Username is required"
            } else if (userpswrd.text.toString().isEmpty()) {
                userpswrd.error = "Password is required"
            }
            else if (!Pattern.compile(passwordPattern).matcher(userpswrd.text.toString()).matches()) {
                userpswrd.error = "Password must be at least 6 characters long and include an upper case letter, a lower case letter, a number, and a special character"}
            else if (cpswrd.text.toString().isEmpty()) {
                cpswrd.error = "Confirm password is required"
            } else if (userpswrd.text.toString() != cpswrd.text.toString()) {
                cpswrd.error = "Passwords do not match"
            } else {
                modelUser.userName=username.text.toString()
            modelUser.email=useremail.text.toString()
            modelUser.password=binding.userpswrd.text.toString()
            modelUser.cpassword=binding.cpswrd.text.toString()
            db.collection("User").add(modelUser)
                .addOnSuccessListener {documentref->
                    modelUser.userID=documentref.id
                    db.collection("User").document(documentref.id).set(modelUser)
                    closeAnimation()
                    Toast.makeText(this@ActivityUserSignUp, "SignUp successfull", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@ActivityUserSignUp, MainActivity::class.java))
                    finish()
                }
                .addOnFailureListener()
                {
                    Toast.makeText(this@ActivityUserSignUp, "SignUp unsuccessfull", Toast.LENGTH_SHORT).show()

                    closeAnimation()
                }
        }}
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