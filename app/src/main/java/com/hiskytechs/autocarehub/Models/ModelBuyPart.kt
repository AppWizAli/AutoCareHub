package com.hiskytechs.autocarehub.Models

class ModelBuyPart
    ( var workshopDocId: String = "",
      var userId: String = "",
      var userAddress: String = "",
      var userIssue: String = "",
      var requestType: String = "", // pending or approved
      var requestDocId: String = "" ,
      var userName: String = "",
      var email: String = "",
      var phoneNumber: String = "",
      var partId: String = "",
      var partName: String = "",
      var partDescription: String = "",
      var partPrice: Double = 0.0,
      var partAvailableQuantity: Int = 0,
      var partImage: String = "",
      var partBrand: String = "",
      var partType: String = ""){

}