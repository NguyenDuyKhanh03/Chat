package com.example.chat.Models

public class Users(
    var userName: String,
    var mail: String,
    var password: String
){
    private var profilePic: String = ""
    private var userId: String = ""
    private var lastMessage: String = ""
    private var status: String = ""

    constructor(userName: String, mail: String, password: String, profilePic: String, userId: String, lastMessage: String, status: String)
            : this(userName, mail, password) {
        this.profilePic = profilePic
        this.userId = userId
        this.lastMessage = lastMessage
        this.status = status
    }
}
