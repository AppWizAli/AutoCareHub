package com.hiskytechs.autocarehub.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivitySparePartRequest2Binding
import com.hiskytechs.autocarehub.databinding.ActivityUserSignUpBinding

class ActivityUserSignUp : AppCompatActivity() {
    private lateinit var binding: ActivityUserSignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityUserSignUpBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}