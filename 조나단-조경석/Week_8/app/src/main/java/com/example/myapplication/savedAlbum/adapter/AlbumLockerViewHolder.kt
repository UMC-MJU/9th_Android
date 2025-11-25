package com.example.myapplication.savedAlbum.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.model.Album
import com.example.myapplication.databinding.ItemLockerAlbumBinding

class AlbumLockerViewHolder(val binding: ItemLockerAlbumBinding): RecyclerView.ViewHolder(binding.root){
    var isPlay : Boolean = true

    fun bind(album: Album){
        binding.itemAlbumImgIv.setImageResource(album.coverImg!!)
        binding.itemAlbumTitleTv.text = album.title
        binding.itemAlbumSingerTv.text = album.singer
    }
}