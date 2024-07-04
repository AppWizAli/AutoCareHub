package com.hiskytechs.autocarehub.Ui


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.hiskytechs.autocarehub.Models.ModelWorkshop
import com.hiskytechs.autocarehub.Models.MySharedPref
import com.hiskytechs.autocarehub.databinding.ActivityLoginWorkshopBinding

class ActivityLoginWorkshop : AppCompatActivity() {

    private lateinit var binding: ActivityLoginWorkshopBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var mySharedPref: MySharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginWorkshopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()
        mySharedPref = MySharedPref(this)

        checkLoginStatus()

        binding.apply {

            backArrow.setOnClickListener {
                startActivity(Intent(this@ActivityLoginWorkshop, ActivityUserChoice::class.java))
            }
            forgotPassword.setOnClickListener {
                startActivity(
                    Intent(
                        this@ActivityLoginWorkshop,
                        ActivityForgetPassword::class.java
                    )
                )
            }
            register.setOnClickListener {
                startActivity(
                    Intent(
                        this@ActivityLoginWorkshop,
                        ActivityWorkShopSignUp::class.java
                    )
                )
            }
            loginButton.setOnClickListener {
                if (useremail.text.toString().isEmpty()) {
                    useremail.error = "Email is required"
                } else if (userpswrd.text.toString().isEmpty()) {
                    userpswrd.error = "Password is required"
                } else {
                    db.collection("WorkshopUser")
                        .whereEqualTo("userName", useremail.text.toString())
                        .whereEqualTo("password", userpswrd.text.toString())
                        .get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val querySnapshot = task.result
                                if (querySnapshot != null && !querySnapshot.isEmpty) {
                                    val document = querySnapshot.documents[0]!!
                                  var isRegister=document.toObject(ModelWorkshop::class.java)?.isRegister

                                    Toast.makeText(
                                        this@ActivityLoginWorkshop,
                                        "Login Successful",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    mySharedPref.saveworkshopLogin(document.id)

                                    if (isRegister == true) {
                                        startActivity(
                                            Intent(
                                                this@ActivityLoginWorkshop,
                                                ActivityWorkShopHome::class.java
                                            )
                                        )
                                    } else {
                                        startActivity(
                                            Intent(
                                                this@ActivityLoginWorkshop,
                                                ActivityWorkshopRegistration::class.java
                                            )
                                        )
                                    }
                                    finish()
                                } else {
                                    Toast.makeText(
                                        this@ActivityLoginWorkshop,
                                        "Invalid email or password",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                Toast.makeText(
                                    this@ActivityLoginWorkshop,
                                    "Login Unsuccessful",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }
        }
    }
    private fun checkLoginStatus() {
        val userId = mySharedPref.getUserDocId()
        if (userId != null) {
            db.collection("WorkshopUser").document(userId).get().addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val isRegister = document.getBoolean("isRegister") ?: false
                    if (isRegister) {
                        startActivity(Intent(this, ActivityWorkShopHome::class.java))
                    } else {
                        startActivity(Intent(this, ActivityWorkshopRegistration::class.java))
                    }
                    finish()
                }
            }
        }
    }


}


