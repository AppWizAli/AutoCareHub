package com.hiskytechs.autocarehub.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityWorkShopAccess4Binding
import com.hiskytechs.autocarehub.databinding.ActivityWorkShopSignUpBinding

class ActivityWorkShopSignUp : AppCompatActivity() {
    private lateinit var binding: ActivityWorkShopSignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityWorkShopSignUpBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}