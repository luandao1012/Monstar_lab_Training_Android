package com.example.customview.edittext

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.SeekBar
import com.example.customview.R
import com.example.customview.databinding.ColorPickerDialogBinding
import com.example.customview.databinding.CustomEditTextBinding

class CustomEditText constructor(context: Context, attrs: AttributeSet) :
    LinearLayout(context, attrs) {
    private val binding: CustomEditTextBinding

    init {
        val typeArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.CustomEditText, 0, 0)
        val color = typeArray.getColor(0, 0)
        binding = CustomEditTextBinding.inflate(LayoutInflater.from(context), this, true)
        binding.iv.setBackgroundColor(color)
        binding.edt.setTextColor(color)
        binding.iv.setOnClickListener {
            (it.background as? ColorDrawable)?.color?.let { ivColor ->
                showColorPickerDialog(ivColor) { color ->
                    binding.iv.setBackgroundColor(color)
                    binding.edt.setTextColor(color)
                }
            }
        }
    }

    private fun showColorPickerDialog(
        color: Int,
        callback: (color: Int) -> Unit
    ) {
        val binding = ColorPickerDialogBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(context)
        builder.setView(binding.root)
        binding.image.setBackgroundColor(color)
        var redValue = Color.red(color)
        var greenValue = Color.green(color)
        var blueValue = Color.blue(color)
        binding.seekBarR.progress = redValue
        binding.seekBarG.progress = greenValue
        binding.seekBarB.progress = blueValue
        val seekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                when (seekBar.id) {
                    R.id.seekBarR -> redValue = progress
                    R.id.seekBarG -> greenValue = progress
                    R.id.seekBarB -> blueValue = progress
                }
                binding.image.setBackgroundColor(Color.rgb(redValue, greenValue, blueValue))
            }

            override fun onStartTrackingTouch(p0: SeekBar?) = Unit
            override fun onStopTrackingTouch(p0: SeekBar?) = Unit

        }
        binding.seekBarR.setOnSeekBarChangeListener(seekBarChangeListener)
        binding.seekBarG.setOnSeekBarChangeListener(seekBarChangeListener)
        binding.seekBarB.setOnSeekBarChangeListener(seekBarChangeListener)
        builder.setPositiveButton("Chọn") { _, _ ->
            callback.invoke(Color.rgb(redValue, greenValue, blueValue))
        }
        builder.setNegativeButton("Hủy", null)
        builder.show()
    }

}