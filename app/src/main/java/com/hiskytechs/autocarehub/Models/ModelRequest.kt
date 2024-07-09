package com.hiskytechs.autocarehub.Models

import com.google.firebase.Timestamp

data class ModelRequest(
    var workshopDocId: String = "",
    var userId: String = "",
    var userAddress: String = "",
    var userIssue: String = "",
    var requestType: String = "", // pending or approved
    var requestDocId: String = "" ,
    var userName: String = "",
    var email: String = "",
    var phoneNumber: String = ""
)
