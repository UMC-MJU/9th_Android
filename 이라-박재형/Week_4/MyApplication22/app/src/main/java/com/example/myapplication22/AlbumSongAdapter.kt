package com.example.myapplication22

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication22.databinding.ItemAlbumSongBinding // ViewBinding for item_album_song.xml

class AlbumSongAdapter(private val songList: List<AlbumSong>) :
    RecyclerView.Adapter<AlbumSongAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(song: AlbumSong, position: Int)
        fun onMoreClick(song: AlbumSong, position: Int)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemAlbumSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val song = songList[position]
        holder.bind(song)

        holder.itemView.setOnClickListener {
            listener?.onItemClick(song, position)
        }
        holder.binding.itemAlbumSongMoreBtn.setOnClickListener {
            listener?.onMoreClick(song, position)
        }
    }

    override fun getItemCount(): Int = songList.size

    inner class ViewHolder(val binding: ItemAlbumSongBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(song: AlbumSong) {
            binding.itemAlbumSongTrackNumberTv.text = String.format("%02d", song.trackNumber) // Format as "01", "02", etc.
            binding.itemAlbumSongTitleTv.text = song.title
            binding.itemAlbumSongArtistTv.text = song.artist
            // TODO: Handle 'isPlaying' state if needed (e.g., change track number to play icon)
        }
    }
}