package com.example.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.Preference
import androidx.preference.PreferenceManager

/**
 * Created by aarslan on 17/02/2022.
 */
class CustomSharedPreferences {

    companion object {
        private val PREFERENCE_TIME = "time"

        private var sharedPreferences: SharedPreferences? = null

        @Volatile
        private var instance: CustomSharedPreferences? = null

        private val lock = Any()

        operator fun invoke(context: Context): CustomSharedPreferences =
            instance ?: synchronized(lock) {
                instance ?: makeCustomSharedPreferences(context).also {
                    instance = it
                }
            }

        private fun makeCustomSharedPreferences(context: Context): CustomSharedPreferences {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPreferences()
        }

    }

    fun saveTime(time : Long){
        sharedPreferences?.edit(commit = true){
            putLong(PREFERENCE_TIME,time)
        }
    }

    fun getTime() = sharedPreferences?.getLong(PREFERENCE_TIME,0)
}