package com.example.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.model.Country
import com.example.service.CountryApiService
import com.example.service.CountryDb
import com.example.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

/**
 * Created by aarslan on 16/02/2022.
 */
class FeedViewModel(application: Application) : BaseViewModel(application) {

    private val countryApiService = CountryApiService()

    /*kullan at*/
    private val disposable = CompositeDisposable()

    private var customPreference = CustomSharedPreferences(getApplication())

    private var refreshTimeMinute = 10 * 60 * 1000 * 1000 * 1000L

    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData() {

        val updateTime = customPreference.getTime();

        if (updateTime != null && updateTime != 0L && (System.nanoTime() - updateTime) < refreshTimeMinute) {
            getDataFromSqlite()
        } else {
            getDataFromAPI()
        }

        // getDataFromAPI()

        //val country1 = Country("Turkey","Ankara","Asia","TRY","Turkish","www.asd.com")
        //val country2 = Country("France","Paris","Europa","","French","www.asd.com")
        //val country3 = Country("Germany","Berlin","Europa","","Germany","www.asd.com")

        //val countryList = arrayListOf<Country>(country1,country2,country3)
        //countries.value = countryList;
        //countryError.value = false
        //countryLoading.value = false

    }

    fun refreshDataFromAPI() {

        getDataFromAPI()

    }

    private fun getDataFromSqlite(){
        countryLoading.value = true
        launch {
            val countries = CountryDb(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(),"Countries from Sqllite", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getDataFromAPI() {
        countryLoading.value = true

        disposable.add(
            countryApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>() {
                    override fun onSuccess(t: List<Country>) {
                        storeInSqlLite(t)
                        Toast.makeText(getApplication(),"Countries from API ", Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(e: Throwable) {
                        countryError.value = true
                        countryLoading.value = false
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun showCountries(countryList: List<Country>) {
        countries.value = countryList
        countryError.value = false
        countryLoading.value = false
    }

    private fun storeInSqlLite(countryList: List<Country>) {
        launch {
            val dao = CountryDb(getApplication()).countryDao()
            val listLong =
                dao.insertAll(*countryList.toTypedArray())// tek tek individual sekilde verir kotlin specific
            var i = 0;
            while (i < countryList.size) {
                countryList[i].uuid = listLong[i].toInt()
                i++
            }

            showCountries(countryList)
        }

        customPreference.saveTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}