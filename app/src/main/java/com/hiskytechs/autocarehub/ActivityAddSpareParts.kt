package com.hiskytechs.autocarehub

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.hiskytechs.autocarehub.Models.ModelSparePart
import com.hiskytechs.autocarehub.Models.MySharedPref
import com.hiskytechs.autocarehub.databinding.ActivityAddSparePartsBinding

class ActivityAddSpareParts : AppCompatActivity() {
    private lateinit var binding: ActivityAddSparePartsBinding
    private lateinit var mySharedPref: MySharedPref
    private var db = Firebase.firestore
    private lateinit var dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSparePartsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mySharedPref = MySharedPref(this@ActivityAddSpareParts)
        binding.apply {
            backArrow.setOnClickListener {
                finish()
            }

            add.setOnClickListener {
                showAnimation()
                val name = prtname.text.toString().trim()
                val type = prttype.text.toString().trim()
                val brand = prtbrand.text.toString().trim()
                val prize = prtprize.text.trim()
                val available = prtavailability.text.toString().trim()
                val Des = des.text.toString().trim()

                if (name.isEmpty()) {
                    prtname.error = "Part Name is required"
                    closeAnimation()
                } else if (type.isEmpty()) {
                    prttype.error = "Part Type is required"
                    closeAnimation()
                } else if (brand.isEmpty()) {
                    prtbrand.error = "Part Brand is required"
                    closeAnimation()
                } else if (prize.isEmpty()) {
                    prtprize.error = "Part Prize is required"
                    closeAnimation()
                } else if (available.isEmpty()) {
                    prtavailability.error = "Part Availability is required"
                    closeAnimation()
                } else if (Des.isEmpty()) {
                    des.error = "Part Description is required"
                    closeAnimation()
                } else {
                    val modelParts = ModelSparePart(
                        partName = name,
                        partDescription = Des,
                        partType = type,
                        partBrand = brand,
                        partAvailableQuantity = available.toInt(),
                        partPrice = prize.toString().toDouble()
                    )
                    db.collection("Spareparts").add(modelParts)
                        .addOnSuccessListener { documentRef ->
                            modelParts.workshopId = mySharedPref.getWorkShopDocId()
                            modelParts.partId = documentRef.id
                            db.collection("Spareparts").document(documentRef.id)
                                .set(modelParts)
                                .addOnSuccessListener {
                                    closeAnimation()
                                    Toast.makeText(
                                        this@ActivityAddSpareParts,
                                        "Added successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(
                                        this@ActivityAddSpareParts,
                                        "Not Added",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    closeAnimation()
                                }
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                this@ActivityAddSpareParts,
                                "Not Added",
                                Toast.LENGTH_SHORT
                            ).show()
                            closeAnimation()
                        }
                }
            }
        }
    }


    private fun showAnimation() {
        dialog = Dialog(this@ActivityAddSpareParts)
        dialog.setContentView(R.layout.dialog_anim_lodaing)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }

    private fun closeAnimation() {
        dialog.dismiss()
    }
}
