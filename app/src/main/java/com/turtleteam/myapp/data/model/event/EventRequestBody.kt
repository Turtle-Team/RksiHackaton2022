package com.turtleteam.myapp.data.model.event

data class EventRequestBody(
    val header: String,
    val text: String,
    val url: String,
    val date_start: String
)