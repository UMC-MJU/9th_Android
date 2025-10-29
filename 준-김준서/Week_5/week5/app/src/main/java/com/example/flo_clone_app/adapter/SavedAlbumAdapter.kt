package com.example.flo_clone_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.flo_clone_app.R
import com.example.flo_clone_app.data.SavedAlbum

class SavedAlbumAdapter(
    private val listener: Listener
) : ListAdapter<SavedAlbum, SavedAlbumAdapter.VH>(DIFF) {

    interface Listener {
        fun onPlayPauseClick(item: SavedAlbum)
        fun onOverflowClick(anchor: View, item: SavedAlbum)
        fun onItemClick(item: SavedAlbum)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_saved_album, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivCover: ImageView = itemView.findViewById(R.id.ivCover)
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvArtist: TextView = itemView.findViewById(R.id.tvArtist)
        private val tvMeta: TextView = itemView.findViewById(R.id.tvMeta)

        private val btnPlayPause: ImageView = itemView.findViewById(R.id.item_album_play_iv)
        private val btnMore: ImageView = itemView.findViewById(R.id.item_album_more_iv)

        fun bind(item: SavedAlbum) {
            tvTitle.text = item.title
            tvArtist.text = item.artist
            tvMeta.text = "${item.savedAt} | ${item.genre}"
            ivCover.setImageResource(R.drawable.img_album_exp2)

            btnPlayPause.setImageResource(
                if (item.isPlaying) R.drawable.btn_miniplay_pause else R.drawable.btn_miniplayer_play
            )

            itemView.setOnClickListener { listener.onItemClick(item) }
            btnPlayPause.setOnClickListener { listener.onPlayPauseClick(item) }
            btnMore.setOnClickListener { v -> listener.onOverflowClick(v, item) }
        }
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<SavedAlbum>() {
            override fun areItemsTheSame(old: SavedAlbum, new: SavedAlbum) = old.id == new.id
            override fun areContentsTheSame(old: SavedAlbum, new: SavedAlbum) = old == new
        }
    }
}