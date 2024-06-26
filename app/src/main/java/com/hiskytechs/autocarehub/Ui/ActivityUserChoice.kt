package com.hiskytechs.autocarehub.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivitySparePartRequest2Binding
import com.hiskytechs.autocarehub.databinding.ActivityUserChoiceBinding

class ActivityUserChoice : AppCompatActivity() {
    private lateinit var binding: ActivityUserChoiceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityUserChoiceBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}