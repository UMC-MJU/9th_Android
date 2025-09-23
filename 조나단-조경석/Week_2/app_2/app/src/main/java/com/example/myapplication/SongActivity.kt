package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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