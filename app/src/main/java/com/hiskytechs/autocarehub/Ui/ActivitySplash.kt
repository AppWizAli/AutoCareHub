package com.hiskytechs.autocarehub.Ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivitySplashBinding

class ActivitySplash : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val logoAnimation = AnimationUtils.loadAnimation(this, R.anim.logo_animation)
        val textAnimation = AnimationUtils.loadAnimation(this, R.anim.text_animation)
        binding.logo.startAnimation(logoAnimation)
        binding.textview.startAnimation(textAnimation)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@ActivitySplash, ActivityIntro1::class.java))
            finish()
        }, 3000)
    }
}
