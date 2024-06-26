package com.hiskytechs.autocarehub.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityWorkShopAccess4Binding
import com.hiskytechs.autocarehub.databinding.ActivityWorkShopReg2Binding

class ActivityWorkShopReg2 : AppCompatActivity() {
    private lateinit var binding: ActivityWorkShopReg2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityWorkShopReg2Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}