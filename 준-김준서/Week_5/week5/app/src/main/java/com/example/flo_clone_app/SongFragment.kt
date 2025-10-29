package com.example.flo_clone_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager // 추가
import com.example.flo_clone_app.adapter.SongRVAdapter // 추가
import com.example.flo_clone_app.data.Song // 추가
import com.example.flo_clone_app.databinding.FragmentSongBinding

class SongFragment : Fragment() {
    lateinit var binding: FragmentSongBinding
    private var isMixOn = false
    private var songDatas = ArrayList<Song>() // 추가

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. 기존 토글 버튼 로직
        applyMixUi()
        binding.songMixoffTg.setOnClickListener { toggleMix() }
        binding.songMixonIv.setOnClickListener { toggleMix() }

        // 2. 리사이클러뷰 로직 추가
        // 더미 데이터 추가
        songDatas.apply {
            add(Song("LILAC", "아이유 (IU)"))
            add(Song("Flu", "아이유 (IU)"))
            add(Song("Coin", "아이유 (IU)"))
            add(Song("봄 안녕 봄", "아이유 (IU)"))
            add(Song("Celebrity", "아이유 (IU)"))
        }

        // 어댑터 생성 및 데이터 전달
        val songRVAdapter = SongRVAdapter(songDatas)
        // XML에 추가한 RecyclerView ID로 연결
        binding.songListRv.adapter = songRVAdapter
        // 레이아웃 매니저 설정
        binding.songListRv.layoutManager = LinearLayoutManager(context)
    }

    private fun toggleMix() {
        isMixOn = !isMixOn
        applyMixUi()
    }

    private fun applyMixUi() {
        if (isMixOn) {
            binding.songMixoffTg.visibility = View.GONE
            binding.songMixonIv.visibility = View.VISIBLE
        } else {
            binding.songMixoffTg.visibility = View.VISIBLE
            binding.songMixonIv.visibility = View.GONE
        }
    }
}