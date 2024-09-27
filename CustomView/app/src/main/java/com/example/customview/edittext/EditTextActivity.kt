package com.example.customview.edittext

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.customview.animations.AnimationsActivity
import com.example.customview.databinding.ActivityEditTextBinding

class EditTextActivity : AppCompatActivity() {
    private val binding by lazy { ActivityEditTextBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initListeners()
    }

    private fun initListeners() {
        binding.btnToAnimations.setOnClickListener {
            startActivity(Intent(this, AnimationsActivity::class.java))
        }
    }
}