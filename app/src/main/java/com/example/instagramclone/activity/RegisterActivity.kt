package com.example.instagramclone.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.instagramclone.MainActivity
import com.example.instagramclone.databinding.ActivityRegisterBinding
import com.example.instagramclone.model.UserModel
import com.example.instagramclone.utils.User_Profile_Folder
import com.example.instagramclone.utils.uploadImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private val bindingRegister by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }
    lateinit var user: UserModel
    private lateinit var viewModel: RegistrationAndLoginViewModel
    var profileImage: String? = null
    private lateinit var auth: FirebaseAuth


    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uriImage ->

            if (uriImage == null) {
                Toast.makeText(this, "Image not selected try again", Toast.LENGTH_LONG).show()
            }else{
                uriImage.let {

                    uploadImage(uriImage!!, User_Profile_Folder) {
                        if (it == null) {

                        } else {
                            Toast.makeText(this, it, Toast.LENGTH_LONG).show()

                            profileImage = it
                            bindingRegister.profileImage.setImageURI(uriImage)

                        }
                    }
                }

            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(bindingRegister.root)



        auth = Firebase.auth

        viewModel = ViewModelProvider(this)[RegistrationAndLoginViewModel::class.java]
        user = UserModel()




        viewModel.registrationStatus.observe(this) { isSuccess ->
            if (isSuccess) {
                // Registration successful
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                Toast.makeText(baseContext, "Registration successful", Toast.LENGTH_SHORT).show()
            } else {
                // Registration failed
                Toast.makeText(baseContext, "Registration failed", Toast.LENGTH_SHORT).show()
            }
        }


        bindingRegister.alreadyhave.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        bindingRegister.profileImage.setOnClickListener {
            startForProfileImageResult.launch("image/*")
        }

        bindingRegister.registerBt.setOnClickListener {
            setUpRegistrationWork()
        }
    }


    private fun setUpRegistrationWork() {
        val username = bindingRegister.usernameRegister.text.toString()
        val email = bindingRegister.emailRegister.text.toString()
        val password = bindingRegister.passwordRegister.text.toString()
        val comPassword = bindingRegister.comframPassword.text.toString()
        val Bio = "My name is $username"

        if (username.isEmpty()) {
            bindingRegister.usernameRegister.error = "Please enter Username!"

        } else if (email.isEmpty()) {
            Toast.makeText(applicationContext, "Please enter email!", Toast.LENGTH_LONG).show()

        } else if (password.isEmpty()) {
            Toast.makeText(applicationContext, "Please enter password!", Toast.LENGTH_LONG)
                .show()

        } else if (comPassword.isEmpty()) {
            Toast.makeText(
                applicationContext,
                "Please enter  Conformation password!",
                Toast.LENGTH_LONG
            )
                .show()
        } else if (password != comPassword) {
            Toast.makeText(
                applicationContext,
                "Please enter  same password!",
                Toast.LENGTH_LONG
            )
                .show()
        } else {


            if (profileImage != null) {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.registerUser(username, profileImage!!, email, password, Bio, 0,0,0)
                    //                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)

                    val editor = getSharedPreferences("MyUser", MODE_PRIVATE).edit()
                    editor.putString("userName", username.toString())
                    editor.putString("email", email.toString())
                    editor.clear()
                    editor.apply()

                }
            } else {
                Toast.makeText(
                    applicationContext,
                    "Please select a profile image",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}