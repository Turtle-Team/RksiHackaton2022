package com.turtleteam.myapp.ui.fragments.participate

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.turtleteam.myapp.adapters.MemberUserAdapter
import com.turtleteam.myapp.data.model.event.Events
import com.turtleteam.myapp.data.model.member.MemberModel
import com.turtleteam.myapp.data.wrapper.Result
import com.turtleteam.myapp.databinding.FragmentParticipateBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ParticipateFragment : Fragment() {

    private val viewModel by viewModels<ParticipateVIewModel>()
    private lateinit var binding: FragmentParticipateBinding
    private val adapter = MemberUserAdapter(
        notSubscribe = {it},
        delete = {}
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentParticipateBinding.inflate(layoutInflater, container, false)

        viewModel.getMyEvents()

        return binding.root
    }

    private fun handleUsersList(result: Result<MemberModel>) {
        when (result) {
            is Result.Success -> {
                viewModel.listIds.clear()
                result.value.forEach {
                    viewModel.listIds.add(it.event_id)
                }
                viewModel.getAllEvents()
            }
            is Result.NotFoundError,
            is Result.ConnectionError,
            is Result.Error,
            is Result.Loading,
            -> {
            }
        }
    }

    fun unSub(item: Events){

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
                val myMembers = mutableListOf<Events>()

                for (i in result.value) {
                    for (f in viewModel.listIds) {
                       if (i.id==f){
                           myMembers.add(i)
                       }
                    }
                }
                Log.e("aaaa", myMembers.toString())
                adapter.submitList(myMembers)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pRecycler.adapter = adapter

        observableData()
    }

    fun observableData() {
        viewModel.myEvents.observe(viewLifecycleOwner) {
            handleUsersList(it)
        }
        viewModel.myEvents2.observe(viewLifecycleOwner) {
            handleViewStates(it)
        }
    }
}