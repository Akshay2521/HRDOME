package com.app.hrdrec.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    fun saveLoginDetails(username: String, password: String) {
        val editor = sharedPreferences.edit()
        editor.putString("USERNAME", username)
        editor.putString("PASSWORD", password)
        editor.apply()
    }

    fun getSavedUsername(): String? {
        return sharedPreferences.getString("USERNAME", null)
    }

    fun getSavedPassword(): String? {
        return sharedPreferences.getString("PASSWORD", null)
    }

    fun clearSavedCredentials() {
        val editor = sharedPreferences.edit()
        editor.remove("USERNAME")
        editor.remove("PASSWORD")
        editor.apply()
    }
}
