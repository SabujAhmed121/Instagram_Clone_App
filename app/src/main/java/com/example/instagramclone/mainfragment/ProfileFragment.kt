package com.example.instagramclone.mainfragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.instagramclone.R
import com.example.instagramclone.ViewPagerAdapter
import com.example.instagramclone.activity.EditProfileActivity
import com.example.instagramclone.databinding.FragmentProfileBinding
import com.example.instagramclone.viewmodel.ProfileAndEditProfileViewModel
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {


    private val binding by lazy {
        FragmentProfileBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: ProfileAndEditProfileViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(ProfileAndEditProfileViewModel::class.java)


        val viewPager = binding.viewPager
        viewPager.adapter = ViewPagerAdapter(requireFragmentManager())

        val tabLayout = binding.tabLayout
        tabLayout.setupWithViewPager(viewPager)

        binding.editProfileBtn.setOnClickListener {

          val intent = Intent(requireContext(), EditProfileActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }


    override fun onStart() {

        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        viewModel.fetchUserData(userId)



        viewModel.userInfo.observe(viewLifecycleOwner, Observer {user->
            binding.profileBio.text = user.Bio
            binding.postsCountText.text = user.posts.toString()
            binding.realsCountText.text = user.reals.toString()
            binding.followingCountText.text = user.following.toString()
            Glide.with(this).load(user.image).into(binding.profileImage)
        })
        super.onStart()
    }

}