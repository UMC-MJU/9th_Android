package com.example.flo_clone_app.activity

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ImageViewCompat
import android.content.res.ColorStateList
import com.example.flo_clone_app.PlayerManager
import com.example.flo_clone_app.R
import com.example.flo_clone_app.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySongBinding

    private var repeatOn = false
    private var shuffleOn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 제목/가수 표시
        if (intent.hasExtra("title") && intent.hasExtra("singer")) {
            binding.songMusicTitleTv.text = intent.getStringExtra("title")
            binding.songSingerNameTv.text = intent.getStringExtra("singer")
        }

        // UI 연결
        binding.songMiniplayerIv.setOnClickListener { PlayerManager.playPause() }
        binding.songPauseIv.setOnClickListener { PlayerManager.playPause() }

        // seekbar 드래그
        binding.songSeekbar.setOnSeekBarChangeListener(object :
            android.widget.SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: android.widget.SeekBar?, progress: Int, fromUser: Boolean
            ) {
                if (fromUser) PlayerManager.seekTo(progress)
            }
            override fun onStartTrackingTouch(seekBar: android.widget.SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: android.widget.SeekBar?) {}
        })

        // PlayerManager → UI 업데이트
        PlayerManager.attach { state ->
            binding.songSeekbar.max = state.duration
            binding.songSeekbar.progress = state.position

            binding.songStartTimeTv.text = formatMs(state.position)
            binding.songEndTimeTv.text = formatMs(state.duration)

            if (state.isPlaying){
                binding.songMiniplayerIv.visibility = View.GONE
                binding.songPauseIv.visibility = View.VISIBLE
            } else {
                binding.songMiniplayerIv.visibility = View.VISIBLE
                binding.songPauseIv.visibility = View.GONE
            }
        }

        // 뒤로가기
        binding.songDownIb.setOnClickListener { finish() }

        // 반복/셔플 버튼 색 토글
        binding.songRepeatIv.setOnClickListener {
            repeatOn = !repeatOn
            val color = if (repeatOn) getColor(R.color.purple_700) else getColor(R.color.gray_color)
            ImageViewCompat.setImageTintList(binding.songRepeatIv, ColorStateList.valueOf(color))
        }
        binding.songRandomIv.setOnClickListener {
            shuffleOn = !shuffleOn
            val color = if (shuffleOn) getColor(R.color.purple_700) else getColor(R.color.gray_color)
            ImageViewCompat.setImageTintList(binding.songRandomIv, ColorStateList.valueOf(color))
        }
    }

    private fun formatMs(ms: Int): String {
        val sec = ms / 1000
        val m = sec / 60
        val s = sec % 60
        return String.format("%02d:%02d", m, s)
    }
}