package com.hiskytechs.autocarehub.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityIntro1Binding
import com.hiskytechs.autocarehub.databinding.ActivityMapView1Binding

class ActivityMapView1 : AppCompatActivity() {
    private lateinit var binding: ActivityMapView1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityMapView1Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}