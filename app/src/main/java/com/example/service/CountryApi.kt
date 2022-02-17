package com.example.service

import com.example.model.Country
import io.reactivex.Single
import retrofit2.http.GET

/**
 * Created by aarslan on 16/02/2022.
 */
interface CountryApi {
    //GET POST
    //https://raw.githubusercontent.com/atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json

    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun getCountries() : Single<List<Country>>


}