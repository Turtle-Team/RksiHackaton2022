package com.turtleteam.myapp.data.repositories

import com.turtleteam.myapp.data.api.MemberService
import com.turtleteam.myapp.data.wrapper.NetworkResultWrapper
import javax.inject.Inject

class MemberRepository @Inject constructor(private val apiService: MemberService) {

    suspend fun createMember(event_id: Int, token: String) = apiService.createMember(event_id = event_id, token = token)
}