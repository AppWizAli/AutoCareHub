package com.hiskytechs.autocarehub.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityEditSparePartBinding

class ActivityEditSparePart : AppCompatActivity() {
    private lateinit var binding: ActivityEditSparePartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityEditSparePartBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}