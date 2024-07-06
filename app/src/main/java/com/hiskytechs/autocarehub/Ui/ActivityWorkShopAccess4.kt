package com.hiskytechs.autocarehub.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityWorkShopAccess2Binding
import com.hiskytechs.autocarehub.databinding.ActivityWorkShopAccess4Binding

class ActivityWorkShopAccess4 : AppCompatActivity() {
    private lateinit var binding: ActivityWorkShopAccess4Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityWorkShopAccess4Binding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}