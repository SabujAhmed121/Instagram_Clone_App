package com.example.instagramclone.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.instagramclone.MainActivity
import com.example.instagramclone.databinding.ActivitySplashScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashScreen : AppCompatActivity() {

    private val binding by lazy{
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({

            if (Firebase.auth.currentUser != null){
                startActivity(Intent(this, MainActivity::class.java))
            }else{
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
        },3000)
    }
}