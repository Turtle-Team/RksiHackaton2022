package com.turtleteam.myapp.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.turtleteam.myapp.R
import com.turtleteam.myapp.data.model.event.Events
import com.turtleteam.myapp.databinding.EventTimeBinding

class HomeAdapter(
    private val participate: (item: Int) -> Unit,
    private val participateEvent: (item: Int) -> Unit,
    private val edit: (item: Events) -> Unit,
    private val delete: (item: Int) -> Unit,
) : ListAdapter<Events, HomeAdapter.HomeHolder>(DiffUtils()) {

    class HomeHolder(
        private val binding: EventTimeBinding,
        private val participate: (item: Int) -> Unit,
        private val participateEvent: (item: Int) -> Unit,
        private val edit: (item: Events) -> Unit,
        private val delete: (item: Int) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind(item: Events) {

            val date = item.date
//            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
//            val dateEd = dateFormat.parse(date)

//            Log.e("DATE", date.toString())


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
                        Log.e("menu", "PARTICIPATE id: ${item1.id}")
                    }
                    R.id.participateEvent -> {
                        Log.e("menu", "PARTICIPATE EVENT")
                    }
                    R.id.editEvent -> {
                        edit(item1)
                        Log.e("menu", "EDIT EVENT")
                    }
                    R.id.deleteEvent -> {
                        Log.e("menu", "DELETE EVENT: ${item1.id}")
                        delete(item1.id)
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
        holder.bind(currentList[position])
    }
}