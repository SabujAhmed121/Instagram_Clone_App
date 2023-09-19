package com.example.instagramclone.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramclone.adapter.FavouriteFragmentAdapter
import com.example.instagramclone.databinding.ActivityFavouriteBinding
import com.example.instagramclone.model.Post
import com.example.instagramclone.utils.Favourite
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class FavouriteActivity : AppCompatActivity() {


    private val binding by lazy {
        ActivityFavouriteBinding.inflate(layoutInflater)
    }
    lateinit var favouriteList : ArrayList<Post>
    lateinit var adapter: FavouriteFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        favouriteList = ArrayList()
        adapter = FavouriteFragmentAdapter(favouriteList, this)

        binding.swipeRefreshLayout.setOnRefreshListener {
            setUPFavouritePost()
        }

        binding.backImageIcon.setOnClickListener {
            finish()
        }
        setUPFavouritePost()




    }

    private fun setUPFavouritePost() {

        binding.mainPostRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val itemDecoration = DividerItemDecoration(this, (binding.mainPostRv.layoutManager as LinearLayoutManager).orientation)
        binding.mainPostRv.addItemDecoration(itemDecoration)
        binding.mainPostRv.setHasFixedSize(false)
        binding.mainPostRv.adapter = adapter

        Firebase.firestore.collection(FirebaseAuth.getInstance().currentUser!!.uid + Favourite).get().addOnSuccessListener {

            val templist = ArrayList<Post>()
            favouriteList.clear()

            for (i in it.documents) {
                val user = i.toObject<Post>()!!
                templist.add(user)
            }

            favouriteList.addAll(templist)
            favouriteList.reverse()

            binding.swipeRefreshLayout.isRefreshing = false
            adapter.notifyDataSetChanged()

        }
    }
}