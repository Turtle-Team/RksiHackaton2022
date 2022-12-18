package com.turtleteam.myapp.data.api

import com.turtleteam.myapp.data.model.member.MemberModel
import retrofit2.http.*

interface MemberService {

    // Получить список участников мероприятия
    @GET("member/event/{event_id}")
    suspend fun getMembers(
        @Header("Content-Type") type: String = "application/json",
        @Path("event_id") event_id: Int,
        @Query("token") token: String,
    ): MemberModel

    // Получить все мероприятия пользователя
    @GET("member/event/me")
    suspend fun getMembersMe(
        @Header("Content-Type") type: String = "application/json",
        @Query("token") token: String,
    ): MemberModel

    // Записать пользователя на мероприятие
    @POST("member/event/{event_id}")
    suspend fun createMember(
        @Header("Content-Type") type: String = "application/json",
        @Path("event_id") event_id: Int,
        @Query("token") token: String,
    )

    // Удалить пользователя с мероприятия
    @DELETE("member/event/{event_id}")
    suspend fun deleteMember(
        @Header("Content-Type") type: String = "application/json",
        @Path("event_id") event_id: Int,
        @Query("token") token: String,
    )

    // Что бы удалить всех пользователей с мероприятия
    @DELETE("ember/event/{event_id}/truncate")
    suspend fun deleteMemberTruncate(
        @Header("Content-Type") type: String = "application/json",
        @Path("event_id") event_id: Int,
        @Query("token") token: String,
    )
}