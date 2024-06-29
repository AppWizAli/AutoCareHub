package com.hiskytechs.autocarehub.Ui

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityLoginAsBinding

class ActivityLoginAs : AppCompatActivity() {
    private lateinit var binding: ActivityLoginAsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginAsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val logoAnimation = AnimationUtils.loadAnimation(this, R.anim.logo_animation)
        binding.imageView5.startAnimation(logoAnimation)

        binding.user.setOnClickListener {
            startActivity(Intent(this@ActivityLoginAs, ActivityLoginUser::class.java))
            finish()
        }
        binding.wrkshop.setOnClickListener {
            startActivity(Intent(this@ActivityLoginAs, ActivityLoginWorkshop::class.java))
            finish()
        }
        binding.backArrow.setOnClickListener {
             finish()
        }
    }
}
