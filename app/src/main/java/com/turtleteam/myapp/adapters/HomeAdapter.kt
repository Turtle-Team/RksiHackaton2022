package com.turtleteam.myapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.turtleteam.myapp.R
import com.turtleteam.myapp.data.model.event.Events
import com.turtleteam.myapp.databinding.EventTimeBinding

class HomeAdapter(
    private val participate: (item: Int) -> Unit,
    private val participateEvent: (item: Int) -> Unit,
    private val edit: (item: Int) -> Unit,
    private val delete: (item: Int) -> Unit,
) : RecyclerView.Adapter<HomeAdapter.HomeHolder>() {

    private val events = mutableListOf<Events>()

    class HomeHolder(
        private val binding: EventTimeBinding,
        private val participate: (item: Int) -> Unit,
        private val participateEvent: (item: Int) -> Unit,
        private val edit: (item: Int) -> Unit,
        private val delete: (item: Int) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Events) {
            binding.title.text = item.header
            binding.description.text = item.text
            binding.date.text = item.date

            binding.itemCard.setOnLongClickListener {
                showPopup(binding.itemCard, item)
                return@setOnLongClickListener true
            }
        }

        private fun showPopup(view: View, item1: Events) {
            val popup = PopupMenu(view.context, view)
            popup.inflate(R.menu.event_menu)
            popup.setOnMenuItemClickListener { item: MenuItem? ->
                when (item?.itemId) {
                    R.id.participate -> {
                        Log.e("menu", "PARTICIPATE")
                    }
                    R.id.participateEvent -> {
                        Log.e("menu", "PARTICIPATE EVENT")
                    }
                    R.id.editEvent -> {
                        Log.e("menu", "EDIT EVENT")
                    }
                    R.id.deleteEvent -> {
                        Log.e("menu", "DELETE EVENT")
                    }
                }
                true
            }
            popup.show()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder {
        val inflater = EventTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeHolder(inflater, participate, participateEvent, edit, delete)
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