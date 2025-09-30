package com.example.myapplication22

import java.util.ArrayList

data class Album(
    var title: String,
    var singer: String,
    var coverImg: Int,
    var songs: ArrayList<Song>? = null
)