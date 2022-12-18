package com.turtleteam.myapp.data.api

import com.turtleteam.myapp.data.model.step.StepRequestBody
import retrofit2.http.*

interface MemberService {

    @POST("member/event/{event_id}")
    suspend fun createMember(
        @Header("Content-Type") type: String = "application/json",
        @Path("event_id") event_id: Int,
        @Query("token") token: String,
    )
}