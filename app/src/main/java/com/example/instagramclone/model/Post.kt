package com.example.instagramclone.model

class Post {

    var postImage: String? = null
    var postCaption: String? = null
    var postTime: String? = null
    var postProfileImage: String? = null
    var postProfileUser: String? = null

    constructor()

    constructor(
        postImage: String?,
        postCaption: String?,
        postTime: String?,
        postProfileImage: String?,
        postProfileUser: String?
    ) {
        this.postImage = postImage
        this.postCaption = postCaption
        this.postTime = postTime
        this.postProfileImage = postProfileImage
        this.postProfileUser = postProfileUser
    }


}