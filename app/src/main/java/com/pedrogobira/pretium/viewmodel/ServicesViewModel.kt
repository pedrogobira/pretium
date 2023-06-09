package com.pedrogobira.pretium.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pedrogobira.pretium.service.model.ServiceModel
import com.pedrogobira.pretium.service.repository.ServiceRepository

class ServicesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ServiceRepository.getInstance(application.applicationContext)

    private val _list = MutableLiveData<List<ServiceModel>>()
    val list: LiveData<List<ServiceModel>> = _list

    fun getAll() {
        _list.value = repository.getAll()
    }

    fun getAbsent() {
        //_list.value = repository.getAbsent()
    }

    fun getPresent() {
        //_list.value = repository.getPresent()
    }

    fun delete(id: Int) {
        repository.delete(id)
    }

}