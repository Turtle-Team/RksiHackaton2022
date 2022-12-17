package com.turtleteam.myapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.turtleteam.myapp.databinding.ItemUrlBinding

class UrlsAdapter(private val onClick: (item: String) -> Unit) :
    ListAdapter<String, UrlsAdapter.UrlViewHolder>(DiffUtils()) {

    class UrlViewHolder(
        private val binding: ItemUrlBinding,
        private val onClick: (item: String) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) = with(binding) {
            Log.e("aaaa", item)
            urltext.text = item
            urltext.setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UrlViewHolder {
        val inflater = ItemUrlBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UrlViewHolder(inflater, onClick)
    }

    override fun onBindViewHolder(holder: UrlViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}