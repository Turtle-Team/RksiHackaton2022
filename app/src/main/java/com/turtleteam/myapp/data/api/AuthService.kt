package com.turtleteam.myapp.data.api

import com.turtleteam.myapp.data.model.users.UserId
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {

    @POST
    suspend fun register(
        @Query("fio") fio: String,
        @Query("post") post: String,
        @Query("organization") organization: String,
        @Query("status") status: String,
        @Query("email") email: String,
        @Query("password") password: String,
        ): UserId

    @GET
    suspend fun login(
        @Query("fio") fio: String,
        @Query("password") password: String,
    ): UserId
}