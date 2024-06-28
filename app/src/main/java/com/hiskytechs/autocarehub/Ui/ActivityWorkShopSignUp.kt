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
            backArrow.setOnClickListener(){
                startActivity(Intent(this@ActivityWorkShopSignUp,ActivityUserChoice::class.java))
            }
            register.setOnClickListener(){
                startActivity(Intent(this@ActivityWorkShopSignUp,ActivityLoginWorkshop::class.java))
            }
            signupButton.setOnClickListener()
            {
                showAnimation()

                val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

                if (email.text.toString().isEmpty()) {
                    email.error = "Email is required"
                } else if (!Pattern.compile(emailPattern).matcher(email.text.toString()).matches()) {
                    email.error = "Invalid email address"
                } else if (username.text.toString().isEmpty()) {
                    username.error = "Username is required"
                } else if (pswrd.text.toString().isEmpty()) {
                    pswrd.error = "Password is required"
                } else if (cpassword.text.toString().isEmpty()) {
                    cpassword .error = "Confirm password is required"
                } else if (pswrd.text.toString() != cpassword.text.toString()) {
                    cpassword.error = "Passwords do not match"
                } else {
                    modelUser.userName=username.text.toString()
                    modelUser.email=email.text.toString()
                    modelUser.password=binding.pswrd.text.toString()
                    modelUser.cpassword=binding.cpassword.text.toString()
                    db.collection("WorkshopUser").add(modelUser)
                        .addOnSuccessListener {documentref->
                            modelUser.userID=documentref.id
                            db.collection("User").document(documentref.id).set(modelUser)
                            closeAnimation()
                            Toast.makeText(this@ActivityWorkShopSignUp, "SignUp successfull", Toast.LENGTH_SHORT).show()

                        }
                        .addOnFailureListener()
                        {
                            Toast.makeText(this@ActivityWorkShopSignUp, "SignUp unsuccessfull", Toast.LENGTH_SHORT).show()

                            closeAnimation()
                        }
                }}
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