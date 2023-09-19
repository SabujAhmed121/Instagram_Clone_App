package com.example.instagramclone.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramclone.R
import com.example.instagramclone.databinding.ItemForFavouritePostBinding
import com.example.instagramclone.databinding.ItemForRellsRecyclerviewBinding
import com.example.instagramclone.databinding.ItemforpostMainFragmentRecyclerviewBinding
import com.example.instagramclone.model.Post
import com.example.instagramclone.model.Reals
import com.example.instagramclone.model.UserModel
import com.example.instagramclone.utils.Favourite
import com.example.instagramclone.utils.Follow
import com.example.instagramclone.utils.User_Node
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class FavouriteFragmentAdapter (private var items: ArrayList<Post>,
                                private val context: Context
) :
    RecyclerView.Adapter<FavouriteFragmentAdapter.MainHolder>() {


    inner class MainHolder(private val binding: ItemForFavouritePostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val postImage = binding.postedImage
        val postCaption = binding.postedCaption
        val delete = binding.deletedSave

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            ItemForFavouritePostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {

        val item = items[position]

        holder.postCaption.text = item.postCaption

        Glide.with(context)
            .load(item.postImage)
            .into(holder.postImage)


        holder.delete.setOnClickListener {
                Firebase.firestore.collection(FirebaseAuth.getInstance().currentUser!!.uid + Favourite).whereEqualTo("postImage", item.postImage).get().addOnSuccessListener {
                    Firebase.firestore.collection(FirebaseAuth.getInstance().currentUser!!.uid+Favourite ).document(it.documents.get(0).id).delete()
                    Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_LONG).show()
            }
        }


    }
    override fun getItemCount(): Int {
        return items.size
    }
}