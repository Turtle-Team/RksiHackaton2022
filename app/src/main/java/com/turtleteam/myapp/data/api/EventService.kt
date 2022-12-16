package com.turtleteam.myapp.data.api

import com.turtleteam.myapp.data.model.event.Events
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface EventService {

    @GET("event/")
    suspend fun getAllEvents(
        @Header("Content-Type") type: String = "application/json",
    ): List<Events>

    @POST("event/")
    suspend fun createEvent(
        @Header("Content-Type") type: String = "application/json",
        @Body eventModel: String
    )
}