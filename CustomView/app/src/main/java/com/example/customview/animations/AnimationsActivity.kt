package com.example.customview.animations

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.customview.databinding.ActivityAnimationsBinding

class AnimationsActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAnimationsBinding.inflate(layoutInflater) }
    private lateinit var objectAnimator: ObjectAnimator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
        initListeners()
    }

    private fun initViews() {
        binding.rv.adapter = Adapter()
    }

    private fun initListeners() {
        binding.btnZoomIn.setOnClickListener {
            objectAnimator = ObjectAnimator.ofPropertyValuesHolder(
                it,
                PropertyValuesHolder.ofFloat(View.SCALE_X, it.scaleX, it.scaleX * 1.25f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, it.scaleY, it.scaleY * 1.25f)
            )
            objectAnimator.duration = 2000
            objectAnimator.start()
        }
        binding.btnZoomOut.setOnClickListener {
            objectAnimator = ObjectAnimator.ofPropertyValuesHolder(
                it,
                PropertyValuesHolder.ofFloat(View.SCALE_X, it.scaleX, it.scaleX * 0.75f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, it.scaleY, it.scaleY * 0.75f)
            )
            objectAnimator.duration = 2000
            objectAnimator.start()
        }
        binding.txtFadeIn.setOnClickListener {
            objectAnimator = ObjectAnimator.ofFloat(it, View.ALPHA, 0f, 1f)
            objectAnimator.duration = 2000
            objectAnimator.start()
        }
        binding.txtFadeOut.setOnClickListener {
            objectAnimator = ObjectAnimator.ofFloat(it, View.ALPHA, 1f, 0.1f)
            objectAnimator.duration = 2000
            objectAnimator.start()
        }
        binding.txtBlink.setOnClickListener {
            objectAnimator = ObjectAnimator.ofFloat(it, View.ALPHA, 1f, 0.1f)
            objectAnimator.duration = 2000
            objectAnimator.repeatMode = ValueAnimator.REVERSE
            objectAnimator.repeatCount = ValueAnimator.INFINITE
            objectAnimator.start()
        }
        binding.txtRotate.setOnClickListener {
            objectAnimator = ObjectAnimator.ofFloat(it, View.ROTATION, 0f, 45f)
            objectAnimator.duration = 2000
            objectAnimator.start()
        }
        binding.txtMove.setOnClickListener {
            objectAnimator = ObjectAnimator.ofFloat(
                it,
                View.TRANSLATION_X,
                it.translationX,
                it.translationX + 30f
            )
            objectAnimator.duration = 2000
            objectAnimator.start()
        }
    }
}