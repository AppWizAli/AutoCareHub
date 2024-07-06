package com.hiskytechs.autocarehub.Ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hiskytechs.autocarehub.ActivityAddSpareParts
import com.hiskytechs.autocarehub.databinding.ActivityWorkShopReg2Binding
import com.hiskytechs.autocarehub.databinding.ActivityWorkshophomeBinding

class ActivityWorkShopHome : AppCompatActivity() {
    private lateinit var binding: ActivityWorkshophomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityWorkshophomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            addsp.setOnClickListener(){
                startActivity(Intent(this@ActivityWorkShopHome,ActivityAddSpareParts::class.java))
            }
            see.setOnClickListener(){
                startActivity(Intent(this@ActivityWorkShopHome,ActivitySparePart::class.java))
            }
        }
    }
}