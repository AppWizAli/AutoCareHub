package com.hiskytechs.autocarehub.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityIntro1Binding

class ActivityIntro1 : AppCompatActivity() {
    private lateinit var binding: ActivityIntro1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityIntro1Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}