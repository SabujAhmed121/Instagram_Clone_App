package com.example.instagramclone.mainfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramclone.adapter.MainRealsFragmantAdapter
import com.example.instagramclone.databinding.FragmentRellBinding
import com.example.instagramclone.model.Reals
import com.example.instagramclone.utils.Reel_User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class RellFragment : Fragment() {

    private val binding by lazy {
        FragmentRellBinding.inflate(layoutInflater)
    }
    lateinit var realsList : ArrayList<Reals>
    lateinit var adapter: MainRealsFragmantAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        realsList = ArrayList<Reals>()
        adapter = MainRealsFragmantAdapter(realsList, requireContext())
        binding.realsRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val itemDecoration = DividerItemDecoration(context, (binding.realsRv.layoutManager as LinearLayoutManager).orientation)
        binding.realsRv.addItemDecoration(itemDecoration)
        binding.realsRv.setHasFixedSize(false)
        binding.realsRv.adapter = adapter

        setUPRealsForAll()
        // Inflate the layout for this mainfragment
        return binding.root
    }

    private fun setUPRealsForAll() {

        Firebase.firestore.collection(Reel_User).get().addOnSuccessListener {

            val templist = ArrayList<Reals>()
            realsList.clear()

            for (i in it.documents) {
                val user = i.toObject<Reals>()!!
                templist.add(user)
            }


            realsList.addAll(templist)
            realsList.reverse()

            adapter.notifyDataSetChanged()

        }
    }

}