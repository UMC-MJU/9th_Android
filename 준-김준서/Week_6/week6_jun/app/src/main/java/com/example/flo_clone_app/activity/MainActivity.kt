package com.example.flo_clone_app.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.flo_clone_app.HomeFragment
import com.example.flo_clone_app.LockerFragment
import com.example.flo_clone_app.LookFragment
import com.example.flo_clone_app.PlayerManager
import com.example.flo_clone_app.R
import com.example.flo_clone_app.SearchFragment
import com.example.flo_clone_app.data.Album
import com.example.flo_clone_app.data.Song
import com.example.flo_clone_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val song = Song(binding.mainMiniplayerTitleTv.text.toString(),
            binding.mainMiniplayerSingerTv.text.toString())

        binding.mainPlayerCl.setOnClickListener {
            val intent = Intent(this, SongActivity::class.java)
            intent.putExtra("title", song.title)
            intent.putExtra("singer", song.singer)

            startActivity(intent)
        }

        initBottomNavigation()

        Log.d("Song123", song.title + song.singer)

        val uri = Uri.parse("android.resource://${packageName}/${R.raw.music_lilac}")
        PlayerManager.initAndPlay(this, uri)

        // 미니 플레이어 UI 버튼 연결
        binding.mainMiniplayerPlayIv.setOnClickListener {
            PlayerManager.playPause()
        }
        binding.mainPauseBtn.setOnClickListener {
            PlayerManager.playPause()
        }

        // PlayerManager 상태 변화 감지해서 Seekbar / 아이콘 업데이트
        PlayerManager.attach { state ->
            binding.mainProgressSb.max = state.duration
            binding.mainProgressSb.progress = state.position

            if (state.isPlaying){
                binding.mainMiniplayerPlayIv.visibility = View.GONE
                binding.mainPauseBtn.visibility = View.VISIBLE
            } else {
                binding.mainMiniplayerPlayIv.visibility = View.VISIBLE
                binding.mainPauseBtn.visibility = View.GONE
            }
        }
    }

    fun updateMiniPlayer(album: Album) {
        binding.mainMiniplayerTitleTv.text = album.title
        binding.mainMiniplayerSingerTv.text = album.singer
        binding.mainProgressSb.progress = 0
    }

    private fun initBottomNavigation(){

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener{ item ->
            when (item.itemId) {

                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lookFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
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