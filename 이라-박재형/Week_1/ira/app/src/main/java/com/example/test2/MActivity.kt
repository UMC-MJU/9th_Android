package com.example.test2

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MActivity : AppCompatActivity() {   // ← MActivity를 쓰고 싶으면 이름만 바꿔도 됨

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupEmotionClicks()   // 우표 클릭 → 토스트
        reorderStamps()        // 순서: 맨 위 → 맨 밑, 밑에서 두번째 → 가운데, 밑에서 세번째 → 맨 위
    }

    private fun setupEmotionClicks() {
        val pairs = listOf(
            R.id.stamp_1 to R.id.tv_1,
            R.id.stamp_2 to R.id.tv_2,
            R.id.stamp_3 to R.id.tv_3,
            R.id.stamp_4 to R.id.tv_4,
            R.id.stamp_5 to R.id.tv_5
        )
        pairs.forEach { (stampId, labelId) ->
            val stamp = findViewById<View>(stampId)
            val label = findViewById<TextView>(labelId)
            label.textAlignment = View.TEXT_ALIGNMENT_CENTER
            stamp.setOnClickListener {
                Toast.makeText(this, label.text.toString(), Toast.LENGTH_SHORT).show()
            }
            label.setOnClickListener { stamp.performClick() }
        }

        findViewById<View>(R.id.btn_next).setOnClickListener {
            Toast.makeText(this, getString(R.string.next), Toast.LENGTH_SHORT).show()
        }
    }

    // 원하는 최종 순서: [5, 2, 3, 4, 1]
    private fun reorderStamps() {
        val container = findViewById<LinearLayout>(R.id.list)

        val s1 = findViewById<View>(R.id.stamp_1)
        val s2 = findViewById<View>(R.id.stamp_2)
        val s3 = findViewById<View>(R.id.stamp_3)
        val s4 = findViewById<View>(R.id.stamp_4)
        val s5 = findViewById<View>(R.id.stamp_5)

        val newOrder = listOf(s5, s2, s3, s4, s1)

        container.removeAllViews()
        newOrder.forEach { container.addView(it) }
    }
}
