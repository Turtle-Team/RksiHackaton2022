package com.turtleteam.myapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.turtleteam.myapp.data.model.event.Events
import com.turtleteam.myapp.databinding.EventTimeBinding

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeHolder>() {

    private val events = mutableListOf<Events>()

    class HomeHolder(private val binding: EventTimeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Events) {
            binding.title.text = item.header
            binding.description.text = item.text
            binding.date.text = item.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder {
        val inflater = EventTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeHolder(inflater)
    }

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        holder.bind(events[position])
    }

    override fun getItemCount(): Int = events.size

    fun setData(list: List<Events>) {
        events.clear()
        events.addAll(list)
        notifyDataSetChanged()
    }
}