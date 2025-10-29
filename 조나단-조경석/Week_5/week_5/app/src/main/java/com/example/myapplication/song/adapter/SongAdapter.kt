package com.example.myapplication.song.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.model.TitleItem
import com.example.myapplication.databinding.ItemSongTextBinding
import java.util.ArrayList

class SongAdapter(
    private val titleList: ArrayList<TitleItem>
) : RecyclerView.Adapter<SongViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SongViewHolder {
        val binding: ItemSongTextBinding = ItemSongTextBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return SongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.bind(titleList[position], position + 1)
    }

    override fun getItemCount(): Int = titleList.size
}