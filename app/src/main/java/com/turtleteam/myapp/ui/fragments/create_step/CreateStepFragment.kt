package com.turtleteam.myapp.ui.fragments.create_step

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.turtleteam.myapp.data.model.step.Step
import com.turtleteam.myapp.data.model.step.StepRequestBody
import com.turtleteam.myapp.data.preferences.UserPreferences
import com.turtleteam.myapp.databinding.FragmentCreateEventBinding
import com.turtleteam.myapp.databinding.FragmentCreateStepBinding
import com.turtleteam.myapp.ui.fragments.create_event.CreateEventViewModel
import com.turtleteam.myapp.ui.fragments.step.StepViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CreateStepFragment : Fragment() {

    private val viewModel: CreateStepViewModel by viewModels()
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

        val id = arguments?.getInt("key")

        binding.timeStartpicker.setIs24HourView(true)
        binding.timeStartpicker.hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

        binding.timeEndpicker.setIs24HourView(true)
        binding.timeEndpicker.hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

        binding.createStepButton.setOnClickListener {
            UserPreferences(requireContext()).setUserId()?.let { savedToken ->
//                viewModel.createEvent(
//                    id = id, stepModel = StepRequestBody(
//                        event_id = id,
//                        date =
//                    )
//                )
            }
        }
    }
}