package com.example.instagramclone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.instagramclone.databinding.ItemForPostUserBinding
import com.example.instagramclone.databinding.ItemForRealsUserBinding
import com.example.instagramclone.model.Post
import com.example.instagramclone.model.Reals

class UserVideoRealsAdapter (private var items: ArrayList<Reals>,
                                                  private val context: Context) :
    RecyclerView.Adapter<UserVideoRealsAdapter.MainHolder>() {


    inner class MainHolder(private val binding: ItemForRealsUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val realsVideo = binding.videoTumnail
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            ItemForRealsUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {

        val item = items[position]
        Glide.with(context)
            .load(item.realsVideo)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.realsVideo)

    }
    override fun getItemCount(): Int {
        return items.size
    }
}

