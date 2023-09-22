package com.example.instagramclone.viewmodel

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.instagramclone.model.Reals
import com.example.instagramclone.model.UserModel
import com.example.instagramclone.utils.Reel_User
import com.example.instagramclone.utils.User_Node
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainViewModel(application: Application): AndroidViewModel(application)  {

    //Search Fragment
    private val _userList = MutableLiveData<List<UserModel>>()
    val userList : LiveData<List<UserModel>> = _userList

    private val _searchList = MutableLiveData<List<UserModel>>()
    val searchList : LiveData<List<UserModel>> = _searchList

    //Reals Fragment
    private val _realsList = MutableLiveData<List<Reals>>()
    val realsList : LiveData<List<Reals>> = _realsList


    fun fetchUserData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val snapshot = Firebase.firestore.collection(User_Node).get().await()

                for(i in snapshot.documents){
                    if (i.id.toString().equals(FirebaseAuth.getInstance().currentUser!!.uid.toString())){

                    }else{
                        val tempList = snapshot.documents.mapNotNull { it.toObject<UserModel>() }
                        _userList.postValue(tempList.reversed())
                    }
                }
            } catch (e: Exception) {
                // Handle the exception
            }
        }
    }

    fun fetchSearchUserData(text : String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val snapshot = Firebase.firestore.collection(User_Node).whereEqualTo("userName",
                    text
                ).get().await()

                for(i in snapshot.documents){
                    if (i.id.toString().equals(FirebaseAuth.getInstance().currentUser!!.uid.toString())){

                    }else{
                        val tempList = snapshot.documents.mapNotNull { it.toObject<UserModel>() }
                        _userList.postValue(tempList.reversed())
                    }
                }
            } catch (e: Exception) {
                // Handle the exception
            }
        }
    }

    fun fetchRealsData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                        val snapshot = Firebase.firestore.collection(Reel_User).get().await()
                        val tempList = snapshot.documents.mapNotNull { it.toObject<Reals>() }
                        _realsList.postValue(tempList.reversed())

                } catch (e: Exception) {
                // Handle the exception
            }
        }
    }









    fun saveSearchData(context: Context, searchText: String) {
        val sharedPreferences = context.getSharedPreferences("MySearch", AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.putString("searchData", searchText)
        editor.apply()
    }
}