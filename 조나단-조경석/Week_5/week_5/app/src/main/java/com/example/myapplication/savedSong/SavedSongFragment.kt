package com.example.myapplication.savedSong

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.model.TitleItem
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSavedSongBinding
import com.example.myapplication.savedSong.adapter.SavedSongAdapter

class SavedSongFragment : Fragment() {

    lateinit var binding: FragmentSavedSongBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedSongBinding.inflate(inflater,container,false)
        val list = getDummy()
        val savedSongAdapter = SavedSongAdapter(list)

        binding.apply {
            lockerSavedSongRecyclerView.adapter = savedSongAdapter
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