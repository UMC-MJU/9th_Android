package com.example.myapplication22

import androidx.lifecycle.LiveData // Added import
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MusicViewModel : ViewModel() {
    // Existing LiveData
    val selectedSongTitle = MutableLiveData<String>()
    val selectedSongArtist = MutableLiveData<String>()

    // New LiveData for Album Cover Resource ID
    private val _selectedSongCoverResId = MutableLiveData<Int>()
    val selectedSongCoverResId: LiveData<Int> = _selectedSongCoverResId

    // Optional: Helper function to update the cover resource ID
    // You would call this from wherever you determine the current song's cover.
    fun updateSelectedSongCover(coverResId: Int) {
        _selectedSongCoverResId.value = coverResId
    }
}