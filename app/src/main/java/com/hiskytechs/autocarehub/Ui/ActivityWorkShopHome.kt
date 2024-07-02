package com.hiskytechs.autocarehub.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hiskytechs.autocarehub.databinding.ActivityWorkShopReg2Binding

class ActivityWorkShopHome : AppCompatActivity() {
    private lateinit var binding: ActivityWorkShopReg2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityWorkShopReg2Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}