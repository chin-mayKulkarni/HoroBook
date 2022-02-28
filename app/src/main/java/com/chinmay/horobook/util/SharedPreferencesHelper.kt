package com.chinmay.horobook.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class SharedPreferencesHelper {
    companion object{
        private const val PREF_TIME = "Pref time"
        private var prefs : SharedPreferences? = null

        @Volatile private var instance : SharedPreferencesHelper? =null
        private var LOCK = Any()

        operator fun invoke(context: Context): SharedPreferencesHelper = instance?: synchronized(LOCK){
            instance?: buildHelper(context).also{
                instance = it
            }
        }

        private fun buildHelper(context: Context): SharedPreferencesHelper{
            prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return SharedPreferencesHelper()
        }


    }

    fun saveUpdatedTime(time: Long){
        prefs?.edit(commit= true) {putLong(PREF_TIME, time)}
    }

    fun storeDataInSharedPref(auth_key: String){
        prefs?.edit(commit = true) {putString("auth_key", auth_key)}
    }

    fun getAuthKey() = prefs?.getString("auth_key", " ")

    fun getUpdatedTime() = prefs?.getLong(PREF_TIME, 0)
}