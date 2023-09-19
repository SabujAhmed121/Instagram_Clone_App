package com.example.instagramclone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramclone.databinding.ItemForSearchRecyclerviewBinding
import com.example.instagramclone.model.UserModel
import com.example.instagramclone.utils.Follow
import com.example.instagramclone.utils.Post_user
import com.example.instagramclone.utils.User_Node
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class MainSearchFragmentAdapter (private var items: ArrayList<UserModel>,
                                 private val context: Context
) :
    RecyclerView.Adapter<MainSearchFragmentAdapter.MainHolder>() {


    inner class MainHolder(private val binding: ItemForSearchRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val userName = binding.userName
        val profileImage = binding.profileImageFollow
        val followBtn = binding.btFollowUN
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            ItemForSearchRecyclerviewBinding.inflate(
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
        var isFollow = false
        var following : Int? = null

        Firebase.firestore.collection(FirebaseAuth.getInstance().currentUser!!.uid +Follow).whereEqualTo("email", item.email).get().addOnSuccessListener {

            if (it.documents.size == 0){
                isFollow = false
            }else{
                isFollow = true
                holder.followBtn.text = "UnFollow"
            }
        }

        Firebase.firestore.collection(User_Node).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {

            var user : UserModel = it.toObject<UserModel>()!!

            following = user.following!!

        }




        holder.followBtn.text = "Follow"
        holder.followBtn.setOnClickListener {

            if (isFollow){
                Firebase.firestore.collection(FirebaseAuth.getInstance().currentUser!!.uid + Follow).whereEqualTo("email", item.email).get().addOnSuccessListener {
                    Firebase.firestore.collection(FirebaseAuth.getInstance().currentUser!!.uid+Follow).document(it.documents.get(0).id).delete()
                    following = following?.minus(1)
                    updateFollowing(following!!)

                }
                isFollow = false
                holder.followBtn.text = "Follow"
            }else{
                    Firebase.firestore.collection(FirebaseAuth.getInstance().currentUser!!.uid +Follow).document()
                        .set(item).addOnSuccessListener {
                            isFollow = true
                            following = following?.plus(1)
                            updateFollowing(following!!)
                            holder.followBtn.text = "UnFollow"
                        }

            }


        }

    }
    override fun getItemCount(): Int {
        return items.size
    }

    fun updateFollowing(following : Int): Int{

        val updates = hashMapOf<String, Any>()

        updates["following"] = following


        Firebase.firestore.collection(User_Node).document(Firebase.auth.currentUser!!.uid).update(
            updates as Map<String, Any>
        )
            .addOnSuccessListener {

            }

        return following

    }
}