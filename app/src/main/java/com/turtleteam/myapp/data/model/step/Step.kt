package com.turtleteam.myapp.data.model.step

data class Step(
    val id: Int,
    val event_id: Int,
    val date_start: String,
    val date_end: String,
    val header: String,
    val text: String,
    val url: String
)