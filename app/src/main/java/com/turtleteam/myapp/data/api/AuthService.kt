
package com.turtleteam.myapp.data.api

import com.turtleteam.myapp.data.model.profile.Profile
import com.turtleteam.myapp.data.model.users.AuthRequestBody
import com.turtleteam.myapp.data.model.users.UserId
import retrofit2.http.*

interface AuthService {

    @POST("auth")
    suspend fun register(
        @Header("Content-Type") type: String = "application/json",
        @Body userModel: AuthRequestBody
    ): UserId

    @GET("auth")
    suspend fun login(
        @Query("email") email: String,
        @Query("password") password: String,
    ): UserId

    @GET("auth/{token}")
    suspend fun getUser(
        @Path("token") token: String
    ): Profile
}