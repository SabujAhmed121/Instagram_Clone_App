package com.example.instagramclone.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.instagramclone.model.Post
import com.example.instagramclone.model.Reals
import com.example.instagramclone.model.UserModel
import com.example.instagramclone.utils.Follow
import com.example.instagramclone.utils.Reel_User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PostAndRealsViewModel(application: Application) : AndroidViewModel(application) {

    private val _userPostList = MutableLiveData<List<Post>>()
    val userPostList : LiveData<List<Post>> = _userPostList

    private val _userRealsList = MutableLiveData<List<Reals>>()
    val userRealsList : LiveData<List<Reals>> = _userRealsList


    fun fetchUserPostData(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val snapshot = Firebase.firestore.collection(userId).get().await()
                val tempList = snapshot.documents.mapNotNull { it.toObject<Post>() }
                _userPostList.postValue(tempList.reversed())
            } catch (e: Exception) {
                // Handle the exception
            }
        }
    }

    fun fetchUserRealsData(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val snapshot = Firebase.firestore.collection("$userId$Reel_User").get().await()
                val tempList = snapshot.documents.mapNotNull { it.toObject<Reals>() }
                _userRealsList.postValue(tempList.reversed())
            } catch (e: Exception) {
                // Handle the exception
            }
        }
    }

}