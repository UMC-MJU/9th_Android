package com.example.myapplication.home.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.model.Album
import com.example.myapplication.databinding.ItemAlbumBinding

class AlbumViewHolder(
    val binding: ItemAlbumBinding
): RecyclerView.ViewHolder(binding.root){

    fun bind(album: Album){
        binding.itemAlbumTitleTv.text = album.title
        binding.itemAlbumSingerTv.text = album.singer
        binding.itemAlbumCoverImgIv.setImageResource(album.coverImg!!)
    }
}