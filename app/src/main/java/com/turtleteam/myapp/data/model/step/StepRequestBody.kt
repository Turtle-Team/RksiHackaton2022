package com.turtleteam.myapp.data.model.step

data class StepRequestBody(
    val event_id: Int,
    val date_start: String,
    val date_end: String,
    val header: String,
    val text: String,
    val url: String
)