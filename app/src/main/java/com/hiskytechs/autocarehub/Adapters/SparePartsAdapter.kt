package com.hiskytechs.autocarehub.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hiskytechs.autocarehub.Models.ModelSparePart
import com.hiskytechs.autocarehub.R

class SparePartsAdapter(private val spareParts: List<ModelSparePart>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<SparePartsAdapter.SparePartViewHolder>() {

    interface OnItemClickListener {
        fun onItemDrag(view: View, sparePart: ModelSparePart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SparePartViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_spare_part, parent, false)
        return SparePartViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SparePartViewHolder, position: Int) {
        val sparePart = spareParts[position]
        Glide.with(holder.itemView.context).load(sparePart.partImage).into(holder.partImageView)

        holder.itemView.setOnLongClickListener {
            listener.onItemDrag(it, sparePart)
            true
        }
    }

    override fun getItemCount() = spareParts.size

    class SparePartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val partImageView: ImageView = itemView.findViewById(R.id.partImageView)
    }
}
