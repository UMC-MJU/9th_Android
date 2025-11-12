package com.example.util

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.example.model.Song
import com.example.myapplication.R
import com.example.util.MusicItem.nowPos
import com.example.util.MusicItem.songs

object MusicItem {
    val songs = listOf(
        Song(
            id = 1,
            title = "뿜",
            singer = "뿜뿜",
            second = 0,
            playTime = 180,
            isPlaying = false,
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
            isPlaying = false,
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
            isPlaying = false,
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
            isPlaying = false,
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
            isPlaying = false,
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
            isPlaying = false,
            music = "music_next",
            coverImg = R.drawable.img_album_exp2,
            isLike = false
        )
    )

    var nowPos = 0
}

interface OnProgressUpdateListener {
    fun onProgressUpdate(progress: Int)
}

class MusicService: Service() {

    var listener: OnProgressUpdateListener? = null
    private val binder = MusicBinder()
    private var mediaPlayer: MediaPlayer? = null
    private var currentSongRes: Int? = null
    var timer: Timer = Timer(songs[nowPos].playTime,songs[nowPos].isPlaying)

    inner class MusicBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        return START_STICKY
    }

    fun play(songRes: Int) {
        songs[nowPos].isPlaying = true
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, songRes)
            mediaPlayer?.isLooping = true
        }
        if (currentSongRes != songRes) {
            mediaPlayer?.reset()
            mediaPlayer = MediaPlayer.create(this, songRes)
            mediaPlayer?.isLooping = true
            currentSongRes = songRes
        }
        mediaPlayer?.start()
    }

    fun pause() {
        songs[nowPos].isPlaying = false
        mediaPlayer?.pause()
    }

    fun resume() {
        if (mediaPlayer != null && !mediaPlayer!!.isPlaying) {
            mediaPlayer?.start()
        }
    }

    fun isPlaying(): Boolean = mediaPlayer?.isPlaying ?: false

    fun nowPlayingSongId(): Int = songs[nowPos].id

    fun setNowPlayingSongPosition(songId: Int) {
        for (i in 0 until songs.size){
            if (songs[i].id == songId){
                nowPos = i
                return
            }
        }
        if (nowPos != 0) {
           return
        } else {
            nowPos = 0
        }
        Log.d("여긴가요?", nowPos.toString())
    }

    fun getSong(): Song {
        return songs[nowPos]
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        mediaPlayer = null
        super.onDestroy()
    }

    fun startTimer(){
        timer = Timer(songs[nowPos].playTime,songs[nowPos].isPlaying)
        timer.start()
    }

    inner class Timer(private val playTime: Int, var isPlaying: Boolean = true) : Thread() {

        var second: Int = 0
        private var mills: Float = 0f
        var progress: Int = 0

        override fun run() {
            super.run()
            try {
                while (second < playTime) {
                    synchronized(this) {
                        // 일시정지 상태면 wait()
                        while (!isPlaying) {
                            (this as java.lang.Object).wait()
                        }
                    }

                    sleep(50)
                    mills += 50
                    progress = ((mills / playTime) * 100).toInt()
                    listener?.onProgressUpdate(progress)

                    if (mills % 1000 == 0f) {
                        second++
                    }
                }

            } catch (e: InterruptedException) {
                Log.d("Song", "쓰레드가 죽었습니다. ${e.message}")
            }
        }

        fun pauseTimer() {
            isPlaying = false
        }

        fun resumeTimer() {
            synchronized(this) {
                isPlaying = true
                (this as java.lang.Object).notify()
            }
        }
    }

}