package com.turtleteam.myapp.ui.fragments.participate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.turtleteam.myapp.adapters.MemberUserAdapter
import com.turtleteam.myapp.databinding.FragmentParticipateBinding


class ParticipateFragment : Fragment() {

    private lateinit var binding: FragmentParticipateBinding
    private val adapter = MemberUserAdapter(
        notSubscribe = {},
        delete = {}
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentParticipateBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.participateRecyclerView.adapter = adapter
    }
}