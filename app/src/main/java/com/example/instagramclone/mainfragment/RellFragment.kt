package com.example.instagramclone.mainfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramclone.adapter.MainRealsFragmantAdapter
import com.example.instagramclone.databinding.FragmentRellBinding
import com.example.instagramclone.model.Reals
import com.example.instagramclone.utils.Reel_User
import com.example.instagramclone.viewmodel.MainViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class RellFragment : Fragment() {

    private val binding by lazy {
        FragmentRellBinding.inflate(layoutInflater)
    }
    lateinit var realsList : ArrayList<Reals>
    lateinit var adapter: MainRealsFragmantAdapter
    private lateinit var viewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setUPRealsForAll()

        return binding.root
    }

    private fun setUPRealsForAll() {

        realsList = ArrayList<Reals>()
        adapter = MainRealsFragmantAdapter(realsList, requireContext())
        binding.realsRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val itemDecoration = DividerItemDecoration(context, (binding.realsRv.layoutManager as LinearLayoutManager).orientation)
        binding.realsRv.addItemDecoration(itemDecoration)
        binding.realsRv.setHasFixedSize(false)
        binding.realsRv.adapter = adapter

        viewModel.realsList.observe(viewLifecycleOwner, Observer { reals ->
            realsList.clear()
            realsList.addAll(reals)
            adapter.notifyDataSetChanged()
        })

        viewModel.fetchRealsData()
    }

}