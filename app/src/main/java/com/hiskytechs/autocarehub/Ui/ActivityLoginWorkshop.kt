package com.hiskytechs.autocarehub.Ui


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.carrepairapp.model.ModelUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.hiskytechs.autocarehub.Models.ModelWorkshop
import com.hiskytechs.autocarehub.Models.MySharedPref
import com.hiskytechs.autocarehub.databinding.ActivityLoginWorkshopBinding
import com.hiskytechs.autocarehub.databinding.ActivityWorkshophomeBinding

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

        // checkLoginStatus()

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
                val userEmail = useremail.text.toString()
                val userPassword = userpswrd.text.toString()

                db.collection("WorkshopUser")
                    .whereEqualTo("userName", userEmail)
                    .whereEqualTo("password", userPassword)
                    .get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val querySnapshot = task.result
                            if (querySnapshot != null && !querySnapshot.isEmpty) {
                                val document = querySnapshot.documents[0]!!
                                val isRegister =
                                    document.toObject(ModelUser::class.java)?.isRegister

                                Toast.makeText(
                                    this@ActivityLoginWorkshop,
                                    "Login Successful",
                                    Toast.LENGTH_SHORT
                                ).show()
                                mySharedPref.saveworkshopLogin()
                                mySharedPref.saveWorkShopDocId(document.id.toString())
                                Toast.makeText(
                                    this@ActivityLoginWorkshop,
                                    isRegister.toString(),
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                if (isRegister == true) {
                                    Log.d("Login", "User is registered, navigating to Home")
                                    startActivity(
                                        Intent(
                                            this@ActivityLoginWorkshop,
                                            ActivityActivityWorkShopHome::class.java
                                        )
                                    )
                                } else {
                                    Log.d(
                                        "Login",
                                        "User is not registered, navigating to Registration"
                                    )
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

    private fun checkLoginStatus() {
        val userEmail = binding.useremail.text.toString()
        val userPassword = binding.userpswrd.text.toString()

        db.collection("WorkshopUser")
            .whereEqualTo("userName", userEmail)
            .whereEqualTo("password", userPassword)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val querySnapshot = task.result
                    if (querySnapshot != null && !querySnapshot.isEmpty) {
                        val document = querySnapshot.documents[0]
                        val isRegister = document.getBoolean("isRegister") ?: false

                        if (isRegister) {
                            Log.d("Login", "User is registered, navigating to Home")
                            startActivity(Intent(this, ActivityActivityWorkShopHome::class.java))
                        } else {
                            Log.d(
                                "Login",
                                "User is not registered, navigating to Registration"
                            )
                            startActivity(
                                Intent(
                                    this,
                                    ActivityWorkshopRegistration::class.java
                                )
                            )
                        }
                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            "Invalid email or password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this,
                        "Login Unsuccessful",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}