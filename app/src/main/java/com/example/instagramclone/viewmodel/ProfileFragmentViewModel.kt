package com.example.instagramclone.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.instagramclone.model.UserModel
import com.example.instagramclone.utils.User_Node
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class ProfileFragmentViewModel(application: Application): AndroidViewModel(application) {

    var user = UserModel()
//    private val auth = FirebaseAuth.getInstance()

    var newProfilePic : String? = null
    var newProfilename : String? = null
    var newProfileBio : String? = null

    fun setUpProfileInformation(){

//        Firebase.firestore.collection(User_Node).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
//
//
//            var user : UserModel = it.toObject<UserModel>()!!
//            newProfilePic = user!!.image
//            newProfilename = user!!.userName
//            newProfileBio= user!!.email
//
//
//
//        }


    }

}