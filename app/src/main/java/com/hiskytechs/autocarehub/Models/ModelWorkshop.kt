package com.hiskytechs.autocarehub.Models

class ModelWorkshop(
    var workshopId: String = "",

    var workshopName: String = "",
    var workshopImage: String="",
    var workshopAddress: String = "",
    var workshopLatitude: Double = 0.0,
    var workshopLongitude: Double = 0.0,
    var workshopPhoneNumber: String = "",
    var workshopEmail: String = "",
    var workshopServices: List<String> = emptyList(),
    var isAvailable: Boolean = false
)
