package com.hiskytechs.autocarehub.Ui

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.hiskytechs.autocarehub.Adapters.PendingRequestsAdapter
import com.hiskytechs.autocarehub.Models.ModelRequest
import com.hiskytechs.autocarehub.Models.MySharedPref
import com.hiskytechs.autocarehub.R

class ActivityWorkshopApproveReq : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PendingRequestsAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var dialog: Dialog
    private lateinit var mySharedPref: MySharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workshop_approve_req)

        mySharedPref = MySharedPref(this)

        // Initialize Firestore
        db = FirebaseFirestore.getInstance()

        // Setup RecyclerView
        recyclerView = findViewById(R.id.rv11)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PendingRequestsAdapter(this)
        recyclerView.adapter = adapter

        showAnimation()
        // Fetch approved requests
        fetchApprovedRequests()
    }

    private fun fetchApprovedRequests() {
        val workshopDocId = mySharedPref.getWorkShopDocId()
        db.collection("Requests")
            .whereEqualTo("requestType", "Approved")

            .get()
            .addOnSuccessListener { querySnapshot ->
                closeAnimation()
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
                closeAnimation()
                Toast.makeText(this, "Failed to fetch approved requests: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun showAnimation() {
        dialog = Dialog(this).apply {
            setContentView(R.layout.dialog_anim_lodaing)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
            show()
        }
    }

    private fun closeAnimation() {
        dialog.dismiss()
    }
}
