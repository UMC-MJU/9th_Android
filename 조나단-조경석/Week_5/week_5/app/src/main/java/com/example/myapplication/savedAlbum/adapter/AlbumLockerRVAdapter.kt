package com.example.myapplication.savedAlbum.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.model.Album
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemLockerAlbumBinding

class AlbumLockerRVAdapter(private val albumList: ArrayList<Album>) : RecyclerView.Adapter<AlbumLockerViewHolder>(){

    // 클릭 인터페이스 정의
    interface MyItemClickListener{
        fun onRemoveAlbum(position: Int)
    }

    private lateinit var mItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AlbumLockerViewHolder {
        val binding: ItemLockerAlbumBinding = ItemLockerAlbumBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return AlbumLockerViewHolder(binding)
    }

    fun removeItem(position: Int){
        albumList.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: AlbumLockerViewHolder, position: Int) {
        holder.bind(albumList[position])
        holder.binding.itemAlbumMoreIv.setOnClickListener {
            mItemClickListener.onRemoveAlbum(position)
        }
        holder.binding.itemAlbumPlayIv.setOnClickListener {
            if(holder.isPlay) it.setBackgroundResource(R.drawable.btn_miniplay_pause)
            else it.setBackgroundResource(R.drawable.btn_player_play)
            holder.isPlay = !holder.isPlay
        }
    }

    override fun getItemCount(): Int = albumList.size
}