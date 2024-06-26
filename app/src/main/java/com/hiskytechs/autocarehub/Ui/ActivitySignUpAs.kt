package com.hiskytechs.autocarehub.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityIntro1Binding
import com.hiskytechs.autocarehub.databinding.ActivitySignUpAsBinding

class ActivitySignUpAs : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpAsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivitySignUpAsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}