package com.hiskytechs.autocarehub.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityIntro1Binding
import com.hiskytechs.autocarehub.databinding.ActivitySparePartBinding

class ActivitySparePart : AppCompatActivity() {
    private lateinit var binding: ActivitySparePartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivitySparePartBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}