package com.example.myapplication.savedSong.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.model.TitleItem
import com.example.myapplication.databinding.ItemSavedSongBinding

class SavedSongViewHolder(
    val binding: ItemSavedSongBinding
): RecyclerView.ViewHolder(binding.root){

    fun bind(titleItem: TitleItem){
        binding.apply {
            itemSongImgIv.setImageResource(titleItem.firstAlbum)
            itemSongTitleTv.text = titleItem.firstTitle
            itemSongSingerTv.text = titleItem.firstSinger
        }
    }
}