package com.hiskytechs.autocarehub.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.hiskytechs.autocarehub.Models.ModelSparePart
import com.hiskytechs.autocarehub.R

class AdapterSpareParts(
    var listeneer:ItemClickListener,
    private val context: Context,
    private val sparePartsList: List<ModelSparePart>
) : RecyclerView.Adapter<AdapterSpareParts.ViewHolder>() {
interface ItemClickListener
{
    fun OnBuyNowButtonClick(modelSparePart: ModelSparePart)
}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_spare_buy, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sparePart = sparePartsList[position]
        holder.bind(sparePart)
    }

    override fun getItemCount(): Int {
        return sparePartsList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewProductName: TextView = itemView.findViewById(R.id.textViewProductName)
        private val textViewPrice: TextView = itemView.findViewById(R.id.textViewPrice)
        private val image: ImageView = itemView.findViewById(R.id.imageParts)
        private val buttonBuyNow: MaterialButton = itemView.findViewById(R.id.buttonBuyNow)

        fun bind(sparePart: ModelSparePart) {
            textViewProductName.text = sparePart.partName
            textViewPrice.text = sparePart.partPrice.toString()
            buttonBuyNow.setOnClickListener()
            {
                listeneer.OnBuyNowButtonClick(sparePart)
            }
            Glide.with(context).load(sparePart.partImage).placeholder(R.drawable.logo).into(image)
        }
    }
}

