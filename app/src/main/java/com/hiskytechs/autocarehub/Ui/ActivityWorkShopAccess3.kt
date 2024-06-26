package com.hiskytechs.autocarehub.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityWorkShopAccess2Binding
import com.hiskytechs.autocarehub.databinding.ActivityWorkShopAccess3Binding

class ActivityWorkShopAccess3 : AppCompatActivity() {
    private lateinit var binding: ActivityWorkShopAccess3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityWorkShopAccess3Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}