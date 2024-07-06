package com.hiskytechs.autocarehub.Adapters

import android.content.Context
import android.media.tv.TvView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hiskytechs.autocarehub.Models.ModelSparePart
import com.hiskytechs.autocarehub.R
import kotlin.contracts.contract

class AdapterSpareParts(var listModelSpareParts: ArrayList<ModelSparePart>,var context: Context) :
    RecyclerView.Adapter<AdapterSpareParts.MyViewHolder>() {

        inner  class MyViewHolder(itemview:View):RecyclerView.ViewHolder(itemview)
        {
var name=itemview.findViewById<TextView>(R.id.name)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterSpareParts.MyViewHolder
    {
var itemview=LayoutInflater.from(context).inflate(R.layout.spare_list,parent,false)
        return MyViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: AdapterSpareParts.MyViewHolder, position: Int) {
holder.name.text=listModelSpareParts[position].partName
    }

    override fun getItemCount(): Int {
return listModelSpareParts.size
    }
}