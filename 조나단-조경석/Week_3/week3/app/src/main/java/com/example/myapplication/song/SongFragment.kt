package com.example.myapplication.song

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.album.adapter.AlbumVPAdapter.MyItemClickListener
import com.example.myapplication.databinding.FragmentDetailBinding
import com.example.myapplication.databinding.FragmentSongBinding

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

        return binding.root
    }
}