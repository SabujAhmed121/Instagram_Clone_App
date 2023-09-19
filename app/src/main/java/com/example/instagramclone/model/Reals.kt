package com.example.instagramclone.model

class Reals {

    var realsVideo: String? = null
    var realsCaption: String? = null
    var profileUrl : String? = null

    constructor()

    constructor(realsVideo: String?, realsCaption: String?, profileUrl: String?) {
        this.realsVideo = realsVideo
        this.realsCaption = realsCaption
        this.profileUrl = profileUrl
    }


}