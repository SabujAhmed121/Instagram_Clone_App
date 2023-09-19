package com.example.instagramclone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramclone.databinding.FollowProfileItemHomeFragmentBinding
import com.example.instagramclone.model.UserModel

class MainFollowingSetAdapter (private var items: ArrayList<UserModel>,
                               private val context: Context
) :
    RecyclerView.Adapter<MainFollowingSetAdapter.MainHolder>() {


    inner class MainHolder(private val binding: FollowProfileItemHomeFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val userName = binding.followUserName
        val profileImage = binding.profileImage
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            FollowProfileItemHomeFragmentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {

        val item = items[position]
        holder.userName.text = item.userName
        Glide.with(context)
            .load(item.image)
            .into(holder.profileImage)


    }
    override fun getItemCount(): Int {
        return items.size
    }
}