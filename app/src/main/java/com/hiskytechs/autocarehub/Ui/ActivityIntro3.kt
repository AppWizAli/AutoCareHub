package com.hiskytechs.autocarehub.Ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityIntro1Binding
import com.hiskytechs.autocarehub.databinding.ActivityIntro3Binding

class ActivityIntro3 : AppCompatActivity() {
    private lateinit var binding: ActivityIntro3Binding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding= ActivityIntro3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load the animation
        val imageAnimation = AnimationUtils.loadAnimation(this, R.anim.intro_image_animation)
        binding.image.startAnimation(imageAnimation)

        binding.getStartedButton.setOnClickListener(){
            startActivity(Intent(this@ActivityIntro3,ActivityUserChoice::class.java))
            finish()
        }
    }
}