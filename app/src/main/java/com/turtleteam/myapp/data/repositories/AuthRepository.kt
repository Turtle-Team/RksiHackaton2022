package com.turtleteam.myapp.data.repositories

import com.turtleteam.myapp.data.api.AuthService
import com.turtleteam.myapp.data.model.users.UserId
import com.turtleteam.myapp.data.preferences.UserPreferences
import com.turtleteam.myapp.data.wrapper.NetworkResultWrapper
import com.turtleteam.myapp.data.wrapper.Result
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: AuthService,
    private val userPrefs: UserPreferences,
) {

    suspend fun register(
        fio: String,
        post: String,
        organization: String,
        status: String,
        email: String,
        password: String,
    ): Result<UserId> = NetworkResultWrapper.wrapWithResult {
        apiService.register(fio,
            post,
            organization,
            status,
            email,
            password)
    }

    suspend fun login(
        fio: String,
        password: String,
    ): Result<UserId> = NetworkResultWrapper.wrapWithResult {
        apiService.login(fio, password)
    }
}