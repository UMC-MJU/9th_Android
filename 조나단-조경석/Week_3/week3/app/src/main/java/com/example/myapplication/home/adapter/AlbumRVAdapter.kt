package com.example.myapplication.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.model.Album
import com.example.myapplication.databinding.ItemAlbumBinding
import java.util.*

class AlbumRVAdapter(
    private val albumList: ArrayList<Album>
) : RecyclerView.Adapter<AlbumViewHolder>(){

    interface MyItemClickListener{
        fun onItemClick(album: Album)
    }

    private lateinit var mItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AlbumViewHolder {
        val binding: ItemAlbumBinding = ItemAlbumBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return AlbumViewHolder(binding)
    }

    fun addItem(album: Album){
        albumList.add(album)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(albumList[position])
        holder.itemView.setOnClickListener { mItemClickListener.onItemClick(albumList[position]) }
    }

    override fun getItemCount(): Int = albumList.size
}