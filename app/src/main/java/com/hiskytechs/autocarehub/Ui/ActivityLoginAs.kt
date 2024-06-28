package com.hiskytechs.autocarehub.Ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityLoginAsBinding

class ActivityLoginAs : AppCompatActivity() {
    private lateinit var binding:ActivityLoginAsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding= ActivityLoginAsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.user.setOnClickListener(){
            startActivity(Intent(this@ActivityLoginAs,ActivityLoginUser::class.java))
            finish()
        }
        binding.wrkshop.setOnClickListener(){
            startActivity(Intent(this@ActivityLoginAs,ActivityLoginWorkshop::class.java))
            finish()
        }
        binding.backArrow.setOnClickListener(){
            startActivity(Intent(this@ActivityLoginAs,ActivityUserChoice::class.java))
            finish()
        }
    }
}