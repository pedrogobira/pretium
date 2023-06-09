package com.pedrogobira.pretium.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pedrogobira.pretium.service.model.ServiceModel
import com.pedrogobira.pretium.service.repository.ServiceRepository
import java.time.LocalDate

class ServicesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ServiceRepository.getInstance(application.applicationContext)

    private val _list = MutableLiveData<List<ServiceModel>>()
    val list: LiveData<List<ServiceModel>> = _list
    private val _totalRevenue = MutableLiveData<Double>()
    val totalRevenue: LiveData<Double> = _totalRevenue

    fun getAll() {
        _list.value = repository.getAll()
    }

    fun calculateTotalPrice() {
        val currentDate = LocalDate.now()
        val services = repository.getByMonthAndYear(currentDate.monthValue, currentDate.year)
        var totalPrice: Double = 0.0
        for (service in services) {
            totalPrice += (service.hours.toDouble() + service.minutes.toDouble() / 60.0) * service.pricePerHour
        }
        _totalRevenue.value = totalPrice
    }

    fun delete(id: Int) {
        repository.delete(id)
        calculateTotalPrice()
    }

}