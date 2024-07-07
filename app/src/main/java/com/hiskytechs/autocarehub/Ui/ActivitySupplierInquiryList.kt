package com.hiskytechs.autocarehub.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivitySupplierInquiryListBinding

class ActivitySupplierInquiryList : AppCompatActivity() {
    private lateinit var binding: ActivitySupplierInquiryListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivitySupplierInquiryListBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}