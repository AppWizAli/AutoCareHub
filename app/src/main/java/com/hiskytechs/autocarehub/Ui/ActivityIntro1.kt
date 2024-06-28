package com.hiskytechs.autocarehub.Ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityIntro1Binding

class ActivityIntro1 : AppCompatActivity() {
    private lateinit var binding: ActivityIntro1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityIntro1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.nextintro1.setOnClickListener(){
            startActivity(Intent(this@ActivityIntro1,ActivityIntro2::class.java))
            finish()
        }
    }
}