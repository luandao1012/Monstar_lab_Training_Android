package com.example.customview.lockpattern

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class CustomLockPatternView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {
    companion object {
        private const val MIN_DOT = 4
        private const val MAX_DOT = 9
        private const val DEFAULT_COLOR = Color.DKGRAY
        private const val ERROR_COLOR = Color.RED
        private const val SUCCESS_COLOR = Color.GREEN
        private const val PADDING_DOT = 50
        private const val RADIUS_DOT = 20
    }

    private var touchedPointX = 0f
    private var touchedPointY = 0f
    private val dotKeyList = arrayOf(
        arrayOf("1", "2", "3"),
        arrayOf("4", "5", "6"),
        arrayOf("7", "8", "9")
    )
    private var dotList = arrayListOf<Dot>()
    private var selectedDotList = arrayListOf<Dot>()
    private var state = PatternState.INITIAL
    private var password = ""
    private var setPasswordCallback: ((password: String) -> Unit)? = null
    private val paintLine by lazy {
        Paint().apply {
            style = Paint.Style.FILL
            strokeWidth = 12f
        }
    }
    private val paintDot by lazy {
        Paint().apply {
            style = Paint.Style.FILL
            color = DEFAULT_COLOR
        }
    }

    fun setPasswordListener(callback: ((password: String) -> Unit)? = null) {
        setPasswordCallback = callback
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawPatternView(canvas = canvas)
        drawLine(canvas)

        if (state == PatternState.ERROR) {
            object : CountDownTimer(1000, 2000) {
                override fun onTick(millisUntilFinished: Long) = Unit

                override fun onFinish() {
                    reset()
                }
            }.start()
        }
    }


    private fun drawLine(canvas: Canvas) {
        selectedDotList.forEachIndexed { index, _ ->
            if (index + 1 < selectedDotList.size) {
                canvas.drawLine(
                    selectedDotList[index].pointX.toFloat(),
                    selectedDotList[index].pointY.toFloat(),
                    selectedDotList[index + 1].pointX.toFloat(),
                    selectedDotList[index + 1].pointY.toFloat(),
                    paintLine
                )
            }
        }

        if (state == PatternState.START) {
            canvas.drawLine(
                selectedDotList.last().pointX.toFloat(),
                selectedDotList.last().pointY.toFloat(),
                touchedPointX, touchedPointY, paintLine
            )
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            touchedPointX = event.x
            touchedPointY = event.y
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (state == PatternState.ERROR) return false
                    reset()
                    if (isTouchedDot(touchedPointX, touchedPointY)) {
                        updatePatternState(PatternState.START)
                    }
                }

                MotionEvent.ACTION_UP -> {
                    if (selectedDotList.size != 0 && selectedDotList.size >= MIN_DOT) {
                        setPassword()
                    } else if (selectedDotList.size != 0) {
                        updatePatternState(PatternState.ERROR)
                    }
                }

                MotionEvent.ACTION_MOVE -> {
                    if (state == PatternState.START && selectedDotList.size < MAX_DOT) {
                        isTouchedDot(touchedPointX, touchedPointY)
                        invalidate()
                    }
                }
            }
            return true
        }
        return false
    }

    private fun drawPatternView(
        rowSize: Int = 3,
        columnSize: Int = 3,
        canvas: Canvas
    ) {
        for (rowIndex in 0 until rowSize) {
            for (columnIndex in 0 until columnSize) {
                val x = width / 6 * (columnIndex * 2 + 1)
                val y = height / 6 * (rowIndex * 2 + 1)
                val dot = Dot(x, y, dotKeyList[rowIndex][columnIndex])
                dotList.add(dot)
                if (selectedDotList.contains(dot)) {
                    canvas.drawCircle(x.toFloat(), y.toFloat(), RADIUS_DOT.toFloat(), paintLine)
                } else {
                    canvas.drawCircle(x.toFloat(), y.toFloat(), RADIUS_DOT.toFloat(), paintDot)
                }
            }
        }
    }

    private fun isTouchedDot(pointX: Float, pointY: Float): Boolean {
        val touchedDot = getDotByPoint(pointX, pointY) ?: return false
        if (isDotSelected(touchedDot)) return false
        selectedDotList.lastOrNull()?.let { lastSelectedDot ->
            val pointX = lastSelectedDot.pointX + touchedDot.pointX
            val pointY = lastSelectedDot.pointY + touchedDot.pointY
            getDotByPoint(pointX / 2f, pointY / 2f)?.let {
                if (!isDotSelected(it)) {
                    selectedDotList.add(it)
                }
            }
        }
        if (selectedDotList.size < MAX_DOT) {
            selectedDotList.add(touchedDot)
        }
        return true
    }

    private fun getDotByPoint(pointX: Float, pointY: Float) = dotList.firstOrNull {
        (it.pointX - PADDING_DOT <= pointX)
                && (it.pointX + PADDING_DOT >= pointX)
                && (it.pointY + PADDING_DOT >= pointY)
                && (it.pointY - PADDING_DOT <= pointY)
    }

    private fun isDotSelected(dot: Dot) = selectedDotList.firstOrNull { it == dot } != null
    private fun setPassword() {
        selectedDotList.forEach { password += it.key }
        setPasswordCallback?.invoke(password)
    }

    private fun reset() {
        updatePatternState(PatternState.INITIAL)
        selectedDotList.clear()
        password = ""
        invalidate()
    }

    fun updatePatternState(state: PatternState) {
        this.state = state
        val color = when (state) {
            PatternState.SUCCESS -> SUCCESS_COLOR
            PatternState.ERROR -> ERROR_COLOR
            else -> DEFAULT_COLOR
        }
        paintLine.color = color
        invalidate()
    }
}