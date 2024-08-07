package com.hiskytechs.autocarehub

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.firebase.firestore.FirebaseFirestore
import com.hiskytechs.autocarehub.Adapters.PartAdapter
import com.hiskytechs.autocarehub.Models.ModelSparePart
import com.hiskytechs.autocarehub.databinding.ActivityModifyCarBinding
import com.hiskytechs.autocarehub.helper.DragResizeView
import java.io.IOException

class ActivityModifyCar : AppCompatActivity() {

    private lateinit var carImageView: ImageView
    private lateinit var dragResizeView: DragResizeView
    private lateinit var uploadButton: Button
    private lateinit var partsLayout: LinearLayout
    private val PICK_IMAGE_REQUEST = 1

    private val db = FirebaseFirestore.getInstance()

    private lateinit var binding: ActivityModifyCarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModifyCarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        carImageView = findViewById(R.id.carImageView)
        dragResizeView = findViewById(R.id.dragResizeView)
        uploadButton = findViewById(R.id.uploadButton)
        partsLayout = findViewById(R.id.partsLayout)

        uploadButton.setOnClickListener {
            openImagePicker()
        }

        fetchSpareParts()
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val uri: Uri? = data.data
            uri?.let {
                try {
                    val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, it)
                    carImageView.setImageBitmap(bitmap)
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun fetchSpareParts() {
        db.collection("SpareParts")
            .get()
            .addOnSuccessListener { documents ->
                val sparePartsList = mutableListOf<ModelSparePart>()
                for (document in documents) {
                    val sparePart = document.toObject(ModelSparePart::class.java)
                    sparePartsList.add(sparePart)
                }
                setupSparepartRecyclerView(sparePartsList)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    this,
                    "Failed to fetch spare parts: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun setupSparepartRecyclerView(sparePartsList: List<ModelSparePart>) {
        binding.recentlyAddedRecyclerView.layoutManager = GridLayoutManager(this, 2)
        val adapter = PartAdapter(sparePartsList) {
            setUpPartsDraggable(it)
        }
        binding.recentlyAddedRecyclerView.adapter = adapter
    }

    private fun setUpPartsDraggable(part: ModelSparePart) {
        Glide.with(this)
            .load(part.partImage)
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    dragResizeView.addPart(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    // Handle if necessary
                }
            })
    }
}
