package com.hiskytechs.autocarehub.Ui
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityMainBinding
import com.hiskytechs.autocarehub.databinding.ActivityWorkShopAccess4Binding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}