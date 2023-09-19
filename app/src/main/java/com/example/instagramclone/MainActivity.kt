package com.example.instagramclone

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.instagramclone.activity.FavouriteActivity
import com.example.instagramclone.activity.LoginActivity
import com.example.instagramclone.databinding.ActivityMainBinding
import com.example.instagramclone.model.UserModel
import com.example.instagramclone.utils.User_Node
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private val bindingMain by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    val mAuth = FirebaseAuth.getInstance()
    lateinit var drawableLayout : DrawerLayout
    var mode = false

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bindingMain.root)

        drawableLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        val toggle = ActionBarDrawerToggle(
            this, drawableLayout, bindingMain.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )

        drawableLayout.addDrawerListener(toggle)
        toggle.syncState()

        if (drawableLayout.isDrawerOpen(GravityCompat.START)) {
              drawableLayout.openDrawer(GravityCompat.START)
        }
        val navigationView: NavigationView =  findViewById(R.id.nav_view)
        val header: View = navigationView.getHeaderView(0)
        val userName: TextView = header.findViewById(R.id.header_user_name)
        val userEmail: TextView = header.findViewById(R.id.header_user_email)
//        val profileImage: ImageView = header.findViewById(R.id.header_image)

        Firebase.firestore.collection(User_Node).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {

            var user : UserModel = it.toObject<UserModel>()!!
            userName.text = user.userName.toString()
            userEmail.text = user.email.toString()
//            if(profileImage != null){
//                Glide.with(this).load(user.image).into(profileImage)
//            }

        }
        val navigationSideView = findViewById<NavigationView>(R.id.nav_view) // Replace with your NavigationView ID
        val menuItemText = navigationSideView.menu.findItem(R.id.nav_dark) // Replace with the ID of the item you want to change


        bindingMain.navView.setNavigationItemSelectedListener { menuItem ->

            when (menuItem.itemId) {
                R.id.nav_Home -> {


                    val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                    val navController: NavController = navHostFragment.navController

                    // Navigate to the editProfileFragment
                    navController.navigate(R.id.homeFragment)

                }R.id.nav_dark -> {

                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                }R.id.nav_light -> {

                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)


                }
                R.id.nav_bookmark_item -> {

                    val intent = Intent(this, FavouriteActivity::class.java)
                    startActivity(intent)

                }
                R.id.nav_about -> {

                }
                R.id.naw_logout -> {
                    val mAuth = FirebaseAuth.getInstance()
                    mAuth.signOut()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
                // Add more items as needed
            }
            // Close the drawer when an item is selected
            drawableLayout.closeDrawer(GravityCompat.START)
            true
        }



        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        bindingMain.bottomNavigationView.setupWithNavController(navController)
    }

}
