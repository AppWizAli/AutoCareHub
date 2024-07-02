package com.hiskytechs.autocarehub

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.hiskytechs.autocarehub.databinding.ActivityWorkshophomeBinding

class workshophome : AppCompatActivity() {
    private lateinit var binding:ActivityWorkshophomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding=ActivityWorkshophomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            profileImage.setOnClickListener(){
                startActivity(Intent(this@workshophome,ActivityWorkshopProfile::class.java))
            }
            ar.setOnClickListener(){
                startActivity(Intent(this@workshophome,ActivityWorkshopApproveReq::class.java))
            }
            pr.setOnClickListener(){
                startActivity(Intent(this@workshophome,ActivityWorkshopPendingReq::class.java))
            }
        }
        }
    }
