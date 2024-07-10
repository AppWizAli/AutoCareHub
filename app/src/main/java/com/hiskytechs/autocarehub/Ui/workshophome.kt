package com.hiskytechs.autocarehub.Ui

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.hiskytechs.autocarehub.Models.ModelOffers
import com.hiskytechs.autocarehub.Models.ModelRequest
import com.hiskytechs.autocarehub.Models.MySharedPref
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityWorkshophomeBinding
import com.hiskytechs.autocarehub.models.ModelNews
import de.hdodenhof.circleimageview.CircleImageView
import java.util.UUID

class workshophome : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityWorkshophomeBinding
private lateinit var mySharedPref: MySharedPref
    private val storage = FirebaseStorage.getInstance()
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private val firestore = FirebaseFirestore.getInstance()
    private val newsCollection = firestore.collection("News")
    private val offersCollection = firestore.collection("Offers")
    private var imageUri: Uri? = null
    private var currentImageView: CircleImageView? = null
    private var workshopId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkshophomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        drawerToggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        binding.navigationView.setNavigationItemSelectedListener(this)
        findViewById<ImageView>(R.id.menu_icon).setOnClickListener {
            toggleDrawer()
        }


        mySharedPref= MySharedPref(this@workshophome)
        workshopId = mySharedPref.getWorkShopDocId()
        binding.apply {

            ar.setOnClickListener {
                startActivity(Intent(this@workshophome, ActivityWorkshopApproveReq::class.java))
            }
            pr.setOnClickListener {
                startActivity(Intent(this@workshophome, ActivityWorkshopPendingReq::class.java))
            }
            seeAll.setOnClickListener {
                startActivity(Intent(this@workshophome, ActivitySeeAllSpareParts::class.java))
            }
            add.setOnClickListener {
                startActivity(Intent(this@workshophome, ActivityAddSparePart1::class.java))
            }
            addNews.setOnClickListener {
                openAddNewsDialog()
            }


            firestore.collection("Requests").whereEqualTo("workshopDocId",mySharedPref.getWorkShopDocId())
                .get()
                .addOnSuccessListener {  querry->
                    var approveCount:Int=0
                    var pendingCount:Int=0



                    for (document in querry.documents)
                    {
                        var modelRequest=document.toObject(ModelRequest::class.java)!!
                        if(modelRequest.requestType=="Approved")
                        {
                            approveCount++
                        }
                        if(modelRequest.requestType=="Pending")
                        {
                            pendingCount++

                        }
                    }


                    binding.tvpending.text=pendingCount.toString()
                    binding.approved.text=approveCount.toString()


                }



            AddOffers.setOnClickListener {
                openAddOfferDialog()
            }
            fetchUserData()
        }
    }
    private fun fetchUserData() {
        // Check if Workshop ID is available
        workshopId?.let { id ->
            // Fetch data from Firestore
            firestore.collection("WorkshopUser").document(id)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val username = document.getString("userName")
                        val profileImageUrl = document.getString("profilePictureUrl")

                        // Display data in UI elements
                        binding.apply {
                            tvUsername.text = username
                        //   Glide.with(this@workshophome).load(profileImageUrl).into(profil)// Assuming you have an extension function to load image
                        }
                    } else {
                        Toast.makeText(this@workshophome, "User data not found", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this@workshophome, "Failed to fetch user data: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
    private fun toggleDrawer() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }
    private fun openAddOfferDialog() {
        val dialogView = LayoutInflater.from(this@workshophome).inflate(R.layout.dialog_add_offer, null)
        val dialog = AlertDialog.Builder(this@workshophome)
            .setView(dialogView)
            .create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent) // Set background to transparent

        val offerImage = dialogView.findViewById<CircleImageView>(R.id.offer_image)
        val offerTitle = dialogView.findViewById<EditText>(R.id.offer_title)
        val offerDescription = dialogView.findViewById<EditText>(R.id.offer_description)
        val offerValidUntil = dialogView.findViewById<EditText>(R.id.offer_valid_until)
        val addOfferButton = dialogView.findViewById<Button>(R.id.add_offer)

        offerImage.setOnClickListener {
            currentImageView = offerImage
            pickImageFromGallery()
        }

        addOfferButton.setOnClickListener {
            val title = offerTitle.text.toString().trim()
            val description = offerDescription.text.toString().trim()
            val validUntil = offerValidUntil.text.toString().trim()

            if (title.isNotEmpty() && description.isNotEmpty() && validUntil.isNotEmpty() && imageUri != null) {
                uploadOfferImage(imageUri!!, title, description, validUntil, dialog)
            } else {
                Toast.makeText(this@workshophome, "All fields are required", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    private fun openAddNewsDialog() {
        val dialogView = LayoutInflater.from(this@workshophome).inflate(R.layout.dialog_add_news, null)
        val dialog = AlertDialog.Builder(this@workshophome)
            .setView(dialogView)
            .create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent) // Set background to transparent

        val newsImage = dialogView.findViewById<CircleImageView>(R.id.news_image)
        val newsTitle = dialogView.findViewById<EditText>(R.id.news_title)
        val newsDescription = dialogView.findViewById<EditText>(R.id.news_description)
        val addNewsButton = dialogView.findViewById<Button>(R.id.btn_add_news)

        newsImage.setOnClickListener {
            currentImageView = newsImage
            pickImageFromGallery()
        }

        addNewsButton.setOnClickListener {
            val title = newsTitle.text.toString().trim()
            val description = newsDescription.text.toString().trim()

            if (title.isNotEmpty() && description.isNotEmpty() && imageUri != null) {
                uploadNewsImage(imageUri!!, title, description, dialog)
            } else {
                Toast.makeText(this@workshophome, "All fields are required", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            currentImageView?.let {
                Glide.with(this).load(imageUri).into(it)
            }
        }
    }

    private fun uploadNewsImage(uri: Uri, title: String, description: String, dialog: AlertDialog) {
        val fileName = UUID.randomUUID().toString() + ".jpg"
        val ref = storage.reference.child("news_images/$fileName")

        ref.putFile(uri).addOnSuccessListener {
            ref.downloadUrl.addOnSuccessListener { downloadUrl ->
                val news = ModelNews(downloadUrl.toString(), title, description)
                newsCollection.add(news).addOnSuccessListener {
                    Toast.makeText(this@workshophome, "News added successfully", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            }
        }.addOnFailureListener {
            Toast.makeText(this@workshophome, "Failed to upload image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadOfferImage(uri: Uri, title: String, description: String, validUntil: String, dialog: AlertDialog) {
        val fileName = UUID.randomUUID().toString() + ".jpg"
        val ref = storage.reference.child("offer_images/$fileName")

        ref.putFile(uri).addOnSuccessListener {
            ref.downloadUrl.addOnSuccessListener { downloadUrl ->
                val offer = ModelOffers(downloadUrl.toString(), title, description, validUntil)
                offersCollection.add(offer).addOnSuccessListener {
                    Toast.makeText(this@workshophome, "Offer added successfully", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            }
        }.addOnFailureListener {
            Toast.makeText(this@workshophome, "Failed to upload image", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_contact -> {

                val dialogView = LayoutInflater.from(this).inflate(R.layout.contactus, null)
                val dialogBuilder = androidx.appcompat.app.AlertDialog.Builder(this)
                    .setView(dialogView)
                    .setCancelable(true)
                val dialog = dialogBuilder.create()
                dialog.show()

                dialogView.findViewById<TextView>(R.id.phoneno).setOnClickListener {
                    val phoneNumber = "+923336052711"
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:$phoneNumber")
                    startActivity(intent)
                    dialog.dismiss()
                }

                dialogView.findViewById<TextView>(R.id.mail).setOnClickListener {
                    val email = "madihakhalid403@gmail.com"
                    val subject = "Regarding your app"
                    val intent = Intent(Intent.ACTION_SENDTO)
                    intent.data = Uri.parse("mailto:$email?subject=$subject")
                    startActivity(intent)
                    dialog.dismiss()
                }

                dialogView.findViewById<TextView>(R.id.whtsapno).setOnClickListener {
                    val phoneNumber = "+923227970173"

                    val url = "https://api.whatsapp.com/send?phone=$phoneNumber"
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    startActivity(intent)
                    dialog.dismiss()
                }

            }
            R.id.nav_rate -> {
                val appPackageName = "com.tencent.ig"
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
                } catch (e: ActivityNotFoundException) {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
                }

            }
            R.id.nav_share -> {
                val content = "https://play.google.com/store/apps/details?id=com.tencent.ig"

                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, content)
                val chooserIntent = Intent.createChooser(intent, "Share via")

                startActivity(chooserIntent)
            }
            R.id.nav_logout -> {
                showLogoutDialog()

            }
        }
        binding.drawerLayout.closeDrawers()
        return true
    }


    private fun showLogoutDialog() {
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setTitle("Logout!!")
        builder.setMessage("Are you sure you want to logout?")
        builder.setPositiveButton("Yes") { dialog, _ ->
            // Clear user login state
            val mySharedPref = MySharedPref(this)
            mySharedPref.clearworkshopLogin() // Clear workshop login state



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
}
