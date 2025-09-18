package com.example.myapplication

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var pairs: List<Pair<ImageView, TextView>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 뒤로가기
        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener { finish() }

        //메인 요소
        val ivHappy   = findViewById<ImageView>(R.id.ivHappy)
        val tvHappy   = findViewById<TextView>(R.id.tvHappy)
        val ivExcited = findViewById<ImageView>(R.id.ivExcited)
        val tvExcited = findViewById<TextView>(R.id.tvExcited)
        val ivNormal  = findViewById<ImageView>(R.id.ivNormal)
        val tvNormal  = findViewById<TextView>(R.id.tvNormal)
        val ivAnxious = findViewById<ImageView>(R.id.ivAnxious)
        val tvAnxious = findViewById<TextView>(R.id.tvAnxious)
        val ivAngry   = findViewById<ImageView>(R.id.ivAngry)
        val tvAngry   = findViewById<TextView>(R.id.tvAngry)

        pairs = listOf(
            ivHappy to tvHappy,
            ivExcited to tvExcited,
            ivNormal  to tvNormal,
            ivAnxious to tvAnxious,
            ivAngry   to tvAngry
        )

        pairs.forEach { (img, text) ->
            img.setOnClickListener {
                select(text)
                Toast.makeText(this, text.text.toString(), Toast.LENGTH_SHORT).show()
            }
            text.setOnClickListener { img.performClick() }
        }
    }

    private fun select(target: TextView) {
        val normal = getColor(R.color.gray_700)
        // 전체 초기화
        pairs.forEach { (_, tv) -> tv.setTextColor(normal) }

        // tag 타입별로 색 해석
        val selectedColor: Int = when (val t = target.tag) {
            is Int -> ContextCompat.getColor(this, t)   // @color 리소스 ID
            is ColorStateList -> t.defaultColor         // ColorStateList
            is String -> Color.parseColor(t)            // "#RRGGBB"
            else -> normal
        }
        target.setTextColor(selectedColor)
    }

}