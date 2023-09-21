package com.example.instagramclone.viewmodel

import android.app.Application
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.instagramclone.model.UserModel
import com.example.instagramclone.utils.User_Node
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProfileAndEditProfileViewModel(application: Application): AndroidViewModel(application) {


    private val _userInfo = MutableLiveData<UserModel>()
    val userInfo : LiveData<UserModel> = _userInfo

    private val _updateResult = MutableLiveData<Boolean>()
    val updateResult: LiveData<Boolean> = _updateResult


    fun fetchUserData(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val snapshot = Firebase.firestore.collection(User_Node).document(userId).get().await()
                val tempList = snapshot.toObject<UserModel>()
                _userInfo.postValue(tempList!!)
            } catch (e: Exception) {
                // Handle the exception
            }
        }
    }

    fun updateUserData(userName: String, email: String, image: String, bio : String, selectedImage : Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userId = FirebaseAuth.getInstance().currentUser?.uid
                if (userId != null) {

                    val updates = hashMapOf<String, Any>()

                    if (userName.isNotEmpty()) {
                        updates["userName"] = userName
                    }


                    if (email.isNotEmpty()) {
                        updates["email"] = email
                    }

                    if (image.isNotEmpty() && selectedImage != null) {
                        updates["image"] = image
                    }

                    if (bio.isNotEmpty()) {
                        updates["bio"] = bio
                    }

                    Firebase.firestore.collection(User_Node).document(userId)
                        .update(updates as Map<String, Any>)
                        .addOnSuccessListener {
                            Log.d("MyApp", "userName: $userName, email: $email, image: $image, bio: $bio")

                            _updateResult.postValue(true)
                        }
                        .addOnFailureListener {
                            _updateResult.postValue(false)
                        }
                }
            } catch (e: Exception) {
                _updateResult.postValue(false)
            }
        }
    }



}