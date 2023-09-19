package com.example.instagramclone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.instagramclone.adapter.UserPostAdapter
import com.example.instagramclone.databinding.FragmentMyPostItemBinding
import com.example.instagramclone.model.Post
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class MyPostItemFragment : Fragment() {

    private val binding by lazy {
        FragmentMyPostItemBinding.inflate(layoutInflater)
    }
    lateinit var postList : ArrayList<Post>
    lateinit var adapter: UserPostAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        postList = ArrayList<Post>()
        adapter = UserPostAdapter(postList, requireContext())
        binding.postRv.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.postRv.adapter = adapter
        binding.postRv.setHasFixedSize(false)


        setUPImageForUserPost()


        // Inflate the layout for this mainfragment
        return binding.root
    }

    private fun setUPImageForUserPost() {

        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {

            val templist = ArrayList<Post>()

            for (i in it.documents) {
                val user = i.toObject<Post>()!!
                templist.add(user)
            }

            postList.addAll(templist)
            adapter.notifyDataSetChanged()

        }
    }


}