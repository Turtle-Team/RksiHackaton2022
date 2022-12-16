package com.turtleteam.myapp.data.model.users

data class AuthRequestBody(
    val fio: String,
    val post: String,
    val organization: String,
    val status: String,
    val email: String,
    val password: String
)