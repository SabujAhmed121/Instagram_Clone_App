package com.example.instagramclone.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.instagramclone.model.Post
import com.example.instagramclone.model.UserModel
import com.example.instagramclone.utils.Follow
import com.example.instagramclone.utils.Post_user
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class HomeFragmentViewModel (application: Application) : AndroidViewModel(application) {

    private val _postList = MutableLiveData<List<Post>>()
    val postList: LiveData<List<Post>> = _postList

    private val _followingList = MutableLiveData<List<UserModel>>()
    val followingList: LiveData<List<UserModel>> = _followingList

    fun fetchPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val snapshot = Firebase.firestore.collection(Post_user).get().await()
                val tempList = snapshot.documents.mapNotNull { it.toObject< Post>() }
                _postList.postValue(tempList.reversed())
            } catch (e: Exception) {
                // Handle the exception
            }
        }
    }

    fun fetchFollowingData(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val snapshot = Firebase.firestore.collection("$userId$Follow").get().await()
                val tempList = snapshot.documents.mapNotNull { it.toObject<UserModel>() }
                _followingList.postValue(tempList.reversed())
            } catch (e: Exception) {
                // Handle the exception
            }
        }
    }

}