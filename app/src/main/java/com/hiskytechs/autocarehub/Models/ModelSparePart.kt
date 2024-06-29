package com.hiskytechs.autocarehub.Models

class ModelSparePart(
    var workshopId: String = "",
    var partId: String = "",
    var partName: String = "",
    var partDescription: String = "",
    var partPrice: Double = 0.0,
    var partAvailableQuantity: Int = 0,
    var partImage: String = "",
    var partBrand: String = "",
    var partType: String = ""
)
