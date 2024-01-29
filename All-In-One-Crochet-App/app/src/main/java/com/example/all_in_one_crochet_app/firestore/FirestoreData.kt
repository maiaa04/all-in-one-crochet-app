package com.example.all_in_one_crochet_app.firestore

data class FirestoreData(
//    var email: String = "",
    var mainCounterRows: String = "",
    var secCountersVisibility: List<Int> = listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
    var secCountersNames: List<String> = listOf("", "", "", "", "", "", "", "", "", ""),
    var secCountersRows: List<String> = listOf("", "", "", "", "", "", "", "", "", "")
)