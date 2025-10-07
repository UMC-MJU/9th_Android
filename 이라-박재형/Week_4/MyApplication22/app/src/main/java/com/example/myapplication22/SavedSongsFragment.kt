package com.example.myapplication22

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication22.databinding.FragmentSavedSongsBinding
import com.example.myapplication22.databinding.ItemSavedSongBinding
import com.example.myapplication22.Song

class SavedSongsFragment : Fragment() {

    private var _binding: FragmentSavedSongsBinding? = null
    private val binding get() = _binding!!

    private lateinit var savedSongsAdapter: SavedSongsAdapter
    private val savedSongs = mutableListOf(
        Song("SPARKY", "SPARKY", R.drawable.img_lost_corner_placeholder),
        Song("IDOL", "(여자)아이들", R.drawable.img_lost_corner_placeholder),
        Song("Bad Boy", "Red Velvet (레드벨벳)", R.drawable.img_lost_corner_placeholder),
        Song("Always Me", "2am", R.drawable.img_lost_corner_placeholder),
        Song("가라앉지", "2am", R.drawable.img_lost_corner_placeholder)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSavedSongsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savedSongsAdapter = SavedSongsAdapter(savedSongs)
        binding.savedSongsRv.adapter = savedSongsAdapter
        binding.savedSongsRv.layoutManager = LinearLayoutManager(context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class SavedSongsAdapter(private val songs: MutableList<Song>) : RecyclerView.Adapter<SavedSongsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSavedSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songs[position])

        holder.binding.itemSavedSongMoreIv.setOnClickListener {
            songs.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, songs.size)
        }
    }

    override fun getItemCount(): Int = songs.size

    class ViewHolder(val binding: ItemSavedSongBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.itemSavedSongTitleTv.text = song.title
            binding.itemSavedSongArtistTv.text = song.artist
            binding.itemSavedSongIv.setImageResource(song.coverImg)
        }
    }
}
