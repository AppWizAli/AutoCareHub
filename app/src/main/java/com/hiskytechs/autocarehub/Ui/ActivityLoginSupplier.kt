package com.hiskytechs.autocarehub.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityIntro1Binding
import com.hiskytechs.autocarehub.databinding.ActivityLoginSupplierBinding

class ActivityLoginSupplier : AppCompatActivity() {
    private lateinit var binding: ActivityLoginSupplierBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityLoginSupplierBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}