package com.example.flo_clone_app.activity // (사용자님의 패키지 이름)

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen // 1. import 추가
import com.example.flo_clone_app.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        // 2. super.onCreate() 보다 먼저 호출
        installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 3. Handler로 2초 지연 후 MainActivity 실행
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            // finish()를 호출하여 스플래시 액티비티를 종료
            finish()
        }, 2000) // 2초 지연
    }
}