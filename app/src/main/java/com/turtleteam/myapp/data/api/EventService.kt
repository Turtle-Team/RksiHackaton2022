package com.turtleteam.myapp.data.api

import com.turtleteam.myapp.data.model.event.Events
import retrofit2.http.GET

interface EventService {

    @GET("event/")
    suspend fun getAllEvents(): List<Events>
}