package com.example.myapplication.savedSong.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.model.TitleItem
import com.example.myapplication.databinding.ItemSavedSongBinding
import java.util.ArrayList

class SavedSongAdapter(
    private val titleList: ArrayList<TitleItem>
) : RecyclerView.Adapter<SavedSongViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SavedSongViewHolder {
        val binding: ItemSavedSongBinding = ItemSavedSongBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return SavedSongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedSongViewHolder, position: Int) {
        holder.bind(titleList[position])
        holder.binding.itemSongMoreIv.setOnClickListener {
            titleList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, titleList.size)
        }
    }

    override fun getItemCount(): Int = titleList.size
}