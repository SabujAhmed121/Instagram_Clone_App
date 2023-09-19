package com.example.instagramclone

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.instagramclone.databinding.FragmentEditProfileBinding
import com.example.instagramclone.model.UserModel
import com.example.instagramclone.utils.User_Node
import com.example.instagramclone.utils.User_Profile_Folder
import com.example.instagramclone.utils.uploadImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class EditProfileFragment : Fragment() {

    private val binding by lazy {
        FragmentEditProfileBinding.inflate(layoutInflater)
    }
    var profileImage: String? = null
    var selectedImageUri: Uri? = null
    lateinit var user: UserModel


    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uriImage ->


            uriImage.let {
                selectedImageUri = it

                uploadImage(uriImage!!, User_Profile_Folder) {
                    if (it == null) {

                    } else {
                        Toast.makeText(context, it, Toast.LENGTH_LONG).show()

                        profileImage = it
                        binding.profileImage.setImageURI(uriImage)

                    }
                }
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {




        Firebase.firestore.collection(User_Node).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {


            var user : UserModel = it.toObject<UserModel>()!!
            binding.usernameInput.setText(user.userName)
            binding.emailInput.setText(user.email)
            binding.bioInput.setText(user.Bio)
            Glide.with(this).load(user.image).into(binding.profileImage)



        }

        binding.saveImage.setOnClickListener {

            if(binding.profileImage.drawable == null){
                Toast.makeText(context, "Choose image", Toast.LENGTH_LONG).show()

            }else{
                upDateData()
                findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment2)

            }
        }
        binding.backImage.setOnClickListener {
            showAlertDialog()
        }
        user = UserModel()

        binding.changePhotoBt.setOnClickListener {
            startForProfileImageResult.launch("image/*")
        }



        return binding.root
    }

    private fun upDateData() {

        val fieldNameToUpdate = "image"
        val newValue = profileImage

        // Create a map with the data you want to update
//        val dataToUpdate = hashMapOf(
//
//            "email" to binding.emailInput.text.toString(),
//            "image" to profileImage,
//            "userName" to binding.usernameInput.text.toString()
//
//            )


        val updates = hashMapOf<String, Any>()

        // Example: Updating the "name" field (replace with your field names as needed)
        val newName = binding.usernameInput.text.toString()
//
        if (newName.isNotEmpty()) {
            updates["userName"] = newName
        }

        // Example: Updating the "email" field
        val newEmail = binding.emailInput.text.toString()
        if (newEmail.isNotEmpty()) {
            updates["email"] = newEmail
        }

        // Example: Updating the "city" field
        val newImage = "$profileImage"
        if (selectedImageUri != null) {
            updates["image"] = newImage
        }


        Firebase.firestore.collection(User_Node).document(Firebase.auth.currentUser!!.uid).update(
            updates as Map<String, Any>
        )
            .addOnSuccessListener {
                // Update was successful
                // You can perform any necessary actions here
                Toast.makeText(context, "Successful", Toast.LENGTH_LONG).show()


            }

            .addOnFailureListener {
                Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show()

            }
    }

    private fun showAlertDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            .setTitle("Alert")
            .setMessage("Are you sure about that ?")
            .setPositiveButton("Yes") { dialogInterface, which ->
                findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment2)
            }
            .setNeutralButton("No") { dialogInterface, which ->
                dialogInterface.dismiss()
            }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

}