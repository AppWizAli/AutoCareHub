package com.hiskytechs.autocarehub.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivitySparePartRequest2Binding
import com.hiskytechs.autocarehub.databinding.ActivitySupplierRegistrationBinding

class ActivitySupplierRegistration : AppCompatActivity() {
    private lateinit var binding: ActivitySupplierRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivitySupplierRegistrationBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}