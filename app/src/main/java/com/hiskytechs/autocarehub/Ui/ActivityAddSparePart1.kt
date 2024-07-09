package com.hiskytechs.autocarehub.Ui

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.hiskytechs.autocarehub.Models.ModelSparePart
import com.hiskytechs.autocarehub.Models.MySharedPref
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityAddSparePart1Binding
import java.util.*

class ActivityAddSparePart1 : AppCompatActivity() {
    private lateinit var binding: ActivityAddSparePart1Binding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var storage: FirebaseStorage
      private var imageUri: Uri? = null
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSparePart1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()

        binding.chooseImageButton.setOnClickListener {
            chooseImage()
        }

        binding.addSparePartButton.setOnClickListener {

            addSparePart()
        }
    }

    private fun chooseImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
            binding.partImageView.setImageURI(imageUri)
            binding.partImageView.visibility = ImageView.VISIBLE
        }
    }

    private fun addSparePart() {
        var mySharedPref=MySharedPref(this@ActivityAddSparePart1)

        val workshopId = mySharedPref.getWorkShopDocId()
        val partName = binding.partName.text.toString().trim()
        val partDescription = binding.partDescription.text.toString().trim()
        val partPrice = binding.partPrice.text.toString().toDoubleOrNull()
        val partQuantity = binding.partQuantity.text.toString().toIntOrNull()
        val partBrand = binding.partBrand.text.toString().trim()
        val partType = binding.partType.text.toString().trim()

        if (workshopId.isEmpty() || partName.isEmpty() || partDescription.isEmpty() || partPrice == null || partQuantity == null || partBrand.isEmpty() || partType.isEmpty() || imageUri == null) {
            Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show()
            return
        }

        val partId = UUID.randomUUID().toString()
        val storageReference = storage.reference.child("spare_parts_images/$partId")

        storageReference.putFile(imageUri!!)
            .addOnSuccessListener {
                storageReference.downloadUrl.addOnSuccessListener { uri ->
                    val partImage = uri.toString()

                    val sparePart = ModelSparePart(
                        workshopId = workshopId,
                        partId = partId,
                        partName = partName,
                        partDescription = partDescription,
                        partPrice = partPrice,
                        partAvailableQuantity = partQuantity,
                        partImage = partImage,
                        partBrand = partBrand,
                        partType = partType
                    )

                    firestore.collection("SpareParts").document(partId).set(sparePart)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Spare part added successfully", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Failed to add spare part: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to upload image: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
    fun showAnimation() {
        dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_anim_lodaing)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }

    fun closeAnimation() {
        dialog.dismiss()
    }

}