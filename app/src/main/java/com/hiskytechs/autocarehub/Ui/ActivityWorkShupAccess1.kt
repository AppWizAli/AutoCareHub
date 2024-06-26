package com.hiskytechs.autocarehub.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityWorkShopAccess4Binding
import com.hiskytechs.autocarehub.databinding.ActivityWorkShopAccess5Binding
import com.hiskytechs.autocarehub.databinding.ActivityWorkShupAccess1Binding

class ActivityWorkShupAccess1 : AppCompatActivity() {
    private lateinit var binding: ActivityWorkShupAccess1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityWorkShupAccess1Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}