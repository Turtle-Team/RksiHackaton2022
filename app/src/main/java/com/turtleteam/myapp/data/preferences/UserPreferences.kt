package com.turtleteam.myapp.data.preferences

import android.content.Context
import android.content.SharedPreferences

class UserPreferences(context: Context) {

    companion object {
        private const val PREFS_NAME = "PREFERENCE"
        private const val USER_ID = "userid"
    }

    private var preferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getUserId(id: Int) = preferences.edit().putInt(USER_ID, id).apply()

    fun setUserId(): Int = preferences.getInt(USER_ID, 0)
}