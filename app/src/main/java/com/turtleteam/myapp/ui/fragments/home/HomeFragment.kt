package com.turtleteam.myapp.ui.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.turtleteam.myapp.R
import com.turtleteam.myapp.adapters.HomeAdapter
import com.turtleteam.myapp.data.model.event.Events
import com.turtleteam.myapp.data.wrapper.Result
import com.turtleteam.myapp.databinding.FragmentHomeBinding
import com.turtleteam.myapp.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels()
    private val adapter = HomeAdapter(
        participate = { participate() },
        participateEvent = { participateEvent() },
        edit = { editEvent(it) },
        delete = { deleteEvent(it) }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.floatingButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_createEventFragment)
        }
        binding.homeRecyclerView.adapter = adapter
        observableData()
    }

    private fun handleViewStates(result: com.turtleteam.myapp.data.wrapper.Result<List<Events>>) {
        when (result) {
            is Result.ConnectionError,
            is Result.Error -> {
                binding.progressbar.visibility = View.GONE
                binding.stateView.layoutviewstate.visibility = View.VISIBLE
                binding.stateView.refreshButton.setOnClickListener {
                    binding.progressbar.visibility = View.VISIBLE
                    binding.stateView.layoutviewstate.visibility = View.GONE
                    viewModel.getAllEvents()
                }
            }
            is Result.NotFoundError,
            -> {
                binding.progressbar.visibility = View.GONE
            }
            is Result.Loading -> {
                binding.progressbar.visibility = View.VISIBLE
            }
            is Result.Success -> {
                adapter.setData(result.value)
                binding.progressbar.visibility = View.GONE
            }
        }
    }

    private fun observableData() {
        viewModel.events.observe(viewLifecycleOwner) { list ->
            handleViewStates(list)
        }
    }

    private fun participate() {

    }

    private fun participateEvent() {

    }

    private fun editEvent(item: Events) {
        Toast.makeText(requireContext(), item.id.toString(), Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_homeFragment_to_editEventFragment, bundleOf("key" to item.id))

    }

    private fun deleteEvent(id: Int) {
        lifecycleScope.launch {
            viewModel.deleteEvent(id)
            delay(300)
            viewModel.getAllEvents()
        }
    }
}