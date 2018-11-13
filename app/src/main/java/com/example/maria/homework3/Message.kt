package com.example.maria.homework3

class Message(private val user: Int, private var content: String){
    var messageContent: String
        get() = this.content
        set(value: String){this.content=value}

    val getUser: Int
        get() = this.user

}