package com.hiskytechs.autocarehub.Ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.hiskytechs.autocarehub.Adapters.PendingRequestsAdapter
import com.hiskytechs.autocarehub.Models.ModelRequest
import com.hiskytechs.autocarehub.R

class ActivityWorkshopApproveReq : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PendingRequestsAdapter
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workshop_approve_req)
        enableEdgeToEdge()

        // Initialize Firestore
        db = FirebaseFirestore.getInstance()

        // Setup RecyclerView
        recyclerView = findViewById(R.id.rv11)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PendingRequestsAdapter(this)
        recyclerView.adapter = adapter

        // Fetch approved requests
        fetchApprovedRequests()
    }

    private fun fetchApprovedRequests() {
        db.collection("Requests")
            .whereEqualTo("requestType", "Approved") // Assuming you have a field 'requestType'
            .get()
            .addOnSuccessListener { querySnapshot ->
                val requests = mutableListOf<ModelRequest>()
                for (document in querySnapshot.documents) {
                    val request = document.toObject(ModelRequest::class.java)
                    request?.let {
                        it.requestDocId = document.id // Set the document ID
                        requests.add(it)
                    }
                }
                adapter.updateData(requests)
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to fetch approved requests: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
