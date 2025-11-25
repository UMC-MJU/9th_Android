package com.example.myapplication.home.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.model.Album
import com.example.model.TitleItem
import com.example.myapplication.databinding.ItemHomePagerBinding

class HomeTitleViewHolder(
    val binding: ItemHomePagerBinding
): RecyclerView.ViewHolder(binding.root){

    fun bind(titleItem: TitleItem){
        binding.apply {
            homePannelTitleTv.text = titleItem.title
            homePannelAlbumImg01Iv.setImageResource(titleItem.firstAlbum)
            homePannelAlbumImg02Iv.setImageResource(titleItem.secondAlbum)
            homePannelAlbumTitle01Tv.text = titleItem.firstTitle
            homePannelAlbumTitle03Tv.text = titleItem.secondTitle
            homePannelAlbumSinger02Tv.text = titleItem.firstSinger
            homePannelAlbumSinger04Tv.text = titleItem.secondSinger
        }

    }
}