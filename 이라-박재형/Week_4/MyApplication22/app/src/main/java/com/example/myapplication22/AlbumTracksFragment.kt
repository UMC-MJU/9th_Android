package com.example.myapplication22

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication22.databinding.FragmentAlbumTracksBinding
import com.example.myapplication22.databinding.ItemAlbumSongBinding

class AlbumTracksFragment : Fragment() {

    private var _binding: FragmentAlbumTracksBinding? = null
    private val binding get() = _binding!!

    private val musicViewModel: MusicViewModel by activityViewModels()
    private lateinit var trackAdapter: TrackAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumTracksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // The adapter is now initialized with three different click handlers
        trackAdapter = TrackAdapter(
            // 1. Click the whole item to open the song activity
            onItemClicked = { clickedTrack ->
                val album = musicViewModel.selectedAlbum.value
                val coverResId = album?.coverImg ?: R.drawable.img_album_exp
                val intent = Intent(activity, SongActivity::class.java).apply {
                    putExtra(MainActivity.EXTRA_SONG_TITLE, clickedTrack.title)
                    putExtra(MainActivity.EXTRA_SONG_ARTIST, clickedTrack.artist)
                    putExtra(SongActivity.EXTRA_ALBUM_COVER_RES_ID, coverResId)
                }
                startActivity(intent)
            },
            // 2. Click the play button to toggle play/pause state
            onPlayClicked = { clickedTrack ->
                // Create a new list with updated states for an immutable update
                val newList = trackAdapter.currentList.map { track ->
                    // Create a copy of each track.
                    // If it's the clicked track, toggle its play state.
                    // If it's another track, ensure it's not playing.
                    track.copy(isPlaying = if (track.id == clickedTrack.id) !track.isPlaying else false)
                }
                // Submit the new list. DiffUtil will find the changes and animate them.
                trackAdapter.submitList(newList)
            },
            // 3. Click the more button for other options
            onMoreClicked = { clickedTrack ->
                // Placeholder for "more" button functionality
                Toast.makeText(context, "'${clickedTrack.title}' more options", Toast.LENGTH_SHORT).show()
            }
        )
        binding.albumTracksListRv.layoutManager = LinearLayoutManager(context)
        binding.albumTracksListRv.adapter = trackAdapter

        musicViewModel.selectedAlbum.observe(viewLifecycleOwner) { album ->
            album?.let {
                binding.albumTitleTv.text = it.title
                binding.albumArtistTv.text = it.artist
                binding.albumCoverIv.setImageResource(it.coverImg)

                val tracks = it.tracks ?: emptyList()
                Log.d("AlbumTracksFragment", "Updating UI for album: ${it.title} with ${tracks.size} tracks")
                // Submit the list from the ViewModel to the adapter
                trackAdapter.submitList(tracks)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): AlbumTracksFragment {
            return AlbumTracksFragment()
        }
    }
}

// The Adapter is updated to handle multiple, specific click listeners
class TrackAdapter(
    private val onItemClicked: (Track) -> Unit,
    private val onPlayClicked: (Track) -> Unit,
    private val onMoreClicked: (Track) -> Unit
) : ListAdapter<Track, TrackAdapter.ViewHolder>(TrackDiffCallback) {

    // ViewHolder now binds the state and click listeners
    class ViewHolder(val binding: ItemAlbumSongBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(track: Track) {
            binding.itemAlbumSongTrackNumberTv.text = track.id
            binding.itemAlbumSongTitleTv.text = track.title
            binding.itemAlbumSongArtistTv.text = track.artist

            // Update the play button's icon based on the track's playing state
            if (track.isPlaying) {
                binding.itemAlbumSongPlayBtn.setImageResource(android.R.drawable.ic_media_pause)
            } else {
                binding.itemAlbumSongPlayBtn.setImageResource(android.R.drawable.ic_media_play)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAlbumSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val track = getItem(position)
        holder.bind(track)

        // Set individual click listeners for the item, play button, and more button
        holder.itemView.setOnClickListener { onItemClicked(track) }
        holder.binding.itemAlbumSongPlayBtn.setOnClickListener { onPlayClicked(track) }
        holder.binding.itemAlbumSongMoreBtn.setOnClickListener { onMoreClicked(track) }
    }

    // Moved the DiffUtil.ItemCallback to a companion object for cleanliness
    companion object TrackDiffCallback : DiffUtil.ItemCallback<Track>() {
        override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem == newItem
        }
    }
}
