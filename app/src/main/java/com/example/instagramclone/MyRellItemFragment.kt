package com.example.instagramclone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.instagramclone.adapter.UserVideoRealsAdapter
import com.example.instagramclone.databinding.FragmentMyRellItemBinding
import com.example.instagramclone.model.Reals
import com.example.instagramclone.utils.Reel_User
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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        realsList = ArrayList<Reals>()
        adapter = UserVideoRealsAdapter(realsList, requireContext())
        binding.rvForRell.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvForRell.adapter = adapter
        binding.rvForRell.setHasFixedSize(false)


        setUPRealsForUserPost()
        // Inflate the layout for this mainfragment
        return binding.root
    }

    private fun setUPRealsForUserPost() {

        Firebase.firestore.collection(FirebaseAuth.getInstance().currentUser!!.uid + Reel_User).get().addOnSuccessListener {

            val templist = ArrayList<Reals>()

            for (i in it.documents) {
                val user = i.toObject<Reals>()!!
                templist.add(user)
            }

            realsList.addAll(templist)
            adapter.notifyDataSetChanged()

        }
    }

}