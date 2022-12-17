package com.turtleteam.myapp.adapters

import androidx.recyclerview.widget.DiffUtil

class DiffUtils<T : Any>: DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}