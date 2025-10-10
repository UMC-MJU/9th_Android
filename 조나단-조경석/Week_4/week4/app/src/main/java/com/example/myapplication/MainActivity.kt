package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.home.HomeFragment
import com.example.myapplication.locker.LockerFragment
import com.example.util.Const

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

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
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.apply {
            mainPlayerCl.setOnClickListener {
                goToSong()
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
        }
        getResultTitle.launch(intent)
    }
}