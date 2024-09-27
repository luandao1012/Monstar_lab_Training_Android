package com.example.customview.clock

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.customview.databinding.ActivityMainBinding
import com.example.customview.lockpattern.LockPatternActivity

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initListeners()
    }

    private fun initListeners() {
        binding.btnToActivity2.setOnClickListener {
            startActivity(Intent(this, LockPatternActivity::class.java))
        }
    }

}