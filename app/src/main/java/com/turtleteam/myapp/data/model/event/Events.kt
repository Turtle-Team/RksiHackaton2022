package com.turtleteam.myapp.data.model.event

import java.text.DateFormat
import java.time.LocalDateTime

data class Events(
    val id: Int,
    val date: String,
    val header: String,
    val text: String,
    val url: String,
)