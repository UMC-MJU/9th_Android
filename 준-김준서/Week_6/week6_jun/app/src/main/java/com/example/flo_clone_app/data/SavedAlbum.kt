package com.example.flo_clone_app.data

data class SavedAlbum(
    val id: Long,
    val title: String,
    val artist: String,
    val savedAt: String,
    val genre: String,
    val coverUrl: String? = null,
    val isPlaying: Boolean = false
)