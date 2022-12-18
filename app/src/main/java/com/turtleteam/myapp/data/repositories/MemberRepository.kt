package com.turtleteam.myapp.data.repositories

import com.turtleteam.myapp.data.api.MemberService
import com.turtleteam.myapp.data.model.member.MemberModel
import com.turtleteam.myapp.data.wrapper.NetworkResultWrapper
import javax.inject.Inject

class MemberRepository @Inject constructor(private val apiService: MemberService) {

    suspend fun createMember(event_id: Int, token: String) =
        kotlin.runCatching { apiService.createMember(event_id = event_id, token = token) }

    suspend fun getMembers(event_id: Int, token: String):com.turtleteam.myapp.data.wrapper.Result<MemberModel> = NetworkResultWrapper.wrapWithResult { apiService.getMembers(event_id = event_id, token = token) }
}