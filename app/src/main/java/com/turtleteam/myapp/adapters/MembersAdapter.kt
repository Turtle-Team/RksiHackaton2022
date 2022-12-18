package com.turtleteam.myapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.turtleteam.myapp.data.model.member.MemberModel
import com.turtleteam.myapp.data.model.member.MemberModelItem
import com.turtleteam.myapp.data.model.member.User
import com.turtleteam.myapp.databinding.ItemParticipantBinding
import com.turtleteam.myapp.databinding.ItemUrlBinding

class MembersAdapter:RecyclerView.Adapter<MembersAdapter.MemberViewHolder>() {

    var myList = emptyList<MemberModelItem>()

    class MemberViewHolder(private val binding: ItemParticipantBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: MemberModelItem) = with(binding){
            fioTextView.text = item.user.fio
            emailTextView.text = item.user.email
            statusTextView.text = item.user.status
            postTextView.text = item.user.post
            oranizationTextView.text = item.user.organization
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val inflater = ItemParticipantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemberViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        holder.bind(myList[position])
    }

    override fun getItemCount(): Int = myList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<MemberModelItem>){
        myList = list
        notifyDataSetChanged()
    }
}