package com.turtleteam.myapp.ui.fragments.participate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.turtleteam.myapp.databinding.FragmentParticipateBinding


class ParticipateFragment : Fragment() {

    private lateinit var binding: FragmentParticipateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentParticipateBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}