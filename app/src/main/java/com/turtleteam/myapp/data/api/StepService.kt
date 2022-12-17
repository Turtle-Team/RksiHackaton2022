package com.turtleteam.myapp.data.api

import com.turtleteam.myapp.data.model.step.Step
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface StepService {

    @GET("event/{id}/steps")
    suspend fun getAllStepsByEvent(
        @Header("Content-Type") type: String = "application/json",
        @Path("id") id: Int,
        @Query("token") token: String
    ): List<Step>
}