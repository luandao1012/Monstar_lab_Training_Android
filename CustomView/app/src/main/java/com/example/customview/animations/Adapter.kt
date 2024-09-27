package com.example.customview.animations

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.customview.databinding.ItemBinding


class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {
    private val listItem =
        arrayListOf("a", "b", "c", "d", "1", "2", "3", "4", "5", "6", "7", "8", "9")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.tv.text = listItem[position]
            val scaleAnimator = ObjectAnimator.ofPropertyValuesHolder(
                binding.root,
                PropertyValuesHolder.ofFloat(View.SCALE_X, 0f, 1f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f, 1f)
            )
            scaleAnimator.duration = 500
            scaleAnimator.start()

            val alphaAnimator = ObjectAnimator.ofFloat(
                binding.root,
                View.ALPHA,
                0f,
                1f
            )
            alphaAnimator.duration = 500
            alphaAnimator.start()
        }
    }
}