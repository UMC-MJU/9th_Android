package com.example.myapplication22

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MusicViewModel : ViewModel() {
    // Existing LiveData
    val selectedSongTitle = MutableLiveData<String>()
    val selectedSongArtist = MutableLiveData<String>()

    // New LiveData for Album Cover Resource ID
    private val _selectedSongCoverResId = MutableLiveData<Int>()
    val selectedSongCoverResId: LiveData<Int> = _selectedSongCoverResId

    // LiveData for the selected album
    private val _selectedAlbum = MutableLiveData<Album?>()
    val selectedAlbum: LiveData<Album?> = _selectedAlbum

    // LiveData for the currently playing song
    private val _nowPlaying = MutableLiveData<Track?>()
    val nowPlaying: LiveData<Track?> = _nowPlaying

    // Optional: Helper function to update the cover resource ID
    // You would call this from wherever you determine the current song's cover.
    fun updateSelectedSongCover(coverResId: Int) {
        _selectedSongCoverResId.value = coverResId
    }

    // Function to update the selected album
    fun selectAlbum(album: Album) {
        _selectedAlbum.value = album
    }

    fun setPlayingSong(track: Track) {
        _nowPlaying.value = track
        selectedSongTitle.value = track.title
        selectedSongArtist.value = track.artist
    }
}