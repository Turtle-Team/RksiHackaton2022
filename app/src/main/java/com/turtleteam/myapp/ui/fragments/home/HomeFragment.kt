package com.turtleteam.myapp.ui.fragments.home

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.turtleteam.myapp.R
import com.turtleteam.myapp.adapters.HomeAdapter
import com.turtleteam.myapp.adapters.MembersAdapter
import com.turtleteam.myapp.data.model.event.Events
import com.turtleteam.myapp.data.model.member.MemberModel
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
    var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>? = null
    private val memberAdapter = MembersAdapter()
    private val viewModel: HomeViewModel by viewModels()
    val observer = Observer<Result<MemberModel>> {
        handleUsersList(it)
    }
    private val adapter = HomeAdapter(
        participate = { participate(it) },
        participateEvent = { participateEvent(it) },
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

        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomsht.bottomsheet)
        bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_HIDDEN
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
        bottomSheetCallback()
        observableData()
    }

    private fun bottomSheetCallback() {
        bottomSheetBehavior?.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            @SuppressLint("SwitchIntDef", "UseCompatLoadingForDrawables")
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        binding.bottomsht.bottomsheet.background =
                            resources.getDrawable(R.drawable.bottomsheet_background,
                                Resources.getSystem().newTheme())
                        viewModel.getMembers(viewModel.eventId)
                        binding.bottomsht.participateRecyclerView.adapter = memberAdapter
                        viewModel.usersList.observe(viewLifecycleOwner, observer)
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        resources.getDrawable(R.drawable.bottomsheet_background,
                            Resources.getSystem().newTheme())
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        viewModel.usersList.removeObservers(viewLifecycleOwner)
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
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
                binding.stateView.layoutviewstate.visibility = View.GONE
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

    private fun handleUsersList(result: Result<MemberModel>) {
        when (result) {
            is Result.Success -> {
                Log.e("gggg", result.value.toString())
                memberAdapter.setData(result.value)
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

    private fun participate(events: Events) {
        lifecycleScope.launch {
            viewModel.createMember(events.id)
            delay(800)
            viewModel.getAllEvents()
        }
    }

    private fun participateEvent(events: Events) {
        viewModel.eventId = events.id
        bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
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