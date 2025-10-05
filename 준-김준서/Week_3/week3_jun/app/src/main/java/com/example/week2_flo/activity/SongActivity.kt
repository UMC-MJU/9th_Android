package com.example.week2_flo.activity

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ImageViewCompat
import com.example.week2_flo.R
import com.example.week2_flo.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    lateinit var binding: ActivitySongBinding

    private var repeatOn = false
    private var shuffleOn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //닫기 버튼
        binding.songDownIb.setOnClickListener {
            finish()
        }

        // 재생 및 일시 정지 버튼
        binding.songMiniplayerIv.setOnClickListener{
            setPlayerStatus(false)
        }
        binding.songPauseIv.setOnClickListener{
            setPlayerStatus(true)
        }

        // 데이터 설정(제목, 가수)
        if(intent.hasExtra("title") && intent.hasExtra("singer")){
            binding.songMusicTitleTv.text = intent.getStringExtra("title")
            binding.songSingerNameTv.text = intent.getStringExtra("singer")
        }
        Log.d("SongCheck", "title=${binding.songMusicTitleTv.text}, singer=${binding.songSingerNameTv.text}")

        // 반복 버튼 클릭 -> 색상 토글
        binding.songRepeatIv.setOnClickListener {
            repeatOn = !repeatOn
            val color = if (repeatOn) getColor(R.color.purple_700) else getColor(R.color.gray_color)
            ImageViewCompat.setImageTintList(binding.songRepeatIv, ColorStateList.valueOf(color))
        }

        // 셔플 버튼 클릭 -> 색상 토글
        binding.songRandomIv.setOnClickListener {
            shuffleOn = !shuffleOn
            val color = if (shuffleOn) getColor(R.color.purple_700) else getColor(R.color.gray_color)
            ImageViewCompat.setImageTintList(binding.songRandomIv, ColorStateList.valueOf(color))
        }
    }

    fun setPlayerStatus(isPlaying : Boolean){
        if(isPlaying){
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
        }else{
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
        }
    }
}