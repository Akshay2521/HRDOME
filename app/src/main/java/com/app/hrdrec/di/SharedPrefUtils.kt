package com.app.hrdrec.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit
import com.app.hrdrec.login.model.userData
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefUtils @Inject constructor(@ApplicationContext context: Context) {
    private val sharedPreferences = context.getSharedPreferences("MySharedPref", MODE_PRIVATE)
    private val USERINFO = "user_info"
    fun saveString(key: String, value: String) {
        sharedPreferences.edit { putString(key, value) }
    }

    fun saveInt(key: String, value: Int) {
        sharedPreferences.edit { putInt(key, value) }
    }

    fun getInt(key: String) = sharedPreferences.getInt(key, 1)

    fun saveBoolean(key: String, value: Boolean) {
        sharedPreferences.edit { putBoolean(key, value) }
    }

    /*
  *  Save User Information and token
  *  @param  data :
  * */
    fun saveUserInfo(data: userData) {
        sharedPreferences.edit { putString(USERINFO, Gson().toJson(data)) }

    }

    fun getUserInfo(): userData? {

        val info = sharedPreferences!!.getString(USERINFO, "")
        return if (info!!.isEmpty()) {
            null
        } else {
            Gson().fromJson(info, userData::class.java)
        }
    }

    fun getSavedString(key: String) = sharedPreferences.getString(key, "")

    fun getSavedBoolean(key: String) = sharedPreferences.getBoolean(key, false)

    fun clearPreference() {
        sharedPreferences.edit { clear() }
    }
}