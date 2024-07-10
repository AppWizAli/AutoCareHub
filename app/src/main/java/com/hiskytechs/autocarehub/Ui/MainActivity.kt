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
import com.hiskytechs.autocarehub.Models.MySharedPref
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.activity_user_registration
import com.hiskytechs.autocarehub.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var mySharedPref: MySharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
mySharedPref=MySharedPref(this)
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
            R.id.nav_profile ->
            {
                startActivity(Intent(this,activity_user_registration::class.java))
            }
            R.id.nav_support -> navController.navigate(R.id.fragmentSupport)
            R.id.nav_privacy_policy -> navController.navigate(R.id.fragmentPrivacy)
            R.id.nav_share -> share()
            R.id.nav_logout -> showLogoutDialog()
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }

    private fun showLogoutDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Logout!!")
        builder.setMessage("Are you sure you want to logout?")
        builder.setPositiveButton("Yes") { dialog, _ ->
            // Clear user login state
            val mySharedPref = MySharedPref(this)
            mySharedPref.clearUserLogin()  // Clear workshop login state



            val intent = Intent(this, ActivityUserChoice::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
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
    private fun share(){
        val content = "https://play.google.com/store/apps/details?id=com.tencent.ig"

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, content)
        val chooserIntent = Intent.createChooser(intent, "Share via")

        startActivity(chooserIntent)
    }
}
