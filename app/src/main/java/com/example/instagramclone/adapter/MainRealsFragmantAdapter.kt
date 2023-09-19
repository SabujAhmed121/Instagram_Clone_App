package com.example.instagramclone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.instagramclone.databinding.ItemForRealsUserBinding
import com.example.instagramclone.databinding.ItemForRellsRecyclerviewBinding
import com.example.instagramclone.model.Reals

class MainRealsFragmantAdapter(private var items: ArrayList<Reals>,
                               private val context: Context
) :
    RecyclerView.Adapter<MainRealsFragmantAdapter.MainHolder>() {


    inner class MainHolder(private val binding: ItemForRellsRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val realsVideo = binding.videoViewRell
        val realsCaption = binding.rellCaption
        val realsProfile = binding.rellProfileImage
        val progressBar = binding.progressBar
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            ItemForRellsRecyclerviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {

        val item = items[position]
        holder.realsCaption.text = item.realsCaption
        Glide.with(context)
            .load(item.profileUrl)
            .into(holder.realsProfile)

        holder.realsVideo.setVideoPath(item.realsVideo)
        holder.realsVideo.setOnPreparedListener {
            holder.progressBar.visibility = View.GONE
            holder.realsVideo.start()
        }

    }
    override fun getItemCount(): Int {
        return items.size
    }
}