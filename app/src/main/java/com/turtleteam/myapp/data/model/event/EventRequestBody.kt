package com.turtleteam.myapp.data.model.event

data class EventRequestBody(
    val title: String,
    val description: String,
    val url: String,
    val date: String
)