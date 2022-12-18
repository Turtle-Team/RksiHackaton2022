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
import com.turtleteam.myapp.data.model.users.AuthRequestBody
import com.turtleteam.myapp.data.preferences.UserPreferences
import com.turtleteam.myapp.data.wrapper.Result
import com.turtleteam.myapp.databinding.FragmentHomeBinding
import com.turtleteam.myapp.dialogs.EventDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var userStatus: String

    private val viewModel: HomeViewModel by viewModels()
    private val adapter = HomeAdapter(
        participate = { participate() },
        participateEvent = { participateEvent() },
        edit = { editEvent(it) },
        delete = { deleteEvent(it) },
        url = { urlEvent(it) },
        openSteps = { openSteps(it) }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        UserPreferences(requireContext()).setUserStatus().apply {
            if (this != null) {
                userStatus = this
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (userStatus == "Организатор") {
            binding.floatingButton.apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    findNavController().navigate(R.id.action_homeFragment_to_createEventFragment)
                }
            }
        }

        binding.homeRecyclerView.adapter = adapter
        observableData()
    }

    private fun handleViewStates(result: Result<List<Events>>) {
        when (result) {
            is Result.ConnectionError,
            is Result.Error,
            -> {
                binding.progressbar.visibility = View.GONE
                binding.stateView.layoutviewstate.visibility = View.VISIBLE
                handleViewStates(Result.Success(emptyList()))
                binding.stateView.refreshButton.setOnClickListener {
                    binding.progressbar.visibility = View.VISIBLE
                    binding.stateView.layoutviewstate.visibility = View.GONE
                    viewModel.getAllEvents()
                }
            }
            is Result.NotFoundError,
            -> {
                binding.progressbar.visibility = View.GONE
                handleViewStates(Result.Success(emptyList()))
            }
            is Result.Loading -> {
                binding.progressbar.visibility = View.VISIBLE
            }
            is Result.Success -> {
                adapter.submitList(result.value)
                binding.progressbar.visibility = View.GONE
                lifecycleScope.launch {
                    delay(10000)
                    viewModel.getAllEvents()
                }
                Log.e("aaaa", "Повторный запрос")
            }
        }
    }

    private fun handleUserData(result: Result<AuthRequestBody>) {
        val prefs = UserPreferences(requireContext())
        when (result) {
            is Result.Success -> {
                Log.e("userrrr", result.value.status)
                prefs.getUserStatus(result.value.status)
                userStatus = result.value.status
                if (result.value.status == "Организатор") {
                    userStatus = result.value.status
                    binding.floatingButton.apply {
                        visibility = View.VISIBLE
                        setOnClickListener {
                            findNavController().navigate(R.id.action_homeFragment_to_createEventFragment)
                        }
                    }
                }
            }
            is Result.NotFoundError,
            is Result.ConnectionError,
            is Result.Error,
            is Result.Loading,
            -> {
            }
        }
    }

    private fun observableData() {
        viewModel.events.observe(viewLifecycleOwner) { list ->
            handleViewStates(list)
        }
        viewModel.user.observe(viewLifecycleOwner) { user ->
            handleUserData(user)
        }
    }

    private fun urlEvent(url: String) {
        val list = url.split(" ")
        EventDialog.urls = list
        EventDialog().show(parentFragmentManager, "Ссылки")
    }

    private fun participate() {

    }

    private fun participateEvent() {

    }

    private fun editEvent(item: Events) {
        if (userStatus == "Организатор") {
            findNavController().navigate(
                R.id.action_homeFragment_to_editEventFragment,
                bundleOf(
                    "key" to item.id,
                    "header" to item.header,
                    "text" to item.text,
                    "url" to item.url,
                    "date_start" to item.date_start
                )
            )
        } else {
            Toast.makeText(requireContext(), "Недостаточно прав", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteEvent(id: Int) {
        if (userStatus == "Организатор") {
            lifecycleScope.launch {
                viewModel.deleteEvent(id)
                delay(800)
                viewModel.getAllEvents()
            }
        } else {
            Toast.makeText(requireContext(), "Недостаточно прав", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openSteps(id: Int) {
        UserPreferences(requireContext()).getEventId(id)
        findNavController().navigate(R.id.action_homeFragment_to_stepFragment, bundleOf("id" to id))
    }
}