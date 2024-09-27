package com.example.customview.lockpattern

import android.graphics.Rect

data class Dot(
    val pointX: Int,
    val pointY: Int,
    val key: String
)

enum class PatternState {
    SUCCESS, ERROR, START, INITIAL
}