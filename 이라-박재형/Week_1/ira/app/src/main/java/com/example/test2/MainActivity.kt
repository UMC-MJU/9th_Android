package com.example.test2

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val root = findViewById<View>(R.id.MainActivity)
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }

        val iv1 = findViewById<ImageView>(R.id.iv_1)
        val iv2 = findViewById<ImageView>(R.id.iv_2)
        val iv3 = findViewById<ImageView>(R.id.iv_3)
        val iv4 = findViewById<ImageView>(R.id.iv_4)
        val iv5 = findViewById<ImageView>(R.id.iv_5)

        val tv1 = findViewById<TextView>(R.id.tv_1)
        val tv2 = findViewById<TextView>(R.id.tv_2)
        val tv3 = findViewById<TextView>(R.id.tv_3)
        val tv4 = findViewById<TextView>(R.id.tv_4)
        val tv5 = findViewById<TextView>(R.id.tv_5)

        iv1.setImageResource(R.drawable.group_462); tv1.text = "더없이 행복한 하루였어요"
        iv2.setImageResource(R.drawable.group_573); tv2.text = "들뜨고 흥분돼요"
        iv3.setImageResource(R.drawable.group_574); tv3.text = "평범한 하루였어요"
        iv4.setImageResource(R.drawable.group_575); tv4.text = "생각이 많아지고 불안해요"
        iv5.setImageResource(R.drawable.group_576); tv5.text = "부글부글 화가 나요"

        // 접근성/토스트 메시지 일치
        iv1.contentDescription = tv1.text
        iv2.contentDescription = tv2.text
        iv3.contentDescription = tv3.text
        iv4.contentDescription = tv4.text
        iv5.contentDescription = tv5.text

        // --- 클릭 → 토스트 ---
        val pairs = listOf(
            R.id.stamp_1 to R.id.tv_1,
            R.id.stamp_2 to R.id.tv_2,
            R.id.stamp_3 to R.id.tv_3,
            R.id.stamp_4 to R.id.tv_4,
            R.id.stamp_5 to R.id.tv_5
        )
        pairs.forEach { (stampId, textId) ->
            val stamp = findViewById<View>(stampId)
            val label = findViewById<TextView>(textId)
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
}

