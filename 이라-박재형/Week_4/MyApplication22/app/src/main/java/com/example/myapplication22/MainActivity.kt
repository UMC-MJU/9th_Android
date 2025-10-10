package com.example.myapplication22

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.myapplication22.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val musicViewModel: MusicViewModel by viewModels()

    private val songActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val albumTitle = result.data?.getStringExtra(EXTRA_ALBUM_TITLE_RESULT)
            if (albumTitle != null) {
                Toast.makeText(this, "Album: $albumTitle", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.fragment_container, HomeFragment())
            }
        }

        musicViewModel.selectedSongTitle.observe(this) {
            title -> binding.miniPlayerLayout.tvTitle.text = title
        }
        musicViewModel.selectedSongArtist.observe(this) {
            artist -> binding.miniPlayerLayout.tvArtist.text = artist
        }

        binding.miniPlayerLayout.root.setOnClickListener {
            val intent = Intent(this, SongActivity::class.java)
            // Pass current song details to SongActivity
            intent.putExtra(EXTRA_SONG_TITLE, musicViewModel.selectedSongTitle.value)
            intent.putExtra(EXTRA_SONG_ARTIST, musicViewModel.selectedSongArtist.value)
            // Pass the album cover resource ID for the current song
            // Assuming musicViewModel holds this. If not, you need to get it from your data source.
            // For example, if your 'Album' object for the mini-player song is accessible:
            // intent.putExtra(SongActivity.EXTRA_ALBUM_COVER_RES_ID, currentAlbumCoverResId)
            // As a placeholder, let's use a default if not readily available
            val currentAlbumCoverResId = musicViewModel.selectedSongCoverResId.value ?: R.drawable.img_album_exp2 // Default or from ViewModel
            intent.putExtra(SongActivity.EXTRA_ALBUM_COVER_RES_ID, currentAlbumCoverResId)
            songActivityLauncher.launch(intent)
        }


        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    swapFragment(HomeFragment())
                    true
                }
                R.id.lookFragment -> {
                    swapFragment(LookFragment())
                    true
                }
                R.id.searchFragment -> {
                    swapFragment(SearchFragment())
                    true
                }
                R.id.lockerFragment -> {
                    swapFragment(LockerFragment()) // Changed to LockerFragment
                    true
                }
                else -> false
            }
        }
    }

    private fun swapFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fragment_container, fragment)
        }
    }

    companion object {
        const val EXTRA_SONG_TITLE = "songTitle"
        const val EXTRA_SONG_ARTIST = "songArtist"
        const val EXTRA_ALBUM_TITLE_RESULT = "albumTitleResult" // Corrected key to match SongActivity
    }
}