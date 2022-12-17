package com.turtleteam.myapp.ui.fragments.step

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
import com.turtleteam.myapp.adapters.StepAdapter
import com.turtleteam.myapp.data.model.step.Step
import com.turtleteam.myapp.data.preferences.UserPreferences
import com.turtleteam.myapp.data.wrapper.Result
import com.turtleteam.myapp.databinding.FragmentStepBinding
import com.turtleteam.myapp.dialogs.EventDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StepFragment : Fragment() {

    private lateinit var binding: FragmentStepBinding
    private val viewModel: StepViewModel by viewModels()
    private val adapter = StepAdapter(
        edit = { editStep(it) },
        delete = { deleteStep(id = it.event_id, stepId = it.id) },
        url = { urlStep(it) }
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentStepBinding.inflate(layoutInflater, container, false)
        arguments?.getInt("id").let {
            if (it != null) {
                viewModel.eventId = it
            }
        }
        UserPreferences(requireContext()).apply {
            viewModel.mtoken = setUserToken().toString()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        UserPreferences(requireContext()).setUserToken()?.let { savedToken ->
            if (viewModel.eventId != null) {
                viewModel.getStepsByEvent(viewModel.eventId, savedToken)
            }
        }

        Log.e("STEP", viewModel.steps.value.toString())

        binding.stepRecyclerView.adapter = adapter

        observableData()

        binding.floatingButtonStep.setOnClickListener {
            findNavController().navigate(R.id.action_stepFragment_to_createStepFragment,
                bundleOf("key" to id))
            Toast.makeText(requireContext(), id.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun observableData() {
        viewModel.steps.observe(viewLifecycleOwner) { list ->
            handleViewStates(list)
        }
    }

    private fun editStep(item: Step) {
        findNavController().navigate(
            R.id.action_stepFragment_to_editStepFragment,
//            bundleOf(
//                "key" to item.id,
//                "header" to item.header,
//                "text" to item.text,
//                "url" to item.url,
//                "date_start" to item.date_start
//            )
        )
    }

    private fun handleViewStates(result: Result<List<Step>>) {
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
                    viewModel.getStepsByEvent(viewModel.eventId, viewModel.mtoken)
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
                    viewModel.getStepsByEvent(viewModel.eventId, viewModel.mtoken)
                }
                Log.e("aaaa", "Повторный запрос")
            }
        }
    }

    private fun urlStep(url: String) {
        val list = url.split(" ")
        EventDialog.urls = list
        EventDialog().show(parentFragmentManager, "Ссылки")
    }

    private fun deleteStep(id: Int, stepId: Int) {
        lifecycleScope.launch {
            viewModel.deleteStep(id, stepId, viewModel.mtoken)
            delay(800)
            viewModel.getStepsByEvent(id, viewModel.mtoken)
            Log.e("DELETE", "OKEY")
        }
    }
}