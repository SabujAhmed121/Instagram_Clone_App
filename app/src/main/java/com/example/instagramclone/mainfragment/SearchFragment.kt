package com.example.instagramclone.mainfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramclone.adapter.MainSearchFragmentAdapter
import com.example.instagramclone.databinding.FragmentSearchBinding
import com.example.instagramclone.model.UserModel
import com.example.instagramclone.utils.Follow
import com.example.instagramclone.utils.User_Node
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        userList = ArrayList<UserModel>()
        adapter = MainSearchFragmentAdapter(userList, requireContext())
        binding.searchRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.searchRv.setHasFixedSize(false)
        binding.searchRv.adapter = adapter

        val editor = requireActivity().getSharedPreferences("MySearch", AppCompatActivity.MODE_PRIVATE)
        binding.searchBox.setText(editor.getString("searchData", null))

        setUpFollowUser()

        binding.searchIcon.setOnClickListener {

            val text = binding.searchBox.text.toString()

            Firebase.firestore.collection(User_Node).whereEqualTo("userName", text).get().addOnSuccessListener {
                val templist = ArrayList<UserModel>()
                userList.clear()


                for (i in it.documents) {

                    if (i.id.toString().equals(FirebaseAuth.getInstance().currentUser!!.uid.toString())){

                    }else{
                        val user = i.toObject<UserModel>()!!
                        templist.add(user)
                        val editor = requireActivity().getSharedPreferences("MySearch", AppCompatActivity.MODE_PRIVATE).edit()
                        editor.clear()
                        editor.putString("searchData", text.toString())
                        editor.apply()
                    }
                }
                userList.addAll(templist)
                userList.reverse()

                adapter.notifyDataSetChanged()

            }
        }

        // Inflate the layout for this mainfragment
        return binding.root
    }

    private fun setUpFollowUser() {

        Firebase.firestore.collection(User_Node).get().addOnSuccessListener {

            val templist = ArrayList<UserModel>()
            userList.clear()

            for (i in it.documents) {
                if (i.id.toString().equals(FirebaseAuth.getInstance().currentUser!!.uid.toString())){

                }else{
                    val user = i.toObject<UserModel>()!!
                    templist.add(user)
                }
            }


            userList.addAll(templist)
            userList.reverse()

            adapter.notifyDataSetChanged()

        }
    }

}