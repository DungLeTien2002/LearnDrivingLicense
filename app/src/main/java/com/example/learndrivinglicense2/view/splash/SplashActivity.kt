package com.example.learndrivinglicense2.view.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.learndrivinglicense2.MainActivity
import com.example.learndrivinglicense2.R
import com.example.learndrivinglicense2.databinding.ActivitySplashBinding
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    companion object {
        const val TIME_DELAY = 3000L
        const val TIME_PER_UPDATE = 100L
    }

    private val binding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initView()
    }

    private fun initView() {
        binding.processLoading.max = TIME_DELAY.toInt()
        val countDownTimer = object : CountDownTimer(TIME_DELAY, TIME_PER_UPDATE) {
            override fun onTick(timeRemain: Long) {
                val spendTime = TIME_DELAY - timeRemain
                lifecycleScope.launch {
                    binding.processLoading.progress = spendTime.toInt()
                }
            }

            override fun onFinish() {
                lifecycleScope.launch {
                    //start app
                    startApp()
                }
            }
        }
        countDownTimer.start()
    }

    private fun startApp() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}