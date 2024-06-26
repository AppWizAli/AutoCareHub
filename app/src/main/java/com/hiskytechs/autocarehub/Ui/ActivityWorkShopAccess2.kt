package com.hiskytechs.autocarehub.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivitySparePartRequest2Binding
import com.hiskytechs.autocarehub.databinding.ActivityWorkShopAccess2Binding

class ActivityWorkShopAccess2 : AppCompatActivity() {
    private lateinit var binding: ActivityWorkShopAccess2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityWorkShopAccess2Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}