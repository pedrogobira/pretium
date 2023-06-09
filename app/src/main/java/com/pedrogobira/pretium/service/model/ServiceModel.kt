package com.pedrogobira.pretium.service.model

data class ServiceModel(
    val id: Int,
    var pricePerHour: Double,
    var hours: Int,
    var minutes: Int,
    var day: Int,
    var month: Int,
    var year: Int,
    var description: String
)