package com.hiskytechs.autocarehub.Ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.iconStart.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            onNavItemSelected(menuItem)
            true
        }

        setupLogoAnimation()
    }

    private fun onNavItemSelected(menuItem: MenuItem) {
        when (menuItem.itemId) {
            R.id.nav_profile -> navController.navigate(R.id.fragmentProfile)
            R.id.nav_support -> navController.navigate(R.id.fragmentSupport)
            R.id.nav_privacy_policy -> navController.navigate(R.id.fragmentPrivacy)
            R.id.nav_share -> Toast.makeText(this@MainActivity, "Share the app", Toast.LENGTH_SHORT).show()
            R.id.nav_logout -> showLogoutDialog()
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }

    private fun showLogoutDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Logout!!")
        builder.setMessage("Are you sure you want to logout?")
        builder.setPositiveButton("Yes") { dialog, _ ->
            // Navigate to ActivityChoice
            val intent = Intent(this, ActivityUserChoice::class.java)
            startActivity(intent)
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        builder.create().show()
    }

    private fun setupLogoAnimation() {
        val rotate = RotateAnimation(
            0f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotate.duration = 2000 // 2 seconds
        rotate.repeatCount = Animation.INFINITE
        binding.appLogo.startAnimation(rotate)
    }
}
