package com.example.instagramclone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.instagramclone.adapter.UserVideoRealsAdapter
import com.example.instagramclone.databinding.FragmentMyRellItemBinding
import com.example.instagramclone.model.Reals
import com.example.instagramclone.utils.Reel_User
import com.example.instagramclone.viewmodel.PostAndRealsViewModel
import com.google.api.Distribution.BucketOptions.Linear
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class MyRellItemFragment : Fragment() {

    private val binding by lazy {
        FragmentMyRellItemBinding.inflate(layoutInflater)
    }
    lateinit var realsList : ArrayList<Reals>
    lateinit var adapter: UserVideoRealsAdapter
    private lateinit var viewModel: PostAndRealsViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this)[PostAndRealsViewModel::class.java]

        setUPRealsForUserPost()

        return binding.root
    }

    private fun setUPRealsForUserPost() {

        realsList = ArrayList<Reals>()
        adapter = UserVideoRealsAdapter(realsList, requireContext())
        binding.rvForRell.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvForRell.adapter = adapter
        binding.rvForRell.setHasFixedSize(false)

        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        viewModel.fetchUserRealsData(userId)


        viewModel.userRealsList.observe(viewLifecycleOwner, Observer { reals ->
            realsList.clear()
            realsList.addAll(reals)
            adapter.notifyDataSetChanged()
        })
    }

}