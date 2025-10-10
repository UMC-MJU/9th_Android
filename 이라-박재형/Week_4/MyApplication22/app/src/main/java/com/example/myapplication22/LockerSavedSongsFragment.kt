package com.example.myapplication22

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication22.databinding.FragmentLockerSavedSongsBinding
import com.example.myapplication22.databinding.ItemLockerSongBinding

class LockerSavedSongsFragment : Fragment() {

    private var _binding: FragmentLockerSavedSongsBinding? = null
    private val binding get() = _binding!!

    private lateinit var savedSongsAdapter: SavedSongAdapter
    private val savedSongs = mutableListOf<Track>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLockerSavedSongsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSongList()
        setupRecyclerView()
        loadSavedSongs()
    }

    private fun initSongList() {
        if (savedSongs.isEmpty()) {
            // Assuming the Track constructor is (id, title, artist, isPlaying)
            savedSongs.addAll(listOf(
                Track("01", "노래 이름", "가수 이름", false),
                Track("02", "라일락", "아이유 (IU)", false),
                Track("03", "Next Level", "aespa", false)
            ))
        }
    }

    private fun setupRecyclerView() {
        savedSongsAdapter = SavedSongAdapter(
            onItemClicked = { track ->
                val intent = Intent(activity, SongActivity::class.java).apply {
                    putExtra(MainActivity.EXTRA_SONG_TITLE, track.title)
                    putExtra(MainActivity.EXTRA_SONG_ARTIST, track.artist)
                    putExtra(SongActivity.EXTRA_ALBUM_COVER_RES_ID, R.drawable.img_album_exp)
                }
                startActivity(intent)
            },
            onMoreClicked = { track -> removeSong(track) },
            // [THE FIX] Replaced the confusing play button logic with a clear and robust implementation.
            onPlayButtonClicked = { clickedTrack ->
                // When one song is played, all others must be stopped.
                savedSongs.forEach { track ->
                    if (track.id != clickedTrack.id) {
                        track.isPlaying = false
                    }
                }
                // Then, toggle the state of the song that was actually clicked.
                clickedTrack.isPlaying = !clickedTrack.isPlaying
                loadSavedSongs()
            }
        )

        binding.lockerSavedSongsRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = savedSongsAdapter
        }
    }

    private fun loadSavedSongs() {
        savedSongsAdapter.submitList(savedSongs.toList())
        updateEmptyViewVisibility(savedSongs.isEmpty())
    }
	
    private fun removeSong(track: Track) {
        savedSongs.remove(track)
        loadSavedSongs()
    }

    private fun updateEmptyViewVisibility(isListEmpty: Boolean) {
        binding.lockerSavedSongsRv.visibility = if (isListEmpty) View.GONE else View.VISIBLE
        binding.lockerSavedSongsEmptyTv.visibility = if (isListEmpty) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class SavedSongAdapter(
    private val onItemClicked: (Track) -> Unit,
    private val onMoreClicked: (Track) -> Unit,
    private val onPlayButtonClicked: (Track) -> Unit
) : ListAdapter<Track, SavedSongAdapter.ViewHolder>(TrackDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLockerSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val track = getItem(position)
        holder.bind(track)
        holder.itemView.setOnClickListener { onItemClicked(track) }
        holder.binding.itemLockerSongMoreBtn.setOnClickListener { onMoreClicked(track) }
        holder.binding.itemLockerSongPlayBtn.setOnClickListener { onPlayButtonClicked(track) }
    }

    class ViewHolder(val binding: ItemLockerSongBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(track: Track) {
            binding.itemLockerSongCoverIv.setImageResource(R.drawable.img_album_exp)
            binding.itemLockerSongTitleTv.text = track.title
            binding.itemLockerSongArtistTv.text = track.artist
            
            if (track.isPlaying) {
                binding.itemLockerSongPlayBtn.setImageResource(android.R.drawable.ic_media_pause)
            } else {
                binding.itemLockerSongPlayBtn.setImageResource(android.R.drawable.ic_media_play)
            }
        }
    }

    // [THE FIX] This is the critical bug fix.
    class TrackDiffCallback : DiffUtil.ItemCallback<Track>() {
        override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
            // Items are the same if they have the same unique ID. This is stable.
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
            // Contents are the same if the whole object is equal. 
            // This works perfectly for a data class.
            return oldItem == newItem
        }
    }
}
