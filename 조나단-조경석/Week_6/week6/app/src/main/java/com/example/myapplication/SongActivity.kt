package com.example.myapplication

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.res.ColorStateList
import android.graphics.ColorFilter
import android.media.MediaPlayer
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.example.model.Song
import com.example.myapplication.databinding.ActivitySongBinding
import com.example.util.Const
import com.example.util.MusicItem.nowPos
import com.example.util.MusicItem.songs
import com.example.util.MusicService
import com.example.util.OnProgressUpdateListener
import java.util.Timer
import kotlin.String

class SongActivity : AppCompatActivity() {

    //전역 변수
    lateinit var binding : ActivitySongBinding
    private var musicService: MusicService? = null
    private var isBound = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            val musicBinder = binder as MusicService.MusicBinder
            musicService = musicBinder.getService()
            isBound = true
            musicService?.listener = object : OnProgressUpdateListener {
                override fun onProgressUpdate(progress: Int) {
                    runOnUiThread {
                        Log.d("하이", progress.toString())
                        binding.songProgressSb.progress = progress
                    }
                }
            }

            initSong()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            musicService = null
            isBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra(Const.TITLE_KEY) ?: ""
        val singer = intent.getStringExtra(Const.SINGER_KEY)

        Intent(this, MusicService::class.java).also {
            bindService(it, connection, Context.BIND_AUTO_CREATE)
        }

        binding.apply {

            songDownIb.setOnClickListener {
                goToMian(title)
            }

            songMiniplayerIv.setOnClickListener {
                setPlayerStatus(true)
                songPauseIv.visibility = View.VISIBLE
                songMiniplayerIv.visibility = View.GONE
            }

            songPauseIv.setOnClickListener {
                setPlayerStatus(false)
                songPauseIv.visibility = View.GONE
                songMiniplayerIv.visibility = View.VISIBLE
            }

            binding.songNextIv.setOnClickListener {
                moveSong(+1)
            }

            binding.songPreviousIv.setOnClickListener {
                moveSong(-1)
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

    private fun initSong(){
        Log.d("now Song ID",songs.toString())
        Log.d("now Song ID",songs[nowPos].isPlaying.toString())
        //musicService?.startTimer()
        setPlayer(songs[nowPos])
    }

    private fun moveSong(direct: Int){
        if (nowPos + direct < 0){
            Toast.makeText(this,"first song",Toast.LENGTH_SHORT).show()
            return
        }

        if (nowPos + direct >= songs.size){
            Toast.makeText(this,"last song",Toast.LENGTH_SHORT).show()
            return
        }

        nowPos += direct

        musicService?.timer?.interrupt()
        musicService?.startTimer()

        setPlayer(songs[nowPos])
    }

    private fun getPlayingSongPosition(songId: Int): Int{
        for (i in 0 until songs.size){
            if (songs[i].id == songId){
                return i
            }
        }
        return 0
    }

    private fun setPlayer(song: Song){
        binding.songMusicTitleTv.text = song.title
        binding.songSingerNameTv.text = song.singer
        binding.songStartTimeTv.text = String.format("%02d:%02d",song.second / 60, song.second % 60)
        binding.songEndTimeTv.text = String.format("%02d:%02d",song.playTime / 60, song.playTime % 60)
        binding.songAlbumIv.setImageResource(song.coverImg!!)
        binding.songProgressSb.progress = musicService?.timer?.progress ?: 0

        if (song.isLike){
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_on)
        } else {
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }

        setPlayerStatus(song.isPlaying)
    }

    private fun setPlayerStatus (isPlaying : Boolean){
        songs[nowPos].isPlaying = isPlaying
        musicService?.timer?.isPlaying = isPlaying

        if(isPlaying){
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
            musicService?.play(resources.getIdentifier(songs[nowPos].music, "raw", this.packageName))
        } else {
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
            musicService?.pause()
            musicService?.timer?.pauseTimer()
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