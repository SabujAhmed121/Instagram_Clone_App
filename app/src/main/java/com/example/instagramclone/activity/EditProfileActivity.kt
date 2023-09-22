package com.example.instagramclone.activity

import android.app.AlertDialog
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.instagramclone.R
import com.example.instagramclone.databinding.ActivityEditProfileBinding
import com.example.instagramclone.model.UserModel
import com.example.instagramclone.utils.User_Profile_Folder
import com.example.instagramclone.utils.uploadImage
import com.example.instagramclone.viewmodel.ProfileAndEditProfileViewModel
import com.google.firebase.auth.FirebaseAuth

class EditProfileActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityEditProfileBinding.inflate(layoutInflater)
    }

    var profileImage: String? = null
    var selectedImageUri: Uri? = null
    lateinit var user: UserModel
    private lateinit var viewModel: ProfileAndEditProfileViewModel


    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uriImage ->


            uriImage.let {
                selectedImageUri = it

                uploadImage(uriImage!!, User_Profile_Folder) {
                    if (it == null) {

                    } else {

                        profileImage = it
                        binding.profileImage.setImageURI(uriImage)

                    }
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ProfileAndEditProfileViewModel::class.java)
        user = UserModel()

        setUserData()

        binding.saveImage.setOnClickListener {

            if (binding.profileImage.drawable == null) {
                Toast.makeText(this, "Choose image", Toast.LENGTH_LONG).show()

            } else {
                val newName = binding.usernameInput.text.toString()
                val newEmail = binding.emailInput.text.toString()
                val newBio = binding.bioInput.text.toString()
                val newImage = "$profileImage"
                if (selectedImageUri != null) {
                    viewModel.updateUserData(
                        newName,
                        newEmail,
                        newImage,
                        newBio,
                        selectedImageUri!!
                    )
                    finish()

                } else {
                    Toast.makeText(this, "Choose image", Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.backImage.setOnClickListener {
            showAlertDialog()
        }

        binding.changePhotoBt.setOnClickListener {
            startForProfileImageResult.launch("image/*")
        }

        viewModel.updateResult.observe(this, Observer { isSuccess ->

            if (isSuccess) {
                Toast.makeText(this, "Update successful", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Update failed", Toast.LENGTH_LONG).show()
            }

        })

    }

    private fun setUserData() {

        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        viewModel.fetchUserData(userId)

        viewModel.userInfo.observe(this, Observer { user ->
            binding.usernameInput.setText(user.userName)
            binding.emailInput.setText(user.email)
            binding.bioInput.setText(user.Bio)
            Glide.with(this).load(user.image).into(binding.profileImage)
        })
    }

    private fun showAlertDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            .setTitle("Alert")
            .setMessage("Are you sure about that ?")
            .setPositiveButton("Yes") { dialogInterface, which ->
                finish()
            }
            .setNeutralButton("No") { dialogInterface, which ->
                dialogInterface.dismiss()
            }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

}