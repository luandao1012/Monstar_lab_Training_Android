package com.example.customview.lockpattern

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.customview.databinding.ActivityLockPatternBinding
import com.example.customview.edittext.EditTextActivity

class LockPatternActivity : AppCompatActivity() {
    companion object {
        private const val PASSWORD = "PASSWORD"
    }

    private val binding by lazy { ActivityLockPatternBinding.inflate(layoutInflater) }
    private val sharedPreferences by lazy { getSharedPreferences(PASSWORD, MODE_PRIVATE) }
    private var password: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
        initListeners()
    }

    private fun initViews() {
        password = sharedPreferences.getString(PASSWORD, null)
        if (password == null) {
            binding.tvTitle.text = "Đặt mật khẩu"
        } else {
            binding.tvTitle.text = "Nhập mật khẩu"
        }
    }

    private fun initListeners() {
        binding.lockPattern.setPasswordListener {
            if (password == null) {
                sharedPreferences.edit().putString(PASSWORD, it).apply()
                binding.lockPattern.updatePatternState(PatternState.SUCCESS)
                object : CountDownTimer(1000, 2000) {
                    override fun onTick(millisUntilFinished: Long) = Unit

                    override fun onFinish() {
                        recreate()
                    }
                }.start()
            } else {
                if (password == it) {
                    Toast.makeText(this, "Mật khẩu đúng", Toast.LENGTH_SHORT).show()
                    binding.lockPattern.updatePatternState(PatternState.SUCCESS)
                } else {
                    binding.lockPattern.updatePatternState(PatternState.ERROR)
                    Toast.makeText(this, "Mật khẩu sai", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.btnToActivity3.setOnClickListener {
            startActivity(Intent(this, EditTextActivity::class.java))
        }
    }
}