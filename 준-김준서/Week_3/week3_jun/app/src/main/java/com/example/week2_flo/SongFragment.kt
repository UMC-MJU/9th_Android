package com.example.week2_flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.week2_flo.databinding.FragmentSongBinding

class SongFragment : Fragment() {
    lateinit var binding: FragmentSongBinding

    private var isMixOn = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        applyMixUi()

        binding.songMixoffTg.setOnClickListener { toggleMix() }
        binding.songMixonIv.setOnClickListener { toggleMix() }
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