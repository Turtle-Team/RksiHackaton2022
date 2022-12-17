package com.turtleteam.myapp.ui.fragments.step

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.turtleteam.myapp.R
import com.turtleteam.myapp.adapters.StepAdapter
import com.turtleteam.myapp.data.preferences.UserPreferences
import com.turtleteam.myapp.databinding.FragmentStepBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StepFragment : Fragment() {

    private lateinit var binding: FragmentStepBinding
    private val viewModel: StepViewModel by viewModels()
    private val adapter = StepAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStepBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getInt("id")
        UserPreferences(requireContext()).setUserId()?.let { savedToken ->
            viewModel.getStepsByEvent(id!!, savedToken)
        }

        Log.e("STEP", viewModel.steps.value.toString())

        binding.stepRecyclerView.adapter = adapter

        observableData()

        binding.floatingButtonStep.setOnClickListener {
            findNavController().navigate(R.id.action_stepFragment_to_createStepFragment)
        }
    }

    private fun observableData() {
        viewModel.steps.observe(viewLifecycleOwner) { list ->
            adapter.setData(list)
        }
    }
}