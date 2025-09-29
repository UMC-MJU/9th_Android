package com.example.myapplication22

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication22.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySongBinding
    private var receivedSongTitle: String? = null
    private var receivedSongArtist: String? = null
    private var receivedAlbumCoverResId: Int = 0 // Variable to store album cover resource ID

    // Companion object for Intent extra keys
    companion object {
        const val EXTRA_ALBUM_COVER_RES_ID = "albumCoverResId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve data from MainActivity (or calling activity)
        receivedSongTitle = intent.getStringExtra(MainActivity.EXTRA_SONG_TITLE)
        receivedSongArtist = intent.getStringExtra(MainActivity.EXTRA_SONG_ARTIST)
        receivedAlbumCoverResId = intent.getIntExtra(EXTRA_ALBUM_COVER_RES_ID, 0) // Default to 0 if not found

        Log.d("SongActivity", "Received Title: $receivedSongTitle, Artist: $receivedSongArtist, CoverResId: $receivedAlbumCoverResId")

        // Set UI elements
        binding.songTitleTv.text = receivedSongTitle ?: "Pop Song"
        binding.songArtistTv.text = receivedSongArtist ?: "Unknown Artist"

        // Set Album Cover Image
        if (receivedAlbumCoverResId != 0) {
            binding.songAlbumArtIv.setImageResource(receivedAlbumCoverResId)
        }
        // If receivedAlbumCoverResId is 0 (or not provided),
        // the ImageView will use the src defined in activity_song.xml (e.g., img_lost_corner_placeholder)

        // Set up the back button to send result and finish
        binding.songCollapseBtn.setOnClickListener {
            sendResultAndFinish()
        }

        // Handle system back press using OnBackPressedDispatcher
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                sendResultAndFinish()
            }
        }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private fun sendResultAndFinish() {
        val resultIntent = Intent()
        val albumTitleToSend = receivedSongTitle ?: "Unknown Album Title"
        resultIntent.putExtra(MainActivity.EXTRA_ALBUM_TITLE_RESULT, albumTitleToSend)
        // Note: You might also want to send back the album cover ID if it's relevant for the calling activity
        setResult(RESULT_OK, resultIntent)
        finish()
    }
}