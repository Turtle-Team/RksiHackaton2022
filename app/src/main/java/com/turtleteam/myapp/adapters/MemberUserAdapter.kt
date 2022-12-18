package com.turtleteam.myapp.adapters

import android.media.metrics.Event
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
import com.turtleteam.myapp.data.model.step.Step
import com.turtleteam.myapp.databinding.EventTimeBinding
import com.turtleteam.myapp.databinding.StepItemBinding

class MemberUserAdapter(
    private val notSubscribe: (item: Events) -> Unit,
    private val delete: (item: Events) -> Unit,
) : ListAdapter<Events, MemberUserAdapter.MemberUserHolder>(DiffUtils()) {

    class MemberUserHolder(
        private val binding: EventTimeBinding,
        private val notSubscribe: (item: Events) -> Unit,
        private val delete: (item: Events) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Events) {
            binding.title.text = item.header
            binding.description.text = item.text
            binding.date.text = item.date_start

            binding.itemCard.setOnLongClickListener {
                showPopup(binding.itemCard, item)
                return@setOnLongClickListener true
            }
        }

        private fun showPopup(view: View, item1: Events) {
            val popup = PopupMenu(view.context, view)
            popup.inflate(R.menu.member_menu)
            popup.setOnMenuItemClickListener { item: MenuItem? ->
                when (item?.itemId) {
                    R.id.notsubscribe -> {
                        notSubscribe(item1)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberUserHolder {
        val inflater =
            EventTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemberUserAdapter.MemberUserHolder(inflater, notSubscribe, delete)
    }

    override fun onBindViewHolder(holder: MemberUserAdapter.MemberUserHolder, position: Int) {
        holder.bind(currentList[position])
    }
}