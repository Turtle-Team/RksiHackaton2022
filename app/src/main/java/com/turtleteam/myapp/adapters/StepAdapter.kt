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
import com.turtleteam.myapp.data.model.step.Step
import com.turtleteam.myapp.databinding.StepItemBinding
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


class StepAdapter(
    private val edit: (item: Step) -> Unit,
    private val delete: (item: Step) -> Unit,
    private val url: (item: String) -> Unit,
) : ListAdapter<Step, StepAdapter.StepHolder>(DiffUtils()) {


    class StepHolder(
        private val binding: StepItemBinding,
        private val edit: (item: Step) -> Unit,
        private val delete: (item: Step) -> Unit,
        private val url: (item: String) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("SetTextI18n")
        fun bind(item: Step) {

            val date_start = item.date_start
            val zoneId = ZoneId.systemDefault()
            val localDate1 = LocalDateTime.parse(date_start, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            val offset1 = ZonedDateTime.of(localDate1, zoneId).toOffsetDateTime()

            Log.e("DATE START", offset1.toString())


            val date_end = item.date_end
//            Log.e("DATE END", offset1.toString())
//            val offset2 = OffsetDateTime.parse(date_end, DateTimeFormatter.ofPattern("yyy-MM-dd'T'HH:mm:ss"))



            //            val actual1 = OffsetDateTime.parse(date_start, DateTimeFormatter.ISO_DATE_TIME)
//            val formatter1 = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss")
//            val formatDateTime1 = actual1.format(formatter1)

            //            val actual2 = OffsetDateTime.parse(date_end, DateTimeFormatter.ISO_DATE_TIME)
//            val formatter2 = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss")
            //            val formatDateTime2 = actual2.format(formatter2)
            binding.title.text = item.header
            binding.description.text = item.text
            binding.time.text = "$date_start : $date_end"

            binding.itemCard.setOnLongClickListener {
                showPopup(binding.itemCard, item)
                return@setOnLongClickListener true
            }
        }

        private fun showPopup(view: View, item1: Step) {
            val popup = PopupMenu(view.context, view)
            popup.inflate(R.menu.step_menu)
            popup.setOnMenuItemClickListener { item: MenuItem? ->
                when (item?.itemId) {
                    R.id.urlStep -> {
                        url(item1.url)
                    }
                    R.id.editStep -> {
                        Log.e("menu", item1.id.toString())
                        edit(item1)
                    }
                    R.id.deleteStep -> {
                        Log.e("menu", "DELETE id: ${item1.id}")
                        delete(item1)
                    }
                }
                true
            }
            popup.show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepHolder {
        val inflater =
            StepItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StepHolder(inflater, edit, delete, url)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: StepHolder, position: Int) {
        holder.bind(currentList[position])
    }
}