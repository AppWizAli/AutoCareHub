package com.hiskytechs.autocarehub.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carrepairapp.model.ModelUser
import com.google.firebase.firestore.FirebaseFirestore
import com.hiskytechs.autocarehub.Adapters.adapters.WorkshopAdapter
import com.hiskytechs.autocarehub.Models.ModelRequest
import com.hiskytechs.autocarehub.Models.ModelWorkshop
import com.hiskytechs.autocarehub.Models.MySharedPref
import com.hiskytechs.autocarehub.databinding.ActivitySparePartAcess1Binding
import com.hiskytechs.autocarehub.databinding.DialogRequestDescriptionBinding

class ActivityEmergencyRequest : AppCompatActivity() {

    private lateinit var binding: ActivitySparePartAcess1Binding
    private lateinit var db: FirebaseFirestore
    private lateinit var workshopAdapter: WorkshopAdapter
    private var selectedWorkshop: ModelWorkshop? = null
    private lateinit var modelUser: ModelUser
    private lateinit var mySharedPref: MySharedPref // Assuming you have a MySharedPref class for managing preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySparePartAcess1Binding.inflate(layoutInflater)
        setContentView(binding.root)
modelUser= ModelUser()
        db = FirebaseFirestore.getInstance()
        mySharedPref = MySharedPref(this) // Initialize MySharedPref with context

        setupRecyclerView()
        fetchUserData()
    }

    private fun setupRecyclerView() {
        db.collection("WorkshopRegistration")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val workshops = mutableListOf<ModelWorkshop>()
                for (document in querySnapshot.documents) {
                    val workshop = document.toObject(ModelWorkshop::class.java)
                    workshop?.let {
                        workshops.add(it)
                    }
                }
                workshopAdapter = WorkshopAdapter(this, workshops) { workshop ->
                    selectedWorkshop = workshop!!
                    showAddRequestDialog()
                }
                binding.rv.apply {
                    adapter = workshopAdapter
                    layoutManager = LinearLayoutManager(this@ActivityEmergencyRequest)
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to fetch workshops: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun fetchUserData() {
        db.collection("User")
            .document(mySharedPref.getUserDocId())
            .get()
            .addOnSuccessListener { document ->
          modelUser = document.toObject(ModelUser::class.java)!!

            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to fetch user data: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun showAddRequestDialog() {
        val dialogBinding = DialogRequestDescriptionBinding.inflate(layoutInflater)
        val dialog = androidx.appcompat.app.AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setPositiveButton("Add Request") { _, _ ->
                val userIssue = dialogBinding.issueEditText.text.toString().trim()
                if (userIssue.isNotEmpty() && selectedWorkshop != null) {
                    val modelRequest = ModelRequest()
                    modelRequest.requestType="pending"
                    modelRequest.userIssue=userIssue
                    modelRequest.userId=modelUser.userID
                    modelRequest.phoneNumber=modelUser.phoneNumber
                    modelRequest.userAddress=modelUser.address
                    modelRequest.workshopDocId= selectedWorkshop!!.workshopId


                    addRequestToFirestore(modelRequest)
                } else {
                    Toast.makeText(this, "Please describe your issue", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        dialog.show()
    }

    private fun addRequestToFirestore(modelRequest: ModelRequest) {
        db.collection("Requests")
            .add(modelRequest)
            .addOnSuccessListener { documentRef ->
                val requestId = documentRef.id
                modelRequest.requestDocId = requestId // Update requestDocId with Firestore document ID
                db.collection("Requests")
                    .document(requestId)
                    .update("requestDocId", requestId)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Request added successfully", Toast.LENGTH_SHORT).show()
                      finish()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Failed to update request: ${e.message}", Toast.LENGTH_SHORT).show()
                        // Handle failure, if needed
                    }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to add request: ${e.message}", Toast.LENGTH_SHORT).show()
                // Handle failure, if needed
            }
    }
}
