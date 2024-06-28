package com.hiskytechs.autocarehub.Ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityIntro1Binding
import com.hiskytechs.autocarehub.databinding.ActivityIntro2Binding

class ActivityIntro2 : AppCompatActivity() {
    private lateinit var binding: ActivityIntro2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityIntro2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.nextintro2.setOnClickListener(){
            startActivity(Intent(this@ActivityIntro2,ActivityIntro3::class.java))
        }
    }
}