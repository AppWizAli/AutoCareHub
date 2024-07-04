package com.hiskytechs.autocarehub.Fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.carrepairapp.model.ModelUser
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.hiskytechs.autocarehub.Models.MySharedPref
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.adapters.AdapterNews
import com.hiskytechs.autocarehub.adapters. ImageViewPagerAdapter
import com.hiskytechs.autocarehub.models.ModelNews

class FragmentHome : Fragment() {
    var db=Firebase.firestore
    private lateinit var mySharedPref: MySharedPref
    private lateinit var viewPager: ViewPager
    private lateinit var adapter: ImageViewPagerAdapter
    private lateinit var newsRecyclerView: RecyclerView
    private lateinit var newsAdapter: AdapterNews
    private val handler = Handler(Looper.getMainLooper())
    private val imageList = listOf(
        R.drawable.top4,
        R.drawable.top5,
        R.drawable.top6,
        R.drawable.top7,
        R.drawable.top8,
        R.drawable.top9,
        R.drawable.top10,
        R.drawable.top11
    )

    private val runnable = object : Runnable {
        override fun run() {
            val nextItem = (viewPager.currentItem + 1) % adapter.count
            viewPager.currentItem = nextItem
            handler.postDelayed(this, 3000) // Scroll every 3 seconds
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        viewPager = rootView.findViewById(R.id.viewPager)

        adapter =
            com.hiskytechs.autocarehub.adapters.ImageViewPagerAdapter(requireContext(), imageList)
        viewPager.adapter = adapter
        activity?.window?.let { window ->
            window.statusBarColor = resources.getColor(R.color.primary2, null)
            val controller = ViewCompat.getWindowInsetsController(window.decorView)
            controller?.isAppearanceLightStatusBars = false // White text
        }







        mySharedPref=MySharedPref(requireActivity())








        var userid=mySharedPref.getUserDocId()





        db.collection("User").document(userid).get()
            .addOnSuccessListener { document->

                var modelUser=document.toObject(ModelUser::class.java)!!

                Toast.makeText(requireActivity(), modelUser.userName, Toast.LENGTH_SHORT).show()
            }





        startAutoScroll()

        setupRecyclerView()

        return rootView
    }

    private fun startAutoScroll() {
        handler.postDelayed(runnable, 3000)
    }

    private fun setupRecyclerView() {
     //   newsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        fetchNewsFromFirestore()
    }

    private fun fetchNewsFromFirestore() {
        val firestore = Firebase.firestore
        firestore.collection("News")
            .get()
            .addOnSuccessListener { documents ->
                val newsList = mutableListOf<ModelNews>()
                for (document in documents) {
                    val news = document.toObject(ModelNews::class.java)
                    newsList.add(news)
                }
             //   newsAdapter = AdapterNews(requireContext(), newsList)
           //     newsRecyclerView.adapter = newsAdapter
            }
            .addOnFailureListener { exception ->
                // Handle error
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(runnable) // Stop auto-scrolling when view is destroyed
    }
}
