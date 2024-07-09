package com.hiskytechs.autocarehub.Fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.carrepairapp.model.ModelUser
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.hiskytechs.autocarehub.ActivityModifyCar
import com.hiskytechs.autocarehub.Adapters.AdapterSpareParts
import com.hiskytechs.autocarehub.Models.ModelOffers
import com.hiskytechs.autocarehub.Models.ModelSparePart
import com.hiskytechs.autocarehub.Models.MySharedPref
import com.hiskytechs.autocarehub.R
import com.hiskytechs.autocarehub.Ui.ActivityEmergencyRequest
import com.hiskytechs.autocarehub.Ui.ActivityMapView1
import com.hiskytechs.autocarehub.adapters.AdapterNews
import com.hiskytechs.autocarehub.adapters.ImageViewPagerAdapter
import com.hiskytechs.autocarehub.adapters.OfferViewPagerAdapter
import com.hiskytechs.autocarehub.databinding.FragmentHomeBinding
import com.hiskytechs.autocarehub.models.ModelNews

class FragmentHome : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentHomeBinding
    private var mGoogleMap: GoogleMap? = null
    var db = FirebaseFirestore.getInstance()
    private lateinit var mySharedPref: MySharedPref
    private lateinit var viewPager: ViewPager
    private lateinit var adapter: ImageViewPagerAdapter
    private lateinit var offersViewPager: ViewPager
    private lateinit var offersAdapter: OfferViewPagerAdapter
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
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val rootView = binding.root
        viewPager = rootView.findViewById(R.id.viewPager)


        adapter = ImageViewPagerAdapter(requireContext(), imageList)
        viewPager.adapter = adapter

        activity?.window?.let { window ->
            window.statusBarColor = resources.getColor(R.color.primary2, null)
            val controller = ViewCompat.getWindowInsetsController(window.decorView)
            controller?.isAppearanceLightStatusBars = false // White text
        }

        mySharedPref = MySharedPref(requireActivity())
db.collection("User")
    .document(mySharedPref.getUserDocId()).get()
    .addOnSuccessListener {
        docu->
        binding.tvUsername.text=docu.toObject(ModelUser::class.java)?.userName
    }
        initMap()

        binding.btnRequest.setOnClickListener {
            startActivity(Intent(requireActivity(), ActivityEmergencyRequest::class.java))
        }
        binding.btnModifycar.setOnClickListener {
            startActivity(Intent(requireActivity(), ActivityModifyCar::class.java))
        }
        binding.btnOpenMap.setOnClickListener {
            startActivity(Intent(requireActivity(), ActivityMapView1::class.java))
        }

        startAutoScroll()
        setupRecyclerView()
        fetchSpareParts()
        //fetchOffersFromFirestore()
        return rootView
    }

    private fun startAutoScroll() {
        handler.postDelayed(runnable, 3000)
    }

    private fun setupRecyclerView() {
        binding.newsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        fetchNewsFromFirestore()
    }

    private fun setupSparepartRecyclerView(sparePartsList: List<ModelSparePart>) {
        binding.recentlyAddedRecyclerView.layoutManager =  LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val adapter = AdapterSpareParts(requireContext(), sparePartsList)
        binding.recentlyAddedRecyclerView.adapter = adapter
    }

    private fun initMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun fetchSpareParts() {
        db.collection("SpareParts")
            .get()
            .addOnSuccessListener { documents ->
                val sparePartsList = mutableListOf<ModelSparePart>()
                for (document in documents) {
                    val sparePart = document.toObject(ModelSparePart::class.java)
                    sparePartsList.add(sparePart)
                }
                setupSparepartRecyclerView(sparePartsList)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(requireContext(), "Failed to fetch spare parts: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun fetchNewsFromFirestore() {
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("News")
            .get()
            .addOnSuccessListener { documents ->
                val newsList = mutableListOf<ModelNews>()
                for (document in documents) {
                    val news = document.toObject(ModelNews::class.java)
                    newsList.add(news)
                }
                newsAdapter = AdapterNews(requireContext(), newsList)
                binding.newsRecyclerView.adapter = newsAdapter
            }
            .addOnFailureListener { exception ->
                // Handle failure
            }
    }

    private fun fetchOffersFromFirestore() {
        db.collection("Offers")
            .get()
            .addOnSuccessListener { documents ->
                val offersList = ArrayList<ModelOffers>()
                for (document in documents) {
                    val offer = document.toObject(ModelOffers::class.java)
                    offersList.add(offer)
                }
                offersAdapter = OfferViewPagerAdapter(requireContext(), offersList)
                offersViewPager.adapter = offersAdapter
                startAutoScroll()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(requireContext(), "Failed to fetch offers: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(runnable) // Stop auto-scrolling when view is destroyed
    }

    override fun onMapReady(p0: GoogleMap) {
        mGoogleMap = p0
    }
}
