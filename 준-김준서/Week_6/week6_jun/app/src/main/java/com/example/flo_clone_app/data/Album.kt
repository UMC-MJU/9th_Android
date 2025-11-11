package com.example.flo_clone_app.data

// build.gradle(Module): plugins { id("kotlin-parcelize") } 필요


data class Album(
    val title: String = "",
    val singer : String = "",
    var coverImg: Int? = null,
    var songs : ArrayList<Song>? = null
)

