package com.example.myapplication22

data class Track(
    val id: String, // Unique identifier for the track
    val title: String,
    val artist: String,
    var isPlaying: Boolean = false
)
