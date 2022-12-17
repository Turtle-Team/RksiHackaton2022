package com.turtleteam.myapp.data.repositories

import com.turtleteam.myapp.data.api.AuthService
import com.turtleteam.myapp.data.model.profile.Profile
import com.turtleteam.myapp.data.model.users.AuthRequestBody
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
        userModel: AuthRequestBody,
    ): Result<UserId> = NetworkResultWrapper.wrapWithResult {
        apiService.register(userModel = userModel)
    }

    suspend fun login(
        email: String,
        password: String,
    ): Result<UserId> = NetworkResultWrapper.wrapWithResult {
        apiService.login(email, password)
    }

    suspend fun getUser(token: String): Profile = apiService.getUser(token)
}