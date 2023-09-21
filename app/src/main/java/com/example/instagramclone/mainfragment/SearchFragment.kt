package com.example.instagramclone.mainfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramclone.adapter.MainSearchFragmentAdapter
import com.example.instagramclone.databinding.FragmentSearchBinding
import com.example.instagramclone.model.UserModel
import com.example.instagramclone.utils.Follow
import com.example.instagramclone.utils.User_Node
import com.example.instagramclone.viewmodel.HomeFragmentViewModel
import com.example.instagramclone.viewmodel.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class SearchFragment : Fragment() {

    private val binding by lazy {
        FragmentSearchBinding.inflate(layoutInflater)
    }
    lateinit var userList : ArrayList<UserModel>
    lateinit var adapter: MainSearchFragmentAdapter
    private lateinit var viewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]



        val editor = requireActivity().getSharedPreferences("MySearch", AppCompatActivity.MODE_PRIVATE)
        binding.searchBox.setText(editor.getString("searchData", null))

        setUpFollowUser()

        binding.searchIcon.setOnClickListener {

            setUpSerchWork()

        }
        return binding.root
    }

    private fun setUpFollowUser() {

        userList = ArrayList<UserModel>()
        adapter = MainSearchFragmentAdapter(userList, requireContext())
        binding.searchRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.searchRv.setHasFixedSize(false)
        binding.searchRv.adapter = adapter

        viewModel.userList.observe(viewLifecycleOwner, Observer { userListAll ->
            userList.clear()
            userList.addAll(userListAll)
            adapter.notifyDataSetChanged()
        })
        viewModel.fetchUserData()


    }

    private fun setUpSerchWork(){

        val text = binding.searchBox.text.toString()

        viewModel.fetchSearchUserData(text)


        viewModel.searchList.observe(viewLifecycleOwner, Observer { searchList ->
            userList.clear()
            userList.addAll(searchList)
            viewModel.saveSearchData(requireContext(), text)
            adapter.notifyDataSetChanged()
        })
    }

}