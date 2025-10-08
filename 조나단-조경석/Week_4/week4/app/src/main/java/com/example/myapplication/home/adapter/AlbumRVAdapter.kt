package com.example.myapplication.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.model.Album
import com.example.model.TitleItem
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemAlbumBinding
import java.util.*

class AlbumRVAdapter(
    private val albumList: ArrayList<Album>
) : RecyclerView.Adapter<AlbumViewHolder>(){

    interface MyItemClickListener{
        fun onItemClick(album: Album)
        fun onPlayBtnClick(titleItem: TitleItem)
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
        holder.itemView.setOnClickListener {
            mItemClickListener.onItemClick(albumList[position])
        }
        holder.binding.itemAlbumPlayImgIv.setOnClickListener {
            mItemClickListener.onPlayBtnClick(
                TitleItem(
                    title = "첫 번째 제목",
                    firstAlbum = R.drawable.img_album_exp2,
                    firstTitle = "LILAC(1)",
                    firstSinger = "아이유 (IU)",
                    secondAlbum = R.drawable.img_album_exp2,
                    secondTitle = "LILAC(2)",
                    secondSinger = "아이유 (IU)",
                )
            )
        }
    }

    override fun getItemCount(): Int = albumList.size
}