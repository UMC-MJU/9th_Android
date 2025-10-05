package com.example.myapplication

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.ColorFilter
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.example.myapplication.databinding.ActivitySongBinding
import com.example.util.Const

class SongActivity : AppCompatActivity() {

    //전역 변수
    lateinit var binding : ActivitySongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra(Const.TITLE_KEY) ?: ""
        val singer = intent.getStringExtra(Const.SINGER_KEY)

        binding.apply {
            songMusicTitleTv.text = title
            songSingerNameTv.text = singer

            songDownIb.setOnClickListener {
                goToMian(title)
            }

            songMiniplayerIv.setOnClickListener {
                songPauseIv.visibility = View.VISIBLE
                songMiniplayerIv.visibility = View.GONE
            }

            songPauseIv.setOnClickListener {
                songPauseIv.visibility = View.GONE
                songMiniplayerIv.visibility = View.VISIBLE
            }

            //TODO 활성화, 비활성화 그림 필요
//            songRepeatIv.setOnClickListener {
//                songRepeatIv.visibility = View.GONE
//                songRepeatActiveIv.visibility = View.VISIBLE
//            }
//
//            songRepeatActiveIv.setOnClickListener {
//                songRepeatIv.visibility = View.VISIBLE
//                songRepeatActiveIv.visibility = View.GONE
//            }
//
//            songRandomIv.setOnClickListener {
//                songRandomIv.visibility = View.GONE
//                songRandomActiveIv.visibility = View.VISIBLE
//            }
//
//            songRandomActiveIv.setOnClickListener {
//                songRandomIv.visibility = View.VISIBLE
//                songRandomActiveIv.visibility = View.GONE
//            }
        }
    }

    private fun goToMian(title: String){
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra(Const.TITLE_KEY, title)
        }
        setResult(RESULT_OK, intent)
        finish()
    }
}