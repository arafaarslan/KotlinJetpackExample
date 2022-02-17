package com.example.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.model.Country

/**
 * Created by aarslan on 16/02/2022.
 */
@Database(entities = arrayOf(Country::class), version = 1,exportSchema = false)
abstract class CountryDb : RoomDatabase(){

    abstract fun countryDao() : CountryDao

    companion object{
        //Volatile : farkli threadlere gorunur hale getirir.
        //Coroutine ile farkli threadlerde calistirilacak
        @Volatile private var instance : CountryDb? = null

        private val lock = Any()

        operator fun invoke(context : Context) = instance ?: synchronized(lock){
            instance ?: makeDb(context).also {
                instance = it
            }
        }

        private fun makeDb(context : Context) = Room.databaseBuilder(
            context.applicationContext,CountryDb::class.java,"countryDatabase").build()
    }

}