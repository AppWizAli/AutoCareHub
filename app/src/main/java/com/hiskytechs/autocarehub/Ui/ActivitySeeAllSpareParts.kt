package com.hiskytechs.autocarehub.Ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.hiskytechs.autocarehub.Adapters.AdapterSeeAllSpareParts
import com.hiskytechs.autocarehub.Models.ModelSparePart
import com.hiskytechs.autocarehub.databinding.ActivitySeeAllSparePartsBinding

class ActivitySeeAllSpareParts : AppCompatActivity() {

    private lateinit var binding: ActivitySeeAllSparePartsBinding
    private lateinit var adapter: AdapterSeeAllSpareParts
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeeAllSparePartsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = GridLayoutManager(this,2)
        fetchSparePartsFromFirestore()
    }

    private fun fetchSparePartsFromFirestore() {
        db.collection("SpareParts")
            .get()
            .addOnSuccessListener { documents ->
                val sparePartsList = ArrayList<ModelSparePart>()
                for (document in documents) {
                    val sparePart = document.toObject(ModelSparePart::class.java)
                    sparePartsList.add(sparePart)
                }
                adapter = AdapterSeeAllSpareParts(sparePartsList)
                binding.recyclerView.adapter = adapter
            }
            .addOnFailureListener { exception ->
                // Handle error
            }
    }
}
