package com.example.myapplication.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.model.Album
import com.example.model.TitleItem
import com.example.myapplication.R
import com.example.myapplication.album.AlbumFragment
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.home.adapter.AlbumRVAdapter
import com.example.myapplication.home.adapter.HomeTitleAdapter
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
        val dummy2 = getDummy2()
        val albumRVAdapter = AlbumRVAdapter(dummy)
        val titleAdapter = HomeTitleAdapter(dummy2)
        binding.apply {
            homeTodayMusicAlbumRv.adapter = albumRVAdapter
            viewPagerHome.adapter = titleAdapter
        }

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

    private fun getDummy2() : ArrayList<TitleItem> {
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