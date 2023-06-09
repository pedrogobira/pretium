package com.pedrogobira.pretium.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pedrogobira.pretium.service.model.ServiceModel
import com.pedrogobira.pretium.service.repository.ServiceRepository

class ServiceFormViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ServiceRepository.getInstance(application.applicationContext)

    private var _save = MutableLiveData<Boolean>()
    val save: LiveData<Boolean> = _save

    private var _service = MutableLiveData<ServiceModel>()
    val service: LiveData<ServiceModel> = _service

    fun save(model: ServiceModel) {
        if (model.id == 0) {
            _save.value = repository.save(model)
        } else {
            _save.value = repository.update(model)
        }
    }

    fun load(id: Int) {
        _service.value = repository.get(id)
    }

}