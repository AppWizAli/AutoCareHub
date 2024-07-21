package com.hiskytechs.autocarehub.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hiskytechs.autocarehub.Models.ModelSparePart
import com.hiskytechs.autocarehub.R

class PartAdapter(private val parts: List<ModelSparePart>,private val onItemClick: (ModelSparePart) -> Unit ) : RecyclerView.Adapter<PartAdapter.PartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_part, parent, false)
        return PartViewHolder(view)
    }

    override fun onBindViewHolder(holder: PartViewHolder, position: Int) {
        val part = parts[position]
        holder.partNameTextView.text = part.partName
        holder.partPriceTextView.text = "$${part.partPrice}"

        // Load image using Glide
        Glide.with(holder.itemView.context)
            .load(part.partImage)
            .into(holder.partImageView)


        holder.itemView.setOnClickListener {
            onItemClick(part)
        }
    }

    override fun getItemCount() = parts.size

    class PartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val partImageView: ImageView = itemView.findViewById(R.id.partImageView)
        val partNameTextView: TextView = itemView.findViewById(R.id.partNameTextView)
        val partPriceTextView: TextView = itemView.findViewById(R.id.partPriceTextView)
    }
}
