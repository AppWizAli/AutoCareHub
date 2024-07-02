package com.example.carrepairapp.model

import com.google.firebase.Timestamp

data class ModelUser(
    var userID: String = "",
    var workshopOwnerID: String="",
    var userName: String = "",
    var email: String = "",
    var password: String = "",
    var cpassword: String = "",
    var phoneNumber: String = "",
    var address: String = "",
    var profilePictureUrl: String = "",
    var userType: String = "",
    var carDetails: String? = "",
    var workshopDetails: String? = "",
    var registrationDate:Timestamp= Timestamp.now(),
    var isActive: Boolean = true
)
