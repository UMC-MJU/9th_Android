package com.example.myapplication.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.data.SongDatabase
import com.example.model.Album
import com.example.model.Like
import com.example.myapplication.R
import com.example.myapplication.album.adapter.AlbumVPAdapter
import com.example.myapplication.databinding.FragmentAlbumBinding
import com.example.myapplication.home.adapter.AlbumRVAdapter
import com.example.util.Const
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import kotlin.math.sin

class AlbumFragment : Fragment() {

    lateinit var binding: FragmentAlbumBinding
    private val information = arrayListOf("수록곡", "상세정보", "영상")
    private var isLiked: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        val albumData = arguments?.getString("album")
        val gson = Gson()

        val album = gson.fromJson(albumData, Album::class.java)
        isLiked = isLikedAlbum(album.id)
        val albumAdapter = AlbumVPAdapter(this)
        albumAdapter.setAlbumInfo(album.title, album.singer)
        albumAdapter.setMyItemClickListener(object : AlbumVPAdapter.MyItemClickListener{
            override fun onItemClick() {
                binding.albumAlbumIv.setImageResource(R.drawable.img_album_exp3)
            }
        })

        binding.apply {
            val userId: Int = getJwt()

            albumBackIv.setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }

            albumMusicTitleTv.text = album.title
            albumSingerNameTv.text = album.singer
            album.coverImg?.let { albumAlbumIv.setImageResource(it) }

            albumContentVp.adapter = albumAdapter
            TabLayoutMediator(albumContentTb, albumContentVp) { tab, position ->
                tab.text = information[position]
            }.attach()

            binding.albumLikeIv.setOnClickListener {
                if(isLiked) {
                    binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_off)
                    disLikeAlbum(userId, album.id)
                } else {
                    binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_on)
                    likeAlbum(userId, album.id)
                }

                isLiked = !isLiked
            }
        }

        return binding.root
    }

    private fun disLikeAlbum(userId: Int, albumId: Int) {
        val songDB = SongDatabase.getInstance(requireContext())!!
        songDB.albumDao().disLikeAlbum(userId, albumId)
    }

    private fun likeAlbum(userId: Int, albumId: Int) {
        val songDB = SongDatabase.getInstance(requireContext())!!
        val like = Like(userId, albumId)

        songDB.albumDao().likeAlbum(like)
    }


    private fun isLikedAlbum(albumId: Int): Boolean {
        val songDB = SongDatabase.getInstance(requireContext())!!
        val userId = getJwt()

        val likeId: Int? = songDB.albumDao().isLikedAlbum(userId, albumId)

        return likeId != null
    }

    private fun getJwt(): Int {
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        val jwt = spf!!.getInt("jwt", 0)

        return jwt
    }
}