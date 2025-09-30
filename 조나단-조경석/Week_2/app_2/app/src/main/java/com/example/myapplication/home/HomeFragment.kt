package com.example.myapplication.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.model.Album
import com.example.myapplication.R
import com.example.myapplication.album.AlbumFragment
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.home.adapter.AlbumRVAdapter
import com.example.util.Const

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)


        val dummy = getDummy()
        val albumRVAdapter = AlbumRVAdapter(dummy)
        binding.homeTodayMusicAlbumRv.adapter = albumRVAdapter

        albumRVAdapter.setMyItemClickListener(object : AlbumRVAdapter.MyItemClickListener{
            override fun onItemClick(album: Album) {
                changeAlbumFragment(album)
            }
        })

        return binding.root
    }

    private fun getDummy() : ArrayList<Album> {
        return arrayListOf(
            Album(
                id = 0,
                title = "LILAC",
                singer = "아이유 (IU)",
                coverImg = R.drawable.img_album_exp2
            ),
            Album(
                id = 1,
                title = "LILAC",
                singer = "아이유 (IU)",
                coverImg = R.drawable.img_album_exp2
            ),
            Album(
                id = 2,
                title = "LILAC",
                singer = "아이유 (IU)",
                coverImg = R.drawable.img_album_exp2
            )
        )
    }

    private fun changeAlbumFragment(album: Album) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, AlbumFragment().apply {
                arguments = Bundle().apply {
                    putString(Const.TITLE_KEY, album.title)
                    putString(Const.SINGER_KEY, album.singer)
                    putInt(Const.IMAGE_KEY, album.coverImg ?: 0)
                }
            }).commitAllowingStateLoss()
    }
}