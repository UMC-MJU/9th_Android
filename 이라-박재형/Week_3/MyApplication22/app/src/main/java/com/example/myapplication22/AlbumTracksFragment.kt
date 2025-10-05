package com.example.myapplication22

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication22.databinding.FragmentAlbumTracksBinding
import com.example.myapplication22.databinding.ItemAlbumSongBinding

data class Track(
    val number: String,
    val title: String,
    val artist: String
)

class AlbumTracksFragment : Fragment() {

    private var _binding: FragmentAlbumTracksBinding? = null
    private val binding get() = _binding!!

    private val musicViewModel: MusicViewModel by activityViewModels()

    // private lateinit var trackAdapter: TrackAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumTracksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tracks = musicViewModel.selectedAlbum.value?.songs ?: listOf(
            Track("01", "Lady", "Kenshi Yonezu"),
            Track("02", "Spinning Globe", "Kenshi Yonezu"),
            Track("03", "Pop Song", "Kenshi Yonezu"),
            Track("04", "LOSER", "Kenshi Yonezu"),
            Track("05", "Peace Sign", "Kenshi Yonezu")
        )

        val currentAlbumCoverResId = musicViewModel.selectedAlbum.value?.coverImgResId.takeIf { it != 0 }
            ?: R.drawable.img_album_exp // Using an existing drawable as a placeholder

        Log.d("AlbumTracksFragment", "Using album cover ID: $currentAlbumCoverResId for tracks")


        val trackAdapter = TrackAdapter(tracks, currentAlbumCoverResId) { clickedTrack, coverResId ->
            val intent = Intent(activity, SongActivity::class.java).apply {
                putExtra(MainActivity.EXTRA_SONG_TITLE, clickedTrack.title)
                putExtra(MainActivity.EXTRA_SONG_ARTIST, clickedTrack.artist)
                putExtra(SongActivity.EXTRA_ALBUM_COVER_RES_ID, coverResId)
            }
            startActivity(intent)
        }

        binding.albumTracksListRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = trackAdapter
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

class TrackAdapter(
    private val tracks: List<Track>,
    private val albumCoverResId: Int,
    private val onItemClicked: (Track, Int) -> Unit
) : RecyclerView.Adapter<TrackAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAlbumSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val track = tracks[position]
        holder.bind(track)
        holder.itemView.setOnClickListener {
            onItemClicked(track, albumCoverResId)
        }
    }

    override fun getItemCount(): Int = tracks.size

    class ViewHolder(private val binding: ItemAlbumSongBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(track: Track) {
            binding.itemAlbumSongTrackNumberTv.text = track.number
            binding.itemAlbumSongTitleTv.text = track.title
            binding.itemAlbumSongArtistTv.text = track.artist
        }
    }
}
