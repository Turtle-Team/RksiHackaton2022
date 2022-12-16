package com.turtleteam.myapp.ui.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.turtleteam.myapp.R
import com.turtleteam.myapp.adapters.HomeAdapter
import com.turtleteam.myapp.databinding.FragmentHomeBinding
import com.turtleteam.myapp.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels()
    private val adapter = HomeAdapter(
        participate = { participate() },
        participateEvent = { participateEvent() },
        edit = { editEvent() },
        delete = { deleteEvent(it) }
    )

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.floatingButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_createEventFragment)
        }
    }

    private fun observableData() {
        viewModel.events.observe(viewLifecycleOwner) { list ->
            adapter.setData(list)
        }
    }

    private fun participate() {

    }

    private fun participateEvent() {

    }

    private fun editEvent() {

    }

    private fun deleteEvent(id: Int) {
        lifecycleScope.launch {
            viewModel.deleteEvent(id)
            delay(100)
            viewModel.getAllEvents()
        }
    }
}