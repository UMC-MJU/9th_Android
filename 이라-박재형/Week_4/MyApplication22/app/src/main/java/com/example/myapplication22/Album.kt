package com.example.myapplication22

// Using Track from AlbumTracksFragment

data class Album(
    var title: String,
    var artist: String,
    var coverImg: Int,
    var tracks: List<Track>? = null // Changed from 'songs' to 'tracks' and type to Track
)
