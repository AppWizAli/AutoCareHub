package com.hiskytechs.autocarehub.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hiskytechs.autocarehub.Models.ModelSparePart
import com.hiskytechs.autocarehub.databinding.SpareListBinding

class AdapterSeeAllSpareParts(private val sparePartsList: ArrayList<ModelSparePart>,var listener:onItemclicklistener) :
    RecyclerView.Adapter<AdapterSeeAllSpareParts.SparePartViewHolder>() {
interface onItemclicklistener{
fun ondeleteclick(spare_model:ModelSparePart)
fun oneditclick(spare_model:ModelSparePart)

}

    inner class SparePartViewHolder(private val binding: SpareListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(sparePart: ModelSparePart) {
            binding.partNameTextView.text = sparePart.partName

            binding.partPriceTextView.text = "OMR ${sparePart.partPrice}"
            Glide.with(binding.partImageView.context)
                .load(sparePart.partImage)
                .into(binding.partImageView)

            binding.editButton.setOnClickListener {
                listener.oneditclick(sparePart)
            }

            binding.deleteButton.setOnClickListener {
                listener.ondeleteclick(sparePart)
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
