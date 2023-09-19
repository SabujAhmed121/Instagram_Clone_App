package com.example.instagramclone.mainfragment

 import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
 import com.example.instagramclone.adapter.MainFollowingSetAdapter
 import com.example.instagramclone.adapter.MainPostFragmentAdapter
import com.example.instagramclone.databinding.FragmentHomeBinding
import com.example.instagramclone.model.Post
 import com.example.instagramclone.model.UserModel
 import com.example.instagramclone.utils.Follow
 import com.example.instagramclone.utils.Post_user
 import com.google.firebase.auth.FirebaseAuth
 import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {

    private val binding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }
    lateinit var postList : ArrayList<Post>
    lateinit var adapter: MainPostFragmentAdapter

    lateinit var followingList : ArrayList<UserModel>
    lateinit var adapter2: MainFollowingSetAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        postList = ArrayList()
        adapter = MainPostFragmentAdapter(postList, requireContext())


        followingList = ArrayList()
        adapter2 = MainFollowingSetAdapter(followingList, requireContext())


        setUPPostForAll()
        setUpFollowingData()
        return binding.root
    }

    private fun setUPPostForAll() {

        binding.mainPostRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.mainPostRv.setHasFixedSize(false)
        binding.mainPostRv.adapter = adapter

        Firebase.firestore.collection(Post_user).get().addOnSuccessListener {

            val templist = ArrayList<Post>()
            postList.clear()

            for (i in it.documents) {
                val user = i.toObject<Post>()!!
                templist.add(user)
            }

            postList.addAll(templist)
            postList.reverse()

            adapter.notifyDataSetChanged()

        }
    }

    private fun setUpFollowingData(){

        binding.recyclerView3.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView3.setHasFixedSize(false)
        binding.recyclerView3.adapter = adapter2


        Firebase.firestore.collection(FirebaseAuth.getInstance().currentUser!!.uid+Follow).get().addOnSuccessListener {

            try {
                val templist = ArrayList<UserModel>()
                followingList.clear()

                for (i in it.documents) {
                    val user = i.toObject<UserModel>()!!
                    templist.add(user)
                }

                followingList.addAll(templist)
                followingList.reverse()

                adapter2.notifyDataSetChanged()

            }catch (e : Exception){

            }


        }
    }




}