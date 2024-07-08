package com.hiskytechs.autocarehub.Models

data class ModelRequest(
    var workshopName: String = "",
    var workshopAddress: String = "",
    var userId: String = "",
    var userAddress: String = "",
    var userIssue: String = "",
    var requestType: String = "", // pending or approved
    var requestDocId: String = "" // to store Firestore document ID
)
