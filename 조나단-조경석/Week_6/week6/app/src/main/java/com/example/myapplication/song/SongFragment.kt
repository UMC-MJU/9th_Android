package com.example.myapplication.song

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.model.TitleItem
import com.example.myapplication.R
import com.example.myapplication.album.adapter.AlbumVPAdapter.MyItemClickListener
import com.example.myapplication.databinding.FragmentDetailBinding
import com.example.myapplication.databinding.FragmentSongBinding
import com.example.myapplication.home.adapter.HomeTitleAdapter
import com.example.myapplication.song.adapter.SongAdapter

class SongFragment(itemClickListener: MyItemClickListener) : Fragment() {

    var mItemClickListener: MyItemClickListener = itemClickListener
    lateinit var binding: FragmentSongBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongBinding.inflate(inflater,container,false)
        binding.btnMix.setOnClickListener {
            mItemClickListener.onItemClick()
        }

        val songAdapter = SongAdapter(getDummy())

        binding.apply {
            recyclerSong.adapter = songAdapter
        }

        return binding.root
    }

    private fun getDummy() : ArrayList<TitleItem> {
        return arrayListOf(
            TitleItem(
                title = "첫 번째 제목",
                firstAlbum = R.drawable.img_album_exp2,
                firstTitle = "LILAC",
                firstSinger = "아이유 (IU)",
                secondAlbum = R.drawable.img_album_exp2,
                secondTitle = "LILAC",
                secondSinger = "아이유 (IU)",
            ),
            TitleItem(
                title = "두 번째 제목",
                firstAlbum = R.drawable.img_album_exp2,
                firstTitle = "LILAC",
                firstSinger = "아이유 (IU)",
                secondAlbum = R.drawable.img_album_exp2,
                secondTitle = "LILAC",
                secondSinger = "아이유",
            ),
            TitleItem(
                title = "세 번째 제목",
                firstAlbum = R.drawable.img_album_exp2,
                firstTitle = "LILAC",
                firstSinger = "아이유",
                secondAlbum = R.drawable.img_album_exp2,
                secondTitle = "LILAC",
                secondSinger = "아이유 (IU)",
            ),
        )
    }

}