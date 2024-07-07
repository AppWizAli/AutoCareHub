package com.hiskytechs.autocarehub.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hiskytechs.autocarehub.Models.ModelSparePart
import com.hiskytechs.autocarehub.databinding.SpareListBinding

class AdapterSeeAllSpareParts(private val sparePartsList: ArrayList<ModelSparePart>) :
    RecyclerView.Adapter<AdapterSeeAllSpareParts.SparePartViewHolder>() {

    inner class SparePartViewHolder(private val binding: SpareListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(sparePart: ModelSparePart) {
            binding.partNameTextView.text = sparePart.partName
            binding.partPriceTextView.text = "OMR ${sparePart.partPrice}"
            Glide.with(binding.partImageView.context)
                .load(sparePart.partImage)
                .into(binding.partImageView)

            binding.editButton.setOnClickListener {
                // Handle edit button click
            }

            binding.deleteButton.setOnClickListener {
                // Handle delete button click
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SparePartViewHolder {
        val binding = SpareListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SparePartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SparePartViewHolder, position: Int) {
        holder.bind(sparePartsList[position])
    }

    override fun getItemCount(): Int {
        return sparePartsList.size
    }
}
