package com.hiskytechs.autocarehub.Ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityIntro1Binding
import com.hiskytechs.autocarehub.databinding.ActivitySignUpAsBinding

class ActivitySignUpAs : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpAsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignUpAsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.user.setOnClickListener(){
            startActivity(Intent(this@ActivitySignUpAs,ActivityUserSignUp::class.java))
        }
        binding.wrkshop.setOnClickListener(){
            startActivity(Intent(this@ActivitySignUpAs,ActivityWorkShopSignUp::class.java))
        }
    }
}