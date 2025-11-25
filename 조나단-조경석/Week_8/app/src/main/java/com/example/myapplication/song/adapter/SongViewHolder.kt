package com.example.myapplication.song.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.model.TitleItem
import com.example.myapplication.databinding.ItemSongTextBinding

class SongViewHolder(
    val binding: ItemSongTextBinding
): RecyclerView.ViewHolder(binding.root){

    fun bind(titleItem: TitleItem, index: Int){
        binding.apply {
            textNum.text = index.toString()
            textTitle.text = titleItem.title
            textSinger.text = titleItem.firstSinger
        }
    }
}