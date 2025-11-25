package com.example.myapplication.savedSong.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.model.Song
import com.example.model.TitleItem
import com.example.myapplication.databinding.ItemSavedSongBinding

class SavedSongViewHolder(
    val binding: ItemSavedSongBinding
): RecyclerView.ViewHolder(binding.root){

    fun bind(titleItem: Song){
        binding.apply {
            titleItem.coverImg?.let { itemSongImgIv.setImageResource(it) }
            itemSongTitleTv.text = titleItem.title
            itemSongSingerTv.text = titleItem.singer
        }
    }
}