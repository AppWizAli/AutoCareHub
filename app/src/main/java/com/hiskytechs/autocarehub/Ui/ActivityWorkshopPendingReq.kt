package com.hiskytechs.autocarehub.Ui

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.hiskytechs.autocarehub.Adapters.PendingRequestsAdapter
import com.hiskytechs.autocarehub.Models.ModelRequest
import com.hiskytechs.autocarehub.Models.MySharedPref
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.databinding.ActivityWorkshopPendingReqBinding

class ActivityWorkshopPendingReq : AppCompatActivity() {

    private lateinit var binding: ActivityWorkshopPendingReqBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var dialog: Dialog
    private lateinit var mySharedPref: MySharedPref
    private lateinit var pendingRequestsAdapter: PendingRequestsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkshopPendingReqBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()
        mySharedPref = MySharedPref(this)
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
        showAnimation()
        val workshopDocId = mySharedPref.getWorkShopDocId()
        Log.d("Firestore", "Fetching pending requests for workshopDocId: $workshopDocId")

        db.collection("Requests")
            .whereEqualTo("requestType", "pending")
            .whereEqualTo("workshopDocId", workshopDocId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                closeAnimation()
                val pendingRequests = mutableListOf<ModelRequest>()
                for (document in querySnapshot.documents) {
                    val request = document.toObject(ModelRequest::class.java)
                    request?.let {
                        it.requestDocId = document.id // Set the document ID
                        pendingRequests.add(it)
                    }
                }

                Log.d("Firestore", "Pending requests fetched: $pendingRequests")

                if (pendingRequests.isEmpty()) {
                    binding.textView22.visibility = View.VISIBLE
                    Log.d("Firestore", "No pending requests found")
                } else {
                    binding.textView22.visibility = View.GONE
                    pendingRequestsAdapter.updateData(pendingRequests)
                }
            }
            .addOnFailureListener { e ->
                closeAnimation()
                Log.e("Firestore", "Error fetching pending requests", e)
            }
    }

    private fun showAnimation() {
        dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_anim_lodaing)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }

    private fun closeAnimation() {
        dialog.dismiss()
    }
}
