package com.hiskytechs.autocarehub.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityWorkShopAccess2Binding
import com.hiskytechs.autocarehub.databinding.ActivityWorkShopAccess4Binding
import com.hiskytechs.autocarehub.databinding.ActivityWorkShopAccess5Binding

class ActivityWorkShopAccess5 : AppCompatActivity() {
    private lateinit var binding: ActivityWorkShopAccess5Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityWorkShopAccess5Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}