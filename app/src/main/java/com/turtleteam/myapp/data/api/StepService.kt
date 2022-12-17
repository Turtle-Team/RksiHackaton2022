package com.turtleteam.myapp.data.api

import com.turtleteam.myapp.data.model.event.EventRequestBody
import com.turtleteam.myapp.data.model.step.Step
import retrofit2.http.*

interface StepService {

    @GET("event/{id}/steps")
    suspend fun getAllStepsByEvent(
        @Header("Content-Type") type: String = "application/json",
        @Path("id") id: Int,
        @Query("token") token: String
    ): List<Step>

    @POST("event/{id}/steps")
    suspend fun createEvent(
        @Header("Content-Type") type: String = "application/json",
        @Body eventModel: EventRequestBody,
        @Query("token") token: String
    )

    @DELETE("event/{id}/steps/{step_id}")
    suspend fun deleteStep(
        @Header("Content-Type") type: String = "application/json",
        @Path("id") id: Int,
        @Path("step_id") stepId: Int,
        @Query("token") token: String
    )
}