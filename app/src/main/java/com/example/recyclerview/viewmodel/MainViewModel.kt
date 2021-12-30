package com.example.recyclerview.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerview.repository.EqRepository
import com.example.recyclerview.domain.Earthquake
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _eqList = MutableLiveData<MutableList<Earthquake>>()
    val eqList: LiveData<MutableList<Earthquake>> = _eqList

    private val eqRepository= EqRepository()

    init {
        viewModelScope.launch {
            val eqList=eqRepository.fetchEqList()
            _eqList.postValue(eqList)
        }
    }
}