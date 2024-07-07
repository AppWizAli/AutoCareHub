package com.hiskytechs.autocarehub.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivitySupplierSignUpBinding

class ActivitySupplierSignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySupplierSignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivitySupplierSignUpBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}