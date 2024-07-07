package com.hiskytechs.autocarehub.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivitySparePartRequest3Binding

class ActivitySparePartRequest3 : AppCompatActivity() {
    private lateinit var binding: ActivitySparePartRequest3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivitySparePartRequest3Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}