package com.example.myapplication.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentAlbumBinding
import com.example.util.Const

class AlbumFragment : Fragment() {

    lateinit var binding: FragmentAlbumBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        val title = arguments?.getString(Const.TITLE_KEY)
        val singer = arguments?.getString(Const.SINGER_KEY)
        val image = arguments?.getInt(Const.IMAGE_KEY)

        binding.apply {
            albumMusicTitleTv.text = title
            albumSingerNameTv.text = singer
            image?.let { albumAlbumIv.setImageResource(it) }
        }

        return binding.root
    }
}