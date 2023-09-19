package com.example.instagramclone.mainfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.instagramclone.R
import com.example.instagramclone.ViewPagerAdapter
import com.example.instagramclone.databinding.FragmentProfileBinding
import com.example.instagramclone.model.UserModel
import com.example.instagramclone.utils.User_Node
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {


    private val binding by lazy {
        FragmentProfileBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: ProfileFragmentViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(ProfileFragmentViewModel::class.java)


        val viewPager = binding.viewPager
        viewPager.adapter = ViewPagerAdapter(requireFragmentManager())

        val tabLayout = binding.tabLayout
        tabLayout.setupWithViewPager(viewPager)

        binding.editProfileBtn.setOnClickListener {

            findNavController().navigate(R.id.action_profileFragment2_to_editProfileFragment)

        }
//        Toast.makeText(context, viewModel.newProfilePic, Toast.LENGTH_SHORT).show()






        return binding.root
    }

    override fun onStart() {

        Firebase.firestore.collection(User_Node).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {

            var user : UserModel = it.toObject<UserModel>()!!
            binding.profileBio.text = user.Bio
            binding.postsCountText.text = user.posts.toString()
            binding.realsCountText.text = user.reals.toString()
            binding.followingCountText.text = user.following.toString()
            Glide.with(this).load(user.image).into(binding.profileImage)

        }

        super.onStart()
    }

}