package com.hiskytechs.autocarehub.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityEditSparePartBinding

class ActivityEditSparePart : AppCompatActivity() {
    private lateinit var binding: ActivityEditSparePartBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding=ActivityEditSparePartBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}