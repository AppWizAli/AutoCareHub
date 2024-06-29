package com.hiskytechs.autocarehub.Ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityIntro1Binding

class ActivityIntro1 : AppCompatActivity() {
    private lateinit var binding: ActivityIntro1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntro1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val imageAnimation = AnimationUtils.loadAnimation(this, R.anim.intro_image_animation)
        binding.image.startAnimation(imageAnimation)
        binding.nextintro1.setOnClickListener {
            startActivity(Intent(this@ActivityIntro1, ActivityIntro2::class.java))
            finish()
        }
    }
}
