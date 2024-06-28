package com.hiskytechs.autocarehub.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityIntro1Binding
import com.hiskytechs.autocarehub.databinding.ActivityLoginWorkshopBinding

class ActivityLoginWorkshop : AppCompatActivity() {
    private lateinit var binding: ActivityLoginWorkshopBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginWorkshopBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}