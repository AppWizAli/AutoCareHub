package com.hiskytechs.autocarehub.Ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.hiskytechs.autocarehub.Models.ModelOffers
import com.hiskytechs.autocarehub.Models.MySharedPref
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityWorkshophomeBinding
import com.hiskytechs.autocarehub.models.ModelNews
import de.hdodenhof.circleimageview.CircleImageView
import java.util.UUID

class workshophome : AppCompatActivity() {
    private lateinit var binding: ActivityWorkshophomeBinding
private lateinit var mySharedPref: MySharedPref
    private val storage = FirebaseStorage.getInstance()
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
}
