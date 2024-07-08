package com.hiskytechs.autocarehub.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.hiskytechs.autocarehub.Models.ModelRequest
import com.hiskytechs.autocarehub.R

class PendingRequestsAdapter(
    private val context: Context
) : RecyclerView.Adapter<PendingRequestsAdapter.PendingRequestViewHolder>() {

    private val db = FirebaseFirestore.getInstance()
    private var requests: MutableList<ModelRequest> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingRequestViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pending_request, parent, false)
        return PendingRequestViewHolder(view)
    }

    override fun onBindViewHolder(holder: PendingRequestViewHolder, position: Int) {
        val request = requests[position]
        holder.bind(request)
    }

    override fun getItemCount(): Int = requests.size

    fun updateData(newRequests: List<ModelRequest>) {
        requests.clear()
        requests.addAll(newRequests)
        notifyDataSetChanged()
    }

    inner class PendingRequestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvWorkshopName: TextView = itemView.findViewById(R.id.tvWorkshopName)
        private val tvWorkshopAddress: TextView = itemView.findViewById(R.id.tvWorkshopAddress)
        private val tvUserIssue: TextView = itemView.findViewById(R.id.tvUserIssue)
        private val btnApproveRequest: Button = itemView.findViewById(R.id.btnApproveRequest)

        fun bind(request: ModelRequest) {
            tvWorkshopName.text = request.workshopName
            tvWorkshopAddress.text = request.workshopAddress
            tvUserIssue.text = request.userIssue

            // Set initial text of the button based on requestType
            if (request.requestType == "Approved") {
                btnApproveRequest.text = "Approved"
                btnApproveRequest.setTextColor(ContextCompat.getColor(context, android.R.color.white)) // Set text color to white

                btnApproveRequest.isEnabled = false // Disable the button if already approved
            } else {
                btnApproveRequest.text = "Approve Request"
                btnApproveRequest.isEnabled = true
            }

            // Handle button click to update request type to "Approved"
            btnApproveRequest.setOnClickListener {
                // Update requestType to "Approved"
                request.requestType = "Approved"
                // Update Firestore document with the new requestType
                updateRequestInFirestore(request)
            }
        }

        private fun updateRequestInFirestore(request: ModelRequest) {
            db.collection("Requests")
                .document(request.requestDocId) // Assuming you have documentId field in ModelRequest
                .update("requestType", "Approved")
                .addOnSuccessListener {
                    Toast.makeText(context, "Request Approved", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    // Handle failure, log the error or show an error message
                }
        }
    }
}
