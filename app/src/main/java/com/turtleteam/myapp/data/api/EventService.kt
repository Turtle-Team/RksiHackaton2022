package com.turtleteam.myapp.data.api

import com.turtleteam.myapp.data.model.event.EventRequestBody
import com.turtleteam.myapp.data.model.event.Events
import retrofit2.http.*

interface EventService {

    @GET("event/")
    suspend fun getAllEvents(
        @Header("Content-Type") type: String = "application/json",
    ): List<Events>

    @POST("event/")
    suspend fun createEvent(
        @Header("Content-Type") type: String = "application/json",
        @Body eventModel: EventRequestBody,
        @Query("token") token: String
    ): Throwable?

    @DELETE("event/{id}")
    suspend fun deleteEvent(
        @Header("Content-Type") type: String = "application/json",
        @Path("id") id: Int
    )

}