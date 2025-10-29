package com.example.flo_clone_app.activity

import android.content.res.ColorStateList
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ImageViewCompat
import com.example.flo_clone_app.R
import com.example.flo_clone_app.databinding.ActivitySongBinding
import kotlin.math.max

class SongActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySongBinding

    private var repeatOn = false
    private var shuffleOn = false

    private var mediaPlayer: MediaPlayer? = null
    private val uiHandler = Handler(Looper.getMainLooper())
    private val tick = object : Runnable {
        override fun run() {
            val mp = mediaPlayer ?: return
            if (mp.isPlaying) {
                val pos = mp.currentPosition
                binding.songSeekbar.progress = pos
                binding.songStartTimeTv.text = formatMs(pos)
            }
            uiHandler.postDelayed(this, 250)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 닫기
        binding.songDownIb.setOnClickListener { finish() }

        // 제목/가수
        if (intent.hasExtra("title") && intent.hasExtra("singer")) {
            binding.songMusicTitleTv.text = intent.getStringExtra("title")
            binding.songSingerNameTv.text = intent.getStringExtra("singer")
        }
        Log.d("SongCheck",
            "title=${binding.songMusicTitleTv.text}, singer=${binding.songSingerNameTv.text}"
        )

        initPlayer(R.raw.music_lilac)
        Log.d("MusicPlayer", "playing=${mediaPlayer?.isPlaying} dur=${mediaPlayer?.duration} pos=${mediaPlayer?.currentPosition}")

        // ▶/⏸
        binding.songMiniplayerIv.setOnClickListener { play() }
        binding.songPauseIv.setOnClickListener { pause() }

        // 이전/다음 (단일 트랙: 처음으로)
        binding.songPreviousIv.setOnClickListener { seekToStart() }
        binding.songNextIv.setOnClickListener { seekToStart() }

        // SeekBar 드래그
        binding.songSeekbar.setOnSeekBarChangeListener(
            object : android.widget.SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: android.widget.SeekBar?, progress: Int, fromUser: Boolean
                ) {
                    if (fromUser) {
                        mediaPlayer?.seekTo(progress)
                        binding.songStartTimeTv.text = formatMs(progress)
                    }
                }
                override fun onStartTrackingTouch(seekBar: android.widget.SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: android.widget.SeekBar?) {}
            }
        )

        // 반복/셔플 색상 토글
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

    private fun initPlayer(resId: Int) {
        releasePlayer()
        mediaPlayer = MediaPlayer.create(this, resId).apply {
            setOnPreparedListener {
                binding.songSeekbar.max = duration
                binding.songEndTimeTv.text = formatMs(duration)
                binding.songStartTimeTv.text = formatMs(0)
                setPlayerStatus(false) // 초기 UI: ⏸ 숨기고 ▶ 보이기
            }
            setOnCompletionListener {
                if (repeatOn) {
                    seekTo(0)
                    start()
                    setPlayerStatus(true)
                } else {
                    setPlayerStatus(false)
                }
            }
        }
    }

    private fun play() {
        mediaPlayer?.let { mp ->
            if (!mp.isPlaying) {
                mp.start()
                setPlayerStatus(true)
                uiHandler.post(tick)
                Log.d("MusicPlayer", "play(): started isPlaying=${mp.isPlaying}")
            }
        }
    }

    private fun pause() {
        mediaPlayer?.let { mp ->
            if (mp.isPlaying) mp.pause()
            Log.d("MusicPlayer", "pause(): isPlaying=${mp.isPlaying}")
        }
        setPlayerStatus(false)
    }

    private fun seekToStart() {
        mediaPlayer?.seekTo(0)
        if (mediaPlayer?.isPlaying == true) setPlayerStatus(true) else setPlayerStatus(false)
    }

    private fun setPlayerStatus(isPlaying: Boolean) {
        if (isPlaying) {
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
        } else {
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
        }
    }

    override fun onStart() {
        super.onStart()
        if (mediaPlayer?.isPlaying == true) uiHandler.post(tick)
    }

    override fun onPause() {
        super.onPause()
        pause() // 화면 벗어나면 일시정지
        uiHandler.removeCallbacks(tick)
    }

    override fun onDestroy() {
        super.onDestroy()
        uiHandler.removeCallbacks(tick)
        releasePlayer()
    }

    private fun releasePlayer() {
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun formatMs(ms: Int): String {
        val sec = max(ms / 1000, 0)
        val m = sec / 60
        val s = sec % 60
        return String.format("%02d:%02d", m, s)
    }
}