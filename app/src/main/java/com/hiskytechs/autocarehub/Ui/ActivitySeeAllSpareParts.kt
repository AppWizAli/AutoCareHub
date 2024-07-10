package com.hiskytechs.autocarehub.Ui

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.hiskytechs.autocarehub.Adapters.AdapterSeeAllSpareParts
import com.hiskytechs.autocarehub.Models.ModelRequest
import com.hiskytechs.autocarehub.Models.ModelSparePart
import com.hiskytechs.autocarehub.Models.MySharedPref
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivitySeeAllSparePartsBinding

class ActivitySeeAllSpareParts : AppCompatActivity(), AdapterSeeAllSpareParts.onItemclicklistener {

    private lateinit var binding: ActivitySeeAllSparePartsBinding
    private lateinit var adapter: AdapterSeeAllSpareParts
    private val db = FirebaseFirestore.getInstance()
    private lateinit var dialog: Dialog
    private val PICK_IMAGE_REQUEST = 1
    private var imageUri: Uri? = null
    private lateinit var mySharedPref: MySharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeeAllSparePartsBinding.inflate(layoutInflater)
        setContentView(binding.root)
mySharedPref=MySharedPref(this)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        fetchSparePartsFromFirestore()



    }

    private fun fetchSparePartsFromFirestore() {
        showAnimation()

        db.collection("SpareParts")
            .whereEqualTo("workshopId",mySharedPref.getWorkShopDocId())
            .get()
            .addOnSuccessListener { documents ->
closeAnimation()
                val sparePartsList = ArrayList<ModelSparePart>()
                for (document in documents) {
                    val sparePart = document.toObject(ModelSparePart::class.java)
                    sparePartsList.add(sparePart)
                }
                adapter = AdapterSeeAllSpareParts(sparePartsList, this)
                binding.recyclerView.adapter = adapter
            }
            .addOnFailureListener { exception ->
                closeAnimation()
                // Handle error
            }
    }

    override fun ondeleteclick(spare_model: ModelSparePart) {
       showAnimation()
        db.collection("SpareParts").document(spare_model.partId).delete()
            .addOnSuccessListener {
                closeAnimation()
                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
                fetchSparePartsFromFirestore()
            }
            .addOnFailureListener {
                closeAnimation()
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
    }

    override fun oneditclick(spare_model: ModelSparePart) {
        val dialog = Dialog(this@ActivitySeeAllSpareParts)
        dialog.setContentView(R.layout.editsparepart_dialog)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.setCancelable(false)

        val image = dialog.findViewById<ImageView>(R.id.part_image_view)
        val description = dialog.findViewById<EditText>(R.id.spart_description)
        val name = dialog.findViewById<EditText>(R.id.spart_name)
        val price = dialog.findViewById<EditText>(R.id.spart_price)
        val quantity = dialog.findViewById<EditText>(R.id.spart_quantity)
        val brand = dialog.findViewById<EditText>(R.id.spart_brand)
        val type = dialog.findViewById<EditText>(R.id.spart_type)
        val edit = dialog.findViewById<Button>(R.id.add_button)
        val cancel = dialog.findViewById<ImageView>(R.id.back_arrow)

        name.setText(spare_model.partName)
        description.setText(spare_model.partDescription)
        price.setText(spare_model.partPrice.toString())
        quantity.setText(spare_model.partAvailableQuantity.toString())
        brand.setText(spare_model.partBrand)
        type.setText(spare_model.partType)

        Glide.with(this).load(spare_model.partImage).into(image)

        image.setOnClickListener {
            openFileChooser()
        }

        cancel.setOnClickListener {
            dialog.dismiss()
        }

        edit.setOnClickListener {
            showAnimation()
            spare_model.partName = name.text.toString()
            spare_model.partDescription = description.text.toString()
            spare_model.partPrice = price.text.toString().toDouble()
            spare_model.partAvailableQuantity = quantity.text.toString().toInt()
            spare_model.partBrand = brand.text.toString()
            spare_model.partType = type.text.toString()

            if (imageUri != null) {
                uploadImageToFirebase(imageUri, spare_model, dialog)
            } else {
                db.collection("SpareParts").document(spare_model.partId).set(spare_model)
                    .addOnSuccessListener {
                        closeAnimation()
                        Toast.makeText(this, "Update successful", Toast.LENGTH_SHORT).show()

                        dialog.dismiss()
                        fetchSparePartsFromFirestore()
                    }
                    .addOnFailureListener {
                        closeAnimation()
                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        dialog.show()

    }

    private fun updateSparePartImage(imageUrl: String, spare_model: ModelSparePart, dialog: Dialog) {
        spare_model.partImage = imageUrl

        db.collection("SpareParts").document(spare_model.partId).set(spare_model)
            .addOnSuccessListener {
                closeAnimation()
                Toast.makeText(this, "Update successful", Toast.LENGTH_SHORT).show()
                fetchSparePartsFromFirestore()
                dialog.dismiss()
            }
            .addOnFailureListener {
                closeAnimation()
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
    }

    private fun uploadImageToFirebase(imageUri: Uri?, spare_model: ModelSparePart, dialog: Dialog) {
        if (imageUri != null) {
            val storageReference = FirebaseStorage.getInstance().getReference("sparepart_images/${System.currentTimeMillis()}.jpg")
            storageReference.putFile(imageUri)
                .addOnSuccessListener {
                    storageReference.downloadUrl.addOnSuccessListener { uri ->
                        val imageUrl = uri.toString()
                        updateSparePartImage(imageUrl, spare_model, dialog)
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun openFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
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
