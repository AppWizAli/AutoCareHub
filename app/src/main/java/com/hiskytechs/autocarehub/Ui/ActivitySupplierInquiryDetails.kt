package com.hiskytechs.autocarehub.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivitySparePartRequest2Binding
import com.hiskytechs.autocarehub.databinding.ActivitySupplierInquiryDetailsBinding

class ActivitySupplierInquiryDetails : AppCompatActivity() {
    private lateinit var binding: ActivitySupplierInquiryDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySupplierInquiryDetailsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}