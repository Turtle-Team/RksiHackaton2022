package com.turtleteam.myapp.data.api

import com.turtleteam.myapp.data.model.event.EventRequestBody
import com.turtleteam.myapp.data.model.event.Events
import com.turtleteam.myapp.data.model.users.AuthRequestBody
import retrofit2.Response
import retrofit2.http.*

interface EventService {

    @GET("auth/{token}")
    suspend fun getUserInfo(
        @Path("token") token: String
    ): Response<AuthRequestBody>

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

    @PUT("event/{id}")
    suspend fun editEvent(
        @Header("Content-Type") type: String = "application/json",
        @Path("id") id: Int,
        @Body eventModel: EventRequestBody,
        @Query("token") token: String
    )

}