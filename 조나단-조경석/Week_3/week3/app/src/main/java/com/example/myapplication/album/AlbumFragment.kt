package com.example.myapplication.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.model.Album
import com.example.myapplication.R
import com.example.myapplication.album.adapter.AlbumVPAdapter
import com.example.myapplication.databinding.FragmentAlbumBinding
import com.example.myapplication.home.adapter.AlbumRVAdapter
import com.example.util.Const
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.math.sin

class AlbumFragment : Fragment() {

    lateinit var binding: FragmentAlbumBinding
    private val information = arrayListOf("수록곡", "상세정보", "영상")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        val title = arguments?.getString(Const.TITLE_KEY) ?: ""
        val singer = arguments?.getString(Const.SINGER_KEY) ?: ""
        val image = arguments?.getInt(Const.IMAGE_KEY)
        val albumAdapter = AlbumVPAdapter(this)
        albumAdapter.setAlbumInfo(title, singer)
        albumAdapter.setMyItemClickListener(object : AlbumVPAdapter.MyItemClickListener{
            override fun onItemClick() {
                binding.albumAlbumIv.setImageResource(R.drawable.img_album_exp3)
            }
        })

        binding.apply {
            albumMusicTitleTv.text = title
            albumSingerNameTv.text = singer
            image?.let { albumAlbumIv.setImageResource(it) }

            albumContentVp.adapter = albumAdapter
            TabLayoutMediator(albumContentTb, albumContentVp) { tab, position ->
                tab.text = information[position]
            }.attach()
        }

        return binding.root
    }
}