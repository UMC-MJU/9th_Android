package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.model.Emotion
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var emotion: Emotion = Emotion.NONE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            imgBest.setOnClickListener {
                emotion = Emotion.BEST
                Toast.makeText(this@MainActivity, "best 클릭", Toast.LENGTH_SHORT).show()
                type = emotion
            }
            imgGood.setOnClickListener {
                emotion = Emotion.GOOD
                Toast.makeText(this@MainActivity, "good 클릭", Toast.LENGTH_SHORT).show()
                type = emotion
            }
            imgSoso.setOnClickListener {
                emotion = Emotion.SOSO
                Toast.makeText(this@MainActivity, "soso 클릭", Toast.LENGTH_SHORT).show()
                type = emotion
            }
            imgBad.setOnClickListener {
                emotion = Emotion.BAD
                Toast.makeText(this@MainActivity, "bad 클릭", Toast.LENGTH_SHORT).show()
                type = emotion
            }
            imgWorst.setOnClickListener {
                emotion = Emotion.WORST
                Toast.makeText(this@MainActivity, "worst 클릭", Toast.LENGTH_SHORT).show()
                type = emotion
            }
        }
    }
}