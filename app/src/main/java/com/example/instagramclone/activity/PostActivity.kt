package com.example.instagramclone.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.instagramclone.databinding.ActivityPostBinding
import com.example.instagramclone.model.Post
import com.example.instagramclone.model.UserModel
import com.example.instagramclone.utils.Post_Image
import com.example.instagramclone.utils.Post_user
import com.example.instagramclone.utils.User_Node
import com.example.instagramclone.utils.uploadImage
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class PostActivity : AppCompatActivity() {

    private val bindingPost by lazy{
        ActivityPostBinding.inflate(layoutInflater)
    }
    var postImage: String? = null
    var postCount : Int? = null

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uriImage ->


            uriImage.let {

                if(it == null){
                    Toast.makeText(this, "Image not selected try again", Toast.LENGTH_LONG).show()
                }else{
                    uploadImage(uriImage!!, Post_Image) {
                        if (it == null) {

                        } else {
                            Toast.makeText(this, it, Toast.LENGTH_LONG).show()

                            postImage = it
                            bindingPost.postImage.setImageURI(uriImage)

                        }
                    }

                }

            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bindingPost.root)

        bindingPost.postImage.setOnClickListener {
            startForProfileImageResult.launch("image/*")
        }

        bindingPost.btnPostComplete.setOnClickListener {
            setPostToFirebase()
        }

        bindingPost.btnPostCancel.setOnClickListener {
            finish()
        }

        Firebase.firestore.collection(User_Node).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {

            var user : UserModel = it.toObject<UserModel>()!!

            postCount = user.posts!!

        }


    }

    @SuppressLint("SuspiciousIndentation")
    private fun setPostToFirebase(){
        Firebase.firestore.collection(User_Node).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {

            var user: UserModel = it.toObject<UserModel>()!!
            val userImage = user.image!!
            val userName = user.userName!!
            val timeInMillis = System.currentTimeMillis()
            val time = TimeAgo.using(timeInMillis)
            val userPost = Post(postImage, bindingPost.postCaption.text.toString(), time, userImage, userName)

            Firebase.firestore.collection(Post_user).document()
                .set(userPost).addOnSuccessListener {


                    postCount = postCount?.plus(1)

                    Firebase.firestore.collection(FirebaseAuth.getInstance().currentUser!!.uid)
                        .document()
                        .set(userPost).addOnSuccessListener {
                            val updates = hashMapOf<String, Any>()

                            updates["posts"] = postCount!!


                            Firebase.firestore.collection(User_Node).document(Firebase.auth.currentUser!!.uid).update(
                                updates as Map<String, Any>
                            )
                                .addOnSuccessListener {

                                }
                            finish()
                        }
                }
        }
    }
}