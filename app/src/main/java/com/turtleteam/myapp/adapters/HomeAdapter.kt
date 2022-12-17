package com.turtleteam.myapp.adapters

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.turtleteam.myapp.R
import com.turtleteam.myapp.data.model.event.Events
import com.turtleteam.myapp.databinding.EventTimeBinding
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class HomeAdapter(
    private val participate: (item: Int) -> Unit,
    private val participateEvent: (item: Int) -> Unit,
    private val edit: (item: Events) -> Unit,
    private val delete: (item: Int) -> Unit,
    private val url: (item: String) -> Unit,
    private val openSteps: (item: Int) -> Unit
    ) : ListAdapter<Events, HomeAdapter.HomeHolder>(DiffUtils()) {

    class HomeHolder(
        private val binding: EventTimeBinding,
        private val participate: (item: Int) -> Unit,
        private val participateEvent: (item: Int) -> Unit,
        private val edit: (item: Events) -> Unit,
        private val delete: (item: Int) -> Unit,
        private val url: (item: String) -> Unit,
        private val openSteps: (item: Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("SimpleDateFormat")
        fun bind(item: Events) {

            val date = item.date_start
            val actual = OffsetDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME)
            val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss")
            val formatDateTime = actual.format(formatter)


            binding.title.text = item.header
            binding.description.text = item.text
            binding.date.text = formatDateTime

            binding.itemCard.setOnLongClickListener {
                showPopup(binding.itemCard, item)
                return@setOnLongClickListener true
            }

            binding.itemCard.setOnClickListener {
                openSteps(item.id)
            }
        }

        private fun showPopup(view: View, item1: Events) {
            val popup = PopupMenu(view.context, view)
            popup.inflate(R.menu.event_menu)
            popup.setOnMenuItemClickListener { item: MenuItem? ->
                when (item?.itemId) {
                    R.id.urlEvent ->{
                        Log.e("menu", item1.url)
                        url(item1.url)
                    }
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
        return HomeHolder(inflater, participate, participateEvent, edit, delete, url, openSteps)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        holder.bind(currentList[position])
    }
}