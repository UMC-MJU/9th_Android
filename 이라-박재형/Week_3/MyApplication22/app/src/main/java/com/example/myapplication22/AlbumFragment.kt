package com.example.myapplication22

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager // For RecyclerView LayoutManager
import com.example.myapplication22.databinding.FragmentAlbumBinding

class AlbumFragment : Fragment(), AlbumSongAdapter.OnItemClickListener { // Implement OnItemClickListener

    private var _binding: FragmentAlbumBinding? = null
    private val binding get() = _binding!!

    private lateinit var albumSongAdapter: AlbumSongAdapter
    private val albumSongs = mutableListOf<AlbumSong>() // List to hold album songs

    companion object {
        private const val ARG_ALBUM_TITLE = "albumTitle"
        private const val ARG_ALBUM_ARTIST = "albumArtist"
        private const val ARG_ALBUM_COVER_RES_ID = "albumCoverResId"

        fun newInstance(title: String, artist: String, coverResId: Int): AlbumFragment {
            val fragment = AlbumFragment()
            val args = Bundle()
            args.putString(ARG_ALBUM_TITLE, title)
            args.putString(ARG_ALBUM_ARTIST, artist)
            args.putInt(ARG_ALBUM_COVER_RES_ID, coverResId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Toolbar 설정
        binding.albumToolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
        // TODO: Toolbar의 메뉴 아이템 클릭 리스너 설정 (onOptionsItemSelected)
        // This will involve setting up listeners for R.id.action_album_like and R.id.action_album_more from the new menu

        // Bundle로부터 데이터 가져오기 및 UI 업데이트
        val albumTitle = arguments?.getString(ARG_ALBUM_TITLE)
        val albumArtist = arguments?.getString(ARG_ALBUM_ARTIST)
        val albumCoverResId = arguments?.getInt(ARG_ALBUM_COVER_RES_ID)

        binding.albumTitleTv.text = albumTitle ?: "Unknown Album"
        binding.albumArtistTv.text = albumArtist ?: "Unknown Artist"
        if (albumCoverResId != null && albumCoverResId != 0) {
            binding.albumCoverIv.setImageResource(albumCoverResId)
        } else {
            binding.albumCoverIv.setImageResource(R.drawable.img_lost_corner_placeholder)
        }
        // binding.albumToolbar.title = albumTitle ?: "앨범" // Removed, title is now in albumTitleTv

        // Setup RecyclerView for album tracks
        setupAlbumTracksRecyclerView(albumTitle, albumArtist)

        // The following click listeners are removed as the buttons albumPlayBtn,
        // albumShuffleBtn, and albumLikeBtn (in its old position) no longer exist in the new layout.
        // Functionality for "Like" is now in the toolbar menu.
        // Functionality for "Play album" might be implicitly the first track, or a new button if desired.
        // binding.albumPlayBtn.setOnClickListener { ... }
        // binding.albumShuffleBtn.setOnClickListener { ... }
        // binding.albumLikeBtn.setOnClickListener { ... }

        // TODO: Add click listener for binding.albumMyTasteMixBtn if needed
        // TODO: Setup binding.albumTabs with a ViewPager2 for tab content if needed
    }

    private fun setupAlbumTracksRecyclerView(defaultAlbumTitle: String?, defaultArtistName: String?) {
        // Sample data - replace with actual data source (e.g., from ViewModel or database)
        albumSongs.clear() // Clear previous data
        albumSongs.add(AlbumSong(1, defaultAlbumTitle ?: "라일락", defaultArtistName ?: "아이유"))
        albumSongs.add(AlbumSong(2, "Flu", defaultArtistName ?: "아이유"))
        albumSongs.add(AlbumSong(3, "Coin", defaultArtistName ?: "아이유"))
        albumSongs.add(AlbumSong(4, "봄 안녕 봄", defaultArtistName ?: "아이유"))
        albumSongs.add(AlbumSong(5, "Celebrity", defaultArtistName ?: "아이유"))
        albumSongs.add(AlbumSong(6, "돌림노래 (Feat. DEAN)", defaultArtistName ?: "아이유"))
        albumSongs.add(AlbumSong(7, "빈 컵 (Empty Cup)", defaultArtistName ?: "아이유"))
        albumSongs.add(AlbumSong(8, "아이와 나의 바다", defaultArtistName ?: "아이유"))
        albumSongs.add(AlbumSong(9, "어푸 (Ah puh)", defaultArtistName ?: "아이유"))
        albumSongs.add(AlbumSong(10, "에필로그", defaultArtistName ?: "아이유"))
        // Add more songs as needed

        albumSongAdapter = AlbumSongAdapter(albumSongs)
        albumSongAdapter.setOnItemClickListener(this) // Set the fragment as the listener

        binding.albumTracksRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = albumSongAdapter
        }
    }

    // Implementation for AlbumSongAdapter.OnItemClickListener
    override fun onItemClick(song: AlbumSong, position: Int) {
        // This is for the item click on the song in the RecyclerView (if the whole item is clickable)
        // The new item_album_song.xml has a specific play button (item_album_song_play_btn)
        // You might want to move play logic to a listener for that specific button in the adapter.
        Toast.makeText(context, "재생 (아이템 클릭): ${song.title}", Toast.LENGTH_SHORT).show()
    }

    override fun onMoreClick(song: AlbumSong, position: Int) {
        // This is for the "more" button on each song item in the RecyclerView
        Toast.makeText(context, "더보기: ${song.title}", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}