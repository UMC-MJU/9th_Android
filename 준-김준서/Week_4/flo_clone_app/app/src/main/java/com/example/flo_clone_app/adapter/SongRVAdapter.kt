package com.example.flo_clone_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo_clone_app.data.Song
import com.example.flo_clone_app.databinding.ItemSongBinding

class SongRVAdapter(private val songList: ArrayList<Song>) : RecyclerView.Adapter<SongRVAdapter.ViewHolder>() {

    // (1) 클릭 리스너를 위한 인터페이스 정의
    interface MyItemClickListener {
        fun onRemoveSong(position: Int)
    }

    // (2) 리스너 객체를 전달받을 변수와, 전달받는 함수 추가
    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        mItemClickListener = itemClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemSongBinding = ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songList[position])
        // (3) '더보기' 버튼 클릭 시 리스너의 onRemoveSong 함수 호출
        holder.binding.itemSongMoreIv.setOnClickListener {
            // bindingAdapterPosition은 holder에 이미 내장된 프로퍼티로, 현재 아이템의 위치를 정확하게 알려줍니다.
            mItemClickListener.onRemoveSong(holder.bindingAdapterPosition)
        }
    }

    override fun getItemCount(): Int = songList.size

    inner class ViewHolder(val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.itemSongTitleTv.text = song.title
            binding.itemSongSingerTv.text = song.singer
        }
    }
}