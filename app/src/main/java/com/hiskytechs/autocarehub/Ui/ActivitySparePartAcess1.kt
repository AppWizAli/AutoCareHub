package com.hiskytechs.autocarehub.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityIntro1Binding
import com.hiskytechs.autocarehub.databinding.ActivitySparePartAcess1Binding

class ActivitySparePartAcess1 : AppCompatActivity() {
    private lateinit var binding: ActivitySparePartAcess1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivitySparePartAcess1Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}