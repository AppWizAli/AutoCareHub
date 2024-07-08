package com.hiskytechs.autocarehub.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.hiskytechs.autocarehub.Models.ModelOffers
import com.hiskytechs.autocarehub.R

class OfferViewPagerAdapter(private val context: Context, private val list: List<ModelOffers>) : PagerAdapter() {

    override fun getCount(): Int = list.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.offer_view_pager_item, container, false)
        val imageView = view.findViewById<ImageView>(R.id.imageViewOffer)
        val textViewTitle = view.findViewById<TextView>(R.id.textViewOfferTitle)
        val textViewDescription = view.findViewById<TextView>(R.id.textViewOfferDescription)
        val textViewValidUntil = view.findViewById<TextView>(R.id.textViewOfferValidUntil)

        val offer = list[position]

        textViewTitle.text = offer.title
        textViewDescription.text = offer.description
        textViewValidUntil.text = "Valid until: ${offer.validUntil}"

        Glide.with(context)
            .load(offer.imageUrl)
            .placeholder(R.drawable.logo)
            .into(imageView)

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}
