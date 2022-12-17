package com.turtleteam.myapp.ui.fragments.create_step

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.turtleteam.myapp.databinding.FragmentCreateEventBinding
import com.turtleteam.myapp.databinding.FragmentCreateStepBinding

class CreateStepFragment : Fragment() {

    private lateinit var binding: FragmentCreateStepBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateStepBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}