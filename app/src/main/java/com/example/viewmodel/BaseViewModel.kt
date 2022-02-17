package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * Created by aarslan on 17/02/2022.
 */
abstract class BaseViewModel(application : Application) : AndroidViewModel(application),CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main//isini yap sonra main threade don anlamina gelir.

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}