package com.example.instagramclone.viewmodel

import android.app.Application
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.instagramclone.MainActivity
import com.example.instagramclone.model.UserModel
import com.example.instagramclone.utils.User_Node
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.UUID

class RegistrationAndLoginViewModel(application: Application) : AndroidViewModel(application) {

    private val auth = FirebaseAuth.getInstance()
    val registrationStatus = MutableLiveData<Boolean>()
    val userStatus = MutableLiveData<Boolean>()
    val loginStatus = MutableLiveData<Boolean>()

    suspend fun registerUser(username: String, profile: String, email: String, password: String, Bio : String, reals : Int, posts : Int, following : Int) {

        try {
            val authResult = withContext(Dispatchers.IO) {
                async {
                    auth.createUserWithEmailAndPassword(email, password).await()

                }.await()
            }
            val userData = UserModel(username, profile, email, password, Bio, reals, posts,following)
            Firebase.firestore.collection(User_Node).document(FirebaseAuth.getInstance().currentUser!!.uid).set(userData).await()
            withContext(Dispatchers.Main) {
                registrationStatus.value = true // Update on the main thread
            }


        } catch (e: Exception) {

            withContext(Dispatchers.Main) {
                registrationStatus.value = false // Update on the main thread
            }
            // Handle registration failure here
        }


    }



    suspend fun loginForUser(email: String, password: String){


        try {
            withContext(Dispatchers.IO) {
                async {
                    auth.signInWithEmailAndPassword(email, password).await()

                }.await()
            }

            withContext(Dispatchers.Main) {
                loginStatus.value = true
            }
        }catch (e:Exception){
            loginStatus.value = true
        }


    }
}








//
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                // Sign in success, update UI with the signed-in user's information
//
//
//                val userData = UserModel(username, profile, email, password)
//                Firebase.firestore.collection(User_Node).document(FirebaseAuth.getInstance().currentUser!!.uid).set(userData).addOnSuccessListener {
//
//                        registrationStatus.value = true
//
//                    }
//            } else {
//
//                registrationStatus.value = true
//
//            }
//        }