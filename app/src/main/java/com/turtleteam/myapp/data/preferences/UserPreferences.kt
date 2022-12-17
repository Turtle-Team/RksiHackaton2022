package com.turtleteam.myapp.data.preferences

import android.content.Context
import android.content.SharedPreferences

class UserPreferences(context: Context) {

    companion object {
        private const val PREFS_NAME = "PREFERENCE"
        private const val USER_ID = "savedusertoken"
        private const val EVENT_ID = "eventid"

    }

    private var preferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getUserToken(token: String) = preferences.edit().putString(USER_ID, token).apply()

    fun setUserToken(): String? = preferences.getString(USER_ID, "0")

    fun getEventId(id: Int) = preferences.edit().putInt(EVENT_ID, id).apply()

    fun setEventId(): Int = preferences.getInt(EVENT_ID, 0)
}