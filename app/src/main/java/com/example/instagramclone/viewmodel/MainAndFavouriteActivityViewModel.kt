package com.example.instagramclone.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.instagramclone.model.Post
import com.example.instagramclone.utils.Favourite
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainAndFavouriteActivityViewModel (application: Application) : AndroidViewModel(application){


    private var isDarkModeEnabled = false
    private val _favouritePostList = MutableLiveData<List<Post>>()
    val favouritePostList : LiveData<List<Post>> = _favouritePostList



    fun fetchFavouritePost (){

        try {
            viewModelScope.launch(Dispatchers.IO){
                val snapshotOfFavouritePost = Firebase.firestore.collection(FirebaseAuth.getInstance().currentUser!!.uid + Favourite).get().await()
                val temList = snapshotOfFavouritePost.documents.mapNotNull { it.toObject<Post>() }
                _favouritePostList.postValue(temList.reversed())
            }
        }catch (_:Exception){

        }
    }



    fun isDarkModeOn(): Boolean {
        return isDarkModeEnabled
    }

    fun toggleDarkMode() {
        isDarkModeEnabled = !isDarkModeEnabled
    }
}