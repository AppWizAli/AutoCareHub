package com.hiskytechs.autocarehub.Ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import com.hiskytechs.autocarehub.Models.MySharedPref
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivitySplashBinding

class ActivitySplash : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var mySharedPref: MySharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
  mySharedPref=MySharedPref(this@ActivitySplash)
        val logoAnimation = AnimationUtils.loadAnimation(this, R.anim.logo_animation)
        val textAnimation = AnimationUtils.loadAnimation(this, R.anim.text_animation)
        binding.logo.startAnimation(logoAnimation)
        binding.textview.startAnimation(textAnimation)
        Handler(Looper.getMainLooper()).postDelayed({

            if(mySharedPref.IsUserLoggedIn()==true)
            {
                startActivity(Intent(this@ActivitySplash,MainActivity::class.java))
            }
            else if(mySharedPref.IsworkLogedIn()==true)
            {
                startActivity(Intent(this@ActivitySplash,workshophome::class.java))
            }

            else
            {
                startActivity(Intent(this@ActivitySplash,ActivityUserChoice::class.java))
            }



        }, 3000)
    }
}
