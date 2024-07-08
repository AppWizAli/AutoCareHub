package com.hiskytechs.autocarehub.Adapters.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hiskytechs.autocarehub.Models.ModelWorkshop
import com.hiskytechs.autocarehub.R

class WorkshopAdapter(private val context: Context, private val workshops: List<ModelWorkshop>, private val onItemClick: (ModelWorkshop) -> Unit) :
    RecyclerView.Adapter<WorkshopAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_workshop, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val workshop = workshops[position]
        holder.bind(workshop)
        holder.itemView.setOnClickListener {
            onItemClick(workshop)
        }
    }

    override fun getItemCount(): Int {
        return workshops.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.workshopNameTextView)
        private val addressTextView: TextView = itemView.findViewById(R.id.workshopAddressTextView)

        fun bind(workshop: ModelWorkshop) {
            nameTextView.text = workshop.workshopName
            addressTextView.text = workshop.workshopAddress
        }
    }
}
