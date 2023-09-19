package com.example.instagramclone.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.instagramclone.MainActivity
import com.example.instagramclone.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: RegistrationAndLoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(RegistrationAndLoginViewModel::class.java)

        binding.donHave!!.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        val editor = getSharedPreferences("MyUser", MODE_PRIVATE)
        binding.emailLogin.setText(editor.getString("email", null))

        binding.loginBt.setOnClickListener {


            val email = binding.emailLogin.text.toString()
            val password = binding.passwordLogin.text.toString()

            viewModel.loginForUser(email, password)

            viewModel.loginStatus.observe(this) { isSuccess ->
                if (isSuccess) {
                    // Registration successful
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    Toast.makeText(baseContext, "Login successful", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    // Registration failed
                    Toast.makeText(baseContext, "Login failed", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }
    }
}