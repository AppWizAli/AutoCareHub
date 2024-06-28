package com.hiskytechs.autocarehub.Ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivitySparePartRequest2Binding
import com.hiskytechs.autocarehub.databinding.ActivityUserChoiceBinding

class ActivityUserChoice : AppCompatActivity() {
    private lateinit var binding: ActivityUserChoiceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUserChoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lgn.setOnClickListener(){
          startActivity(Intent(this@ActivityUserChoice,ActivityLoginAs::class.java))
        }
        binding.sgn.setOnClickListener(){
          startActivity(Intent(this@ActivityUserChoice,ActivitySignUpAs::class.java))
        }
    }
}