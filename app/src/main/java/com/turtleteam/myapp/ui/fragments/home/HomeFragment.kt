package com.turtleteam.myapp.ui.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.turtleteam.myapp.adapters.HomeAdapter
import com.turtleteam.myapp.databinding.FragmentHomeBinding
import com.turtleteam.myapp.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels()
    private val adapter = HomeAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        binding.homeRecyclerView.adapter = adapter

        viewModel.getAllEvents()
        observableData()

        return binding.root
    }

    private fun observableData() {
        viewModel.events.observe(viewLifecycleOwner) { list ->
            adapter.setData(list)
        }
    }
}