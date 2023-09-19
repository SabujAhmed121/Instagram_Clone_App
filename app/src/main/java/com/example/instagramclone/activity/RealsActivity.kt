package com.example.instagramclone.activity

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.instagramclone.databinding.ActivityRealsBinding
import com.example.instagramclone.model.Reals
import com.example.instagramclone.model.UserModel
import com.example.instagramclone.utils.Post_Video
import com.example.instagramclone.utils.Reel_User
import com.example.instagramclone.utils.User_Node
import com.example.instagramclone.utils.uploadVideo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlin.properties.Delegates

class RealsActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityRealsBinding.inflate(layoutInflater)
    }
    var realsVideo: String? = null
    lateinit var progressDialog: ProgressDialog
    var realsCount : Int? = null

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uriVideo ->


            uriVideo.let {

                uploadVideo(uriVideo!!, Post_Video, progressDialog) {
                    if (it == null) {

                    } else {
                        Toast.makeText(this, it, Toast.LENGTH_LONG).show()

                        realsVideo = it

                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)

        binding.btnSelectVideo.setOnClickListener {
            startForProfileImageResult.launch("video/*")
        }

        binding.btnRealsPost.setOnClickListener {
            upLoadRealsIntoFirebase()
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }

        Firebase.firestore.collection(User_Node)
            .document(Firebase.auth.currentUser!!.uid).get()
            .addOnSuccessListener {

                var user: UserModel = it.toObject<UserModel>()!!

                realsCount = user.reals!!

            }

    }

    private fun upLoadRealsIntoFirebase() {
        Firebase.firestore.collection(User_Node).document(Firebase.auth.currentUser!!.uid).get()
            .addOnSuccessListener {

                var user: UserModel = it.toObject<UserModel>()!!
                val userImage = user.image!!

                val userReals = Reals(realsVideo, binding.videoCaption.text.toString(), userImage)

                Firebase.firestore.collection(Reel_User).document()
                    .set(userReals).addOnSuccessListener {

                        Firebase.firestore.collection(FirebaseAuth.getInstance().currentUser!!.uid + Reel_User)
                            .document()
                            .set(userReals).addOnSuccessListener {


                                realsCount = realsCount?.plus(1)

                                Firebase.firestore.collection(FirebaseAuth.getInstance().currentUser!!.uid)
                                    .document()
                                    .set(userReals).addOnSuccessListener {
                                        val updates = hashMapOf<String, Any>(
                                            "reals" to realsCount!!
                                        )

                                        Firebase.firestore.collection(User_Node)
                                            .document(Firebase.auth.currentUser!!.uid).update(
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
}