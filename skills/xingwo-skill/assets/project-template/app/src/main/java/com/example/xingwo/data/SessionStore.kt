package com.example.xingwo.data

import android.content.Context

class SessionStore(context: Context) {
    private val prefs = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun isLoggedIn(): Boolean = prefs.getBoolean(KEY_LOGGED_IN, false)

    fun setLoggedIn(loggedIn: Boolean) {
        prefs.edit().putBoolean(KEY_LOGGED_IN, loggedIn).apply()
    }

    companion object {
        private const val PREFS_NAME = "xingwo_session"
        private const val KEY_LOGGED_IN = "logged_in"
    }
}
