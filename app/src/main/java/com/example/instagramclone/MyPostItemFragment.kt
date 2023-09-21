package com.example.instagramclone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.instagramclone.adapter.UserPostAdapter
import com.example.instagramclone.databinding.FragmentMyPostItemBinding
import com.example.instagramclone.model.Post
import com.example.instagramclone.viewmodel.HomeFragmentViewModel
import com.example.instagramclone.viewmodel.PostAndRealsViewModel
import com.google.firebase.auth.FirebaseAuth
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
    private lateinit var viewModel: PostAndRealsViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this)[PostAndRealsViewModel::class.java]

        setUPImageForUserPost()

        return binding.root
    }

    private fun setUPImageForUserPost() {

        postList = ArrayList<Post>()
        adapter = UserPostAdapter(postList, requireContext())
        binding.postRv.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.postRv.adapter = adapter
        binding.postRv.setHasFixedSize(false)

        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        viewModel.fetchUserPostData(userId)


        viewModel.userPostList.observe(viewLifecycleOwner, Observer { posts ->
            postList.clear()
            postList.addAll(posts)
            adapter.notifyDataSetChanged()
        })
    }

}