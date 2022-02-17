package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.model.Country
import com.example.service.CountryDb
import kotlinx.coroutines.launch

/**
 * Created by aarslan on 16/02/2022.
 */
class CountryViewModel(application: Application) : BaseViewModel(application) {

    val countryLiveData = MutableLiveData<Country>()

    fun getDataFromRoom(uuid : Int){
//        val country1 = Country("Turkey","Ankara","Asia","TRY","Turkish","www.asd.com")
 //       countryLiveData.value = country1
        launch {
            val dao = CountryDb(getApplication()).countryDao()
            val country = dao.getCountry(uuid)
            countryLiveData.value = country
        }
    }
}