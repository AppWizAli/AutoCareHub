package com.hiskytechs.autocarehub.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.hiskytechs.autocarehub.Adapters.AdapterSpareParts
import com.hiskytechs.autocarehub.Models.ModelSparePart
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityIntro1Binding
import com.hiskytechs.autocarehub.databinding.ActivitySparePartBinding

class ActivitySparePart : AppCompatActivity() {
    var db=Firebase.firestore
    private lateinit var binding: ActivitySparePartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySparePartBinding.inflate(layoutInflater)
        setContentView(binding.root)


        db.collection("Spareparts").get()
            .addOnSuccessListener { querySnapshot->

                var listModelSpareParts=ArrayList<ModelSparePart>()

for(document in querySnapshot.documents)
{

    var modelSparePart=document.toObject(ModelSparePart::class.java)!!
    listModelSpareParts.add(modelSparePart)


}


                binding.rvSpare.layoutManager=GridLayoutManager(this@ActivitySparePart,2)
                binding.rvSpare.adapter=AdapterSpareParts(listModelSpareParts,this@ActivitySparePart)


            }



    }
}