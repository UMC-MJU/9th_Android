package com.example.myapplication

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.ColorFilter
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.example.model.Song
import com.example.myapplication.databinding.ActivitySongBinding
import com.example.util.Const
import java.util.Timer
import kotlin.String

class SongActivity : AppCompatActivity() {

    //전역 변수
    lateinit var binding : ActivitySongBinding
    lateinit var timer: Timer
    private var mediaPlayer: MediaPlayer? = null
    val songs = listOf(
        Song(
            id = 1,
            title = "뿜",
            singer = "뿜뿜",
            second = 0,
            playTime = 180,
            isPlaying = true,
            music = "music_bboom",
            coverImg = R.drawable.img_album_exp2,
            isLike = false
        ),
        Song(
            id = 2,
            title = "보이",
            singer = "뿜뿜",
            second = 0,
            playTime = 180,
            isPlaying = true,
            music = "music_boy",
            coverImg = R.drawable.img_album_exp2,
            isLike = false
        ),
        Song(
            id = 3,
            title = "버터",
            singer = "뿜뿜",
            second = 0,
            playTime = 180,
            isPlaying = true,
            music = "music_butter",
            coverImg = R.drawable.img_album_exp2,
            isLike = false
        ),
        Song(
            id = 4,
            title = "flu",
            singer = "뿜뿜",
            second = 0,
            playTime = 180,
            isPlaying = true,
            music = "music_flu",
            coverImg = R.drawable.img_album_exp2,
            isLike = false
        ),
        Song(
            id = 5,
            title = "라일락",
            singer = "뿜뿜",
            second = 0,
            playTime = 180,
            isPlaying = true,
            music = "music_lilac",
            coverImg = R.drawable.img_album_exp2,
            isLike = false
        ),
        Song(
            id = 6,
            title = "넥스트",
            singer = "뿜뿜",
            second = 0,
            playTime = 180,
            isPlaying = true,
            music = "music_next",
            coverImg = R.drawable.img_album_exp2,
            isLike = false
        )
    )
    var nowPos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra(Const.TITLE_KEY) ?: ""
        val singer = intent.getStringExtra(Const.SINGER_KEY)
        initSong()

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
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId",0)

        nowPos = getPlayingSongPosition(songId)

        Log.d("now Song ID",songs[nowPos].id.toString())

        startTimer()
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

        timer.interrupt()
        startTimer()

        mediaPlayer?.release()
        mediaPlayer = null

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
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)

        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this, music)

        if (song.isLike){
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_on)
        } else{
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }

        setPlayerStatus(song.isPlaying)

    }

    override fun onPause() {
        super.onPause()


        songs[nowPos].second = ((binding.songProgressSb.progress * songs[nowPos].playTime)/100)/1000
        songs[nowPos].isPlaying = false
        setPlayerStatus(false)

        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit() // 에디터

        editor.putInt("songId",songs[nowPos].id)

        editor.apply()
    }

    private fun setPlayerStatus (isPlaying : Boolean){
        songs[nowPos].isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if(isPlaying){
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
            mediaPlayer?.start()
        } else {
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
            if(mediaPlayer?.isPlaying == true){
                mediaPlayer?.pause()
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

    private fun startTimer(){
        timer = Timer(songs[nowPos].playTime,songs[nowPos].isPlaying)
        timer.start()
    }

    inner class Timer(private val playTime: Int,var isPlaying: Boolean = true):Thread(){

        private var second : Int = 0
        private var mills: Float = 0f

        override fun run() {
            super.run()
            try {
                while (true){

                    if (second >= playTime){
                        break
                    }

                    if (isPlaying){
                        sleep(50)
                        mills += 50

                        runOnUiThread {
                            binding.songProgressSb.progress = ((mills / playTime)*100).toInt()
                        }

                        if (mills % 1000 == 0f){
                            runOnUiThread {
                                binding.songStartTimeTv.text = String.format("%02d:%02d",second / 60, second % 60)
                            }
                            second++
                        }

                    }

                }

            }catch (e: InterruptedException){
                Log.d("Song","쓰레드가 죽었습니다. ${e.message}")
            }

        }
    }
}