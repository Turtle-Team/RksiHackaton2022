package com.turtleteam.myapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.turtleteam.myapp.data.model.step.Step
import com.turtleteam.myapp.databinding.EventTimeBinding
import com.turtleteam.myapp.databinding.FragmentStepBinding
import com.turtleteam.myapp.databinding.StepItemBinding

class StepAdapter : RecyclerView.Adapter<StepAdapter.StepHolder>() {

    private val steps = mutableListOf<Step>()

    class StepHolder(private val binding: StepItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Step) {
            binding.title.text = item.header
            binding.description.text = item.text
            binding.time.text = item.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepHolder {
        val inflater =
            StepItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StepHolder(inflater)
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