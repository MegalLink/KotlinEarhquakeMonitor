package com.example.recyclerview.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.recyclerview.domain.ApiResponseStatus
import com.example.recyclerview.repository.EqRepository
import com.example.recyclerview.persistance.getDatabase
import kotlinx.coroutines.launch
import java.net.UnknownHostException

val TAG = MainViewModel::class.simpleName

// using by viewModel() this application si automatically passed to this view model
class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val db = getDatabase(application.applicationContext)
    private val eqRepository = EqRepository(db)
    val eqList = eqRepository.eqList
    private val _status =MutableLiveData<ApiResponseStatus>()
    val status: LiveData<ApiResponseStatus> = _status


    init {
        viewModelScope.launch {
            try { //this try catch is necesary when there is not internet
                _status.value=ApiResponseStatus.LOADING
                eqRepository.fetchEqList()
                _status.value=ApiResponseStatus.DONE
            } catch (e: UnknownHostException) {
                Log.d(TAG, "There is not connection to internet")
                _status.value=ApiResponseStatus.ERROR
            }
        }
    }
}
