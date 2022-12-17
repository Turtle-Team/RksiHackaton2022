package com.turtleteam.myapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.turtleteam.myapp.R
import com.turtleteam.myapp.data.model.step.Step
import com.turtleteam.myapp.databinding.StepItemBinding

class StepAdapter(
    private val edit: (item: Step) -> Unit,
    private val delete: (item: Step) -> Unit,
) : RecyclerView.Adapter<StepAdapter.StepHolder>() {

    private val steps = mutableListOf<Step>()

    class StepHolder(
        private val binding: StepItemBinding,
        private val edit: (item: Step) -> Unit,
        private val delete: (item: Step) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Step) {
            binding.title.text = item.header
            binding.description.text = item.text
            binding.time.text = item.date

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
                    R.id.editStep -> {
                        Log.e("menu", item1.id.toString())
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
        return StepHolder(inflater, edit, delete)
    }

    override fun onBindViewHolder(holder: StepHolder, position: Int) {
        holder.bind(steps[position])
    }

    override fun getItemCount(): Int = steps.size


    fun setData(list: List<Step>) {
        steps.clear()
        steps.addAll(list)
        notifyDataSetChanged()
    }
}