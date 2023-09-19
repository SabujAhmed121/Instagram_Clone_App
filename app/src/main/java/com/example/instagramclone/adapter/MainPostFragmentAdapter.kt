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

class MainPostFragmentAdapter (private var items: ArrayList<Post>,
                               private val context: Context
) :
    RecyclerView.Adapter<MainPostFragmentAdapter.MainHolder>() {


    inner class MainHolder(private val binding: ItemforpostMainFragmentRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val postImage = binding.postedImage
        val postCaption = binding.postedCaption
        val postTime = binding.posttedTime
        val postUserProfile = binding.profileImage
        val postUserName = binding.usernameText
        val likedbt = binding.loveIcon
        val send = binding.sendIcon
        val save = binding.bookmarkIcon

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            ItemforpostMainFragmentRecyclerviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {

        val item = items[position]
        var liked = false

        holder.postCaption.text = item.postCaption
        holder.postTime.text = item.postTime
        holder.postUserName.text = item.postProfileUser
        holder.likedbt.setImageResource(R.drawable.heart)
        holder.save.setImageResource(R.drawable.notsaveicon)

        Glide.with(context)
            .load(item.postProfileImage)
            .into(holder.postUserProfile)

        Glide.with(context)
            .load(item.postImage)
            .into(holder.postImage)

        holder.likedbt.setOnClickListener {
            if (liked){
                holder.likedbt.setImageResource(R.drawable.heart)
                liked = false
            }else{
                holder.likedbt.setImageResource(R.drawable.love)
                liked = true

            }
        }

        holder.save.setOnClickListener {

                Firebase.firestore.collection(FirebaseAuth.getInstance().currentUser!!.uid + Favourite).document()
                    .set(item).addOnSuccessListener {
                        Toast.makeText(context, "Favourite Post Added", Toast.LENGTH_LONG).show()
                        holder.save.setImageResource(R.drawable.bookmark_saved)
                    }
        }

//        Firebase.firestore.collection(FirebaseAuth.getInstance().currentUser!!.uid + Follow).whereEqualTo("email", item.email).get().addOnSuccessListener {
//
//            if (it.documents.size == 0){
//                isFollow = false
//            }else{
//                isFollow = true
//                holder.followBtn.text = "UnFollow"
//            }
//        }
//

//
//        Firebase.firestore.collection(User_Node).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
//
//            var user : UserModel = it.toObject<UserModel>()!!
//
//            following = user.following!!
//
//        }
//
//
//
//
//        holder.followBtn.text = "Follow"
//        holder.followBtn.setOnClickListener {
//
//            if (isFollow){
//                Firebase.firestore.collection(FirebaseAuth.getInstance().currentUser!!.uid + Follow).whereEqualTo("email", item.email).get().addOnSuccessListener {
//                    Firebase.firestore.collection(FirebaseAuth.getInstance().currentUser!!.uid+Follow).document(it.documents.get(0).id).delete()
//                    following = following?.minus(1)
//                    updateFollowing(following!!)
//
//                }
//                isFollow = false
//                holder.followBtn.text = "Follow"
//            }else{
//                Firebase.firestore.collection(FirebaseAuth.getInstance().currentUser!!.uid +Follow).document()
//                    .set(item).addOnSuccessListener {
//                        isFollow = true
//                        following = following?.plus(1)
//                        updateFollowing(following!!)
//                        holder.followBtn.text = "UnFollow"
//                    }
//
//            }
//
//
//        }
        holder.send.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, item.postImage)
            context.startActivity(Intent.createChooser(shareIntent, "Share Image"))
        }


    }
    override fun getItemCount(): Int {
        return items.size
    }
}