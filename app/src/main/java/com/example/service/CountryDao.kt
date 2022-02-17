package com.example.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.model.Country

/**
 * Created by aarslan on 16/02/2022.
 */
@Dao
interface CountryDao {
    //vararg : Country country ... in Java
    /*used in a function's declaration as a parameter.
     These dots allow zero to multiple arguments to be passed when the function is called.
     The three dots are also known as var args .
     */
    @Insert
    suspend fun insertAll(vararg countries : Country) : List<Long>

    @Query("SELECT * from country")
    suspend fun getAllCountries() : List<Country>

    @Query("SELECT * from country WHERE uuid = :countryId")
    suspend fun getCountry(countryId : Int ) : Country

    @Query("DELETE from country")
    suspend fun deleteAllCountries()

}