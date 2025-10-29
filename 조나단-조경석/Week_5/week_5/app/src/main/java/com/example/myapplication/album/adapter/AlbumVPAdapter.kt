package com.example.myapplication.album.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.model.Album
import com.example.myapplication.detail.DetailFragment
import com.example.myapplication.song.SongFragment
import com.example.myapplication.video.VideoFragment
import kotlin.math.sin

class AlbumVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    interface MyItemClickListener{
        fun onItemClick()
    }

    private lateinit var mItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    private var title: String = ""
    private var singer: String = ""

    fun setAlbumInfo(title: String, singer: String) {
        this.title = title
        this.singer = singer
    }

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> SongFragment(mItemClickListener)
            1 -> DetailFragment(title, singer)
            else -> VideoFragment()
        }
    }
}