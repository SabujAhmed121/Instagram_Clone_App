package com.example.instagramclone.model

import androidx.activity.result.contract.ActivityResultContracts
import com.example.instagramclone.utils.User_Profile_Folder
import com.example.instagramclone.utils.uploadImage

class UserModel {

    var userName: String? = null
    var image: String? = null
    var email: String? = null
    var password: String? = null
    var Bio : String? = null
    var reals : Int? = null
    var posts : Int? = null
    var following : Int? = null

    constructor()
    constructor(userName: String?, image: String?, email: String?, password: String?) {
        this.userName = userName
        this.image = image
        this.email = email
        this.password = password
    }

    constructor(
        userName: String?,
        image: String?,
        email: String?,
        password: String?,
        Bio: String?,
        reals: Int?,
        posts: Int?,
        following: Int?
    ) {
        this.userName = userName
        this.image = image
        this.email = email
        this.password = password
        this.Bio = Bio
        this.reals = reals
        this.posts = posts
        this.following = following
    }


}


