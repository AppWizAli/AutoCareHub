package com.hiskytechs.autocarehub.Ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.hiskytechs.autocarehub.Adapters.PendingRequestsAdapter
import com.hiskytechs.autocarehub.Models.ModelRequest
import com.hiskytechs.autocarehub.databinding.ActivityWorkshopPendingReqBinding

class ActivityWorkshopPendingReq : AppCompatActivity() {

    private lateinit var binding: ActivityWorkshopPendingReqBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var pendingRequestsAdapter: PendingRequestsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkshopPendingReqBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()

        setupRecyclerView()
        fetchPendingRequests()
    }

    private fun setupRecyclerView() {
        pendingRequestsAdapter = PendingRequestsAdapter(this)
        binding.rv.apply {
            adapter = pendingRequestsAdapter
            layoutManager = LinearLayoutManager(this@ActivityWorkshopPendingReq)
        }
    }

    private fun fetchPendingRequests() {
        db.collection("Requests")
            .whereEqualTo("requestType", "pending") // Fetch only pending requests
            .get()
            .addOnSuccessListener { querySnapshot ->
                val pendingRequests = mutableListOf<ModelRequest>()
                for (document in querySnapshot.documents) {
                    val request = document.toObject(ModelRequest::class.java)
                    request?.let {
                        it.requestDocId = document.id // Set the document ID
                        pendingRequests.add(it)
                    }
                }
                pendingRequestsAdapter.updateData(pendingRequests)
            }
            .addOnFailureListener { e ->
                // Handle failure to fetch data
            }
    }
}
