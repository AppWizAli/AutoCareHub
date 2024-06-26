package com.hiskytechs.autocarehub.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityWorkShopAccess4Binding
import com.hiskytechs.autocarehub.databinding.ActivityWorkshopRegistrationBinding

class ActivityWorkshopRegistration : AppCompatActivity() {
    private lateinit var binding: ActivityWorkshopRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityWorkshopRegistrationBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}