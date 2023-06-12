package com.pedrogobira.pretium.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pedrogobira.pretium.service.constants.ServiceConstants
import com.pedrogobira.pretium.service.model.ServiceModel
import com.pedrogobira.pretium.service.repository.ServiceRepository
import java.time.LocalDate

class ServicesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ServiceRepository.getInstance(application.applicationContext)

    private val _list = MutableLiveData<List<ServiceModel>>()
    val list: LiveData<List<ServiceModel>> = _list
    private val _totalRevenue = MutableLiveData<Double>()
    val totalRevenue: LiveData<Double> = _totalRevenue
    private val _tax = MutableLiveData<Tax>()
    val tax: LiveData<Tax> = _tax

    class Tax(var value: Double, var descriptor: String, var type: String)

    fun getAll(month: Int? = null, year: Int? = null) {
        if (month != null && year != null) {
            _list.value = repository.getByMonthAndYear(month, year)
            return
        }
        _list.value = repository.getAll()
    }

    fun calculateTotalPrice(month: Int? = null, year: Int? = null) {
        val services: List<ServiceModel> = if (month == null || year == null) {
            repository.getByMonthAndYear(LocalDate.now().monthValue, LocalDate.now().year)
        } else {
            repository.getByMonthAndYear(month, year)
        }

        val totalPrice: Double = services.sumOf { service ->
            (service.hours.toDouble() + service.minutes.toDouble() / 60.0) * service.pricePerHour
        }
        _totalRevenue.value = totalPrice
    }

    fun calculateTax(totalRevenue: Double, prolabore: Double) {
        val percentage = (prolabore * 100) / totalRevenue

        val type = if (percentage >= ServiceConstants.TAX.PROLABORE_PERCENTAGE) {
            ServiceConstants.TAX.ANEXO_3
        } else {
            ServiceConstants.TAX.ANEXO_5
        }

        val tax = if (type == ServiceConstants.TAX.ANEXO_3) {
            ServiceConstants.TAX.TAXA_ANEXO_3
        } else {
            ServiceConstants.TAX.TAXA_ANEXO_5
        }

        val value = totalRevenue * (tax / 100.0)
        _tax.value = Tax(value, ServiceConstants.TAX.DESCRIPTOR, type)
    }

    fun delete(id: Int) {
        repository.delete(id)
        calculateTotalPrice()
    }
}