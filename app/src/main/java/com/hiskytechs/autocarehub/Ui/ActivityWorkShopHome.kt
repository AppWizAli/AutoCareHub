package com.hiskytechs.autocarehub.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hiskytechs.autocarehub.databinding.ActivityWorkShopReg2Binding
import com.hiskytechs.autocarehub.databinding.ActivityWorkshophomeBinding

class ActivityWorkShopHome : AppCompatActivity() {
    private lateinit var binding: ActivityWorkshophomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding= ActivityWorkshophomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}