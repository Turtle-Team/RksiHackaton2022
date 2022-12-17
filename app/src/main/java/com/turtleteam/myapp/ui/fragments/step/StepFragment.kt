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
import com.turtleteam.myapp.databinding.FragmentStepBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StepFragment : Fragment() {

    private lateinit var binding: FragmentStepBinding
    private val viewModel: StepViewModel by viewModels()
    private val adapter = StepAdapter(
        edit = { editStep(it) },
        delete = { deleteStep(id = it.event_id, stepId = it.id) }
    )

    companion object {
        private var mId: Int? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentStepBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mId = arguments?.getInt("id")
        UserPreferences(requireContext()).setUserId()?.let { savedToken ->
            if (id != null) {
                viewModel.getStepsByEvent(id, savedToken)
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
            adapter.setData(list)
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

    private fun deleteStep(id: Int, stepId: Int) {
        UserPreferences(requireContext()).setUserId()?.let { savedToken ->
            lifecycleScope.launch {
                viewModel.deleteStep(id, stepId, savedToken)
                delay(800)
                viewModel.getStepsByEvent(id, savedToken)
                Log.e("DELETE", "OKEY")
            }
        }
    }
}