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
 import com.example.instagramclone.adapter.MainFollowingSetAdapter
 import com.example.instagramclone.adapter.MainPostFragmentAdapter
import com.example.instagramclone.databinding.FragmentHomeBinding
import com.example.instagramclone.model.Post
 import com.example.instagramclone.model.UserModel
 import com.example.instagramclone.utils.Follow
 import com.example.instagramclone.utils.Post_user
 import com.example.instagramclone.viewmodel.HomeFragmentViewModel
 import com.example.instagramclone.viewmodel.RegistrationAndLoginViewModel
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
    private lateinit var viewModel: HomeFragmentViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        postList = ArrayList()
        adapter = MainPostFragmentAdapter(postList, requireContext())
        viewModel = ViewModelProvider(this)[HomeFragmentViewModel::class.java]



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

        viewModel.postList.observe(viewLifecycleOwner, Observer { posts ->
            postList.clear()
            postList.addAll(posts)
            adapter.notifyDataSetChanged()
        })

        viewModel.fetchPosts()

//        Firebase.firestore.collection(Post_user).get().addOnSuccessListener {
//
//            val templist = ArrayList<Post>()
//            postList.clear()
//
//            for (i in it.documents) {
//                val user = i.toObject<Post>()!!
//                templist.add(user)
//            }
//
//            postList.addAll(templist)
//            postList.reverse()
//
//            adapter.notifyDataSetChanged()
//
//        }
    }

    private fun setUpFollowingData(){

        binding.recyclerView3.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView3.setHasFixedSize(false)
        binding.recyclerView3.adapter = adapter2


        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        viewModel.fetchFollowingData(userId)


        viewModel.followingList.observe(viewLifecycleOwner, Observer { following ->
            followingList.clear()
            followingList.addAll(following)
            adapter2.notifyDataSetChanged()
        })

    }




}