package com.hiskytechs.autocarehub.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.models.ModelNews

class AdapterNews(private val context: Context, private val newsList: List<ModelNews>) : RecyclerView.Adapter<AdapterNews.NewsViewHolder>() {

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val newsTitle: TextView = itemView.findViewById(R.id.news_title)
        val newsDescription: TextView = itemView.findViewById(R.id.news_description)
        val newsImage: ImageView = itemView.findViewById(R.id.news_image)

        fun bind(news: ModelNews) {
            newsTitle.text = news.title
            newsDescription.text = news.description
            Glide.with(context).load(news.imageUrl).placeholder(R.drawable.top1).into(newsImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.newslist, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int = newsList.size
}
