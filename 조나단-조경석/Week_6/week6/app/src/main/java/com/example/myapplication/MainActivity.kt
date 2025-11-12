package com.example.myapplication

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.model.Song
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.home.HomeFragment
import com.example.myapplication.locker.LockerFragment
import com.example.util.MusicService
import com.example.util.Const
import com.example.util.MusicItem.nowPos
import com.example.util.MusicItem.songs
import com.example.util.OnProgressUpdateListener

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var musicService: MusicService? = null
    private var isBound = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MusicService.MusicBinder
            musicService = binder.getService()
            isBound = true

            musicService?.listener = object : OnProgressUpdateListener {
                override fun onProgressUpdate(progress: Int) {
                    runOnUiThread {
                        Log.d("하이", progress.toString())
                        binding.songProgressSb.progress = progress
                    }
                }
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            musicService = null
            isBound = false
        }
    }

    private val getResultTitle = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val returnTitle = result.data?.getStringExtra(Const.TITLE_KEY) ?: "오류"
            Toast.makeText(this, returnTitle, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        Intent(this, MusicService::class.java).also { intent ->
            startService(intent) // 백그라운드 유지용
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
            initSong()
        }

        binding.apply {
            mainPlayerCl.setOnClickListener {
                goToSong()
            }

            mainMiniplayerPreviousIv.setOnClickListener {
                moveSong(-1)
            }

            mainMiniplayerNextIv.setOnClickListener {
                moveSong(+1)
            }

            mainMiniplayerBtn.setOnClickListener {
                setPlayerStatus(true)
                mainMiniplayerPauseBtn.visibility = View.VISIBLE
                mainMiniplayerBtn.visibility = View.GONE
            }

            mainMiniplayerPauseBtn.setOnClickListener {
                setPlayerStatus(false)
                mainMiniplayerPauseBtn.visibility = View.GONE
                mainMiniplayerBtn.visibility = View.VISIBLE
            }

            mainBnv.setOnItemSelectedListener{ item ->
                when (item.itemId) {

                    R.id.homeFragment -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, HomeFragment())
                            .commitAllowingStateLoss()
                        return@setOnItemSelectedListener true
                    }

                    R.id.lookFragment -> {
//                        supportFragmentManager.beginTransaction()
//                            .replace(R.id.main_frm, LookFragment())
//                            .commitAllowingStateLoss()
                        return@setOnItemSelectedListener true
                    }
                    R.id.searchFragment -> {
//                        supportFragmentManager.beginTransaction()
//                            .replace(R.id.main_frm, SearchFragment())
//                            .commitAllowingStateLoss()
                        return@setOnItemSelectedListener true
                    }
                    R.id.lockerFragment -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, LockerFragment())
                            .commitAllowingStateLoss()
                        return@setOnItemSelectedListener true
                    }
                }
                false
            }
        }
    }

    private fun goToSong(){
        val intent = Intent(this, SongActivity::class.java).apply {
            putExtra(Const.TITLE_KEY, "라일락")
            putExtra(Const.SINGER_KEY, "아이유 (IU)")
            putExtra(Const.SONG_ID, musicService?.nowPlayingSongId())
        }
        getResultTitle.launch(intent)
    }

    private fun initSong(){
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId",0)

        musicService?.setNowPlayingSongPosition(songId)
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

        setPlayer(songs[nowPos])
    }

    private fun setPlayer(song: Song){
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        binding.songProgressSb.progress = musicService?.timer?.progress ?: 0

        setPlayerStatus(song.isPlaying)

    }

    private fun setPlayerStatus (isPlaying : Boolean){
        songs[nowPos].isPlaying = isPlaying

        if(isPlaying){
            binding.mainMiniplayerPauseBtn.visibility = View.VISIBLE
            binding.mainMiniplayerBtn.visibility = View.GONE
            musicService?.play(resources.getIdentifier(songs[nowPos].music, "raw", this.packageName))
            if (musicService?.timer?.progress != 0) {
                musicService?.timer?.resumeTimer()
            } else {
                musicService?.startTimer()
            }
        } else {
            binding.mainMiniplayerPauseBtn.visibility = View.GONE
            binding.mainMiniplayerBtn.visibility = View.VISIBLE
            musicService?.pause()
            musicService?.timer?.pauseTimer()
        }
    }
}