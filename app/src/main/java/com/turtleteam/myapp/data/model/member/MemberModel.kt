package com.turtleteam.myapp.data.model.member

class MemberModel : ArrayList<MemberModelItem>()

data class MemberModelItem(
    val event_id: Int,
    val user: User
)

data class User(
    val email: String,
    val fio: String,
    val organization: String,
    val post: String,
    val status: String
)