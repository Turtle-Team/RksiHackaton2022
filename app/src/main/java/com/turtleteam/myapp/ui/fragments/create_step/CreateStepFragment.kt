package com.turtleteam.myapp.ui.fragments.create_step

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.turtleteam.myapp.data.preferences.UserPreferences
import com.turtleteam.myapp.databinding.FragmentCreateStepBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CreateStepFragment : Fragment() {

    private val viewModel: CreateStepViewModel by viewModels()
    private lateinit var binding: FragmentCreateStepBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCreateStepBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getInt("key")

        binding.timestartpicker.setIs24HourView(true)
        binding.timestartpicker.hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

        binding.timeendpicker.setIs24HourView(true)
        binding.timeendpicker.hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

        binding.datestartpicker.setOnDateChangedListener { _, _, _, _ ->
            getFormattedStartTime()
        }

        binding.timestartpicker.setOnTimeChangedListener { _, _, _ ->
            getFormattedStartTime()
        }

        binding.dateendpicker.setOnDateChangedListener { _, _, _, _ ->
            getFormattedEndTime()
        }

        binding.timeendpicker.setOnTimeChangedListener { _, _, _ ->
            getFormattedEndTime()
        }

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

    @SuppressLint("SimpleDateFormat")
    private fun getFormattedEndTime(): String {
        val date = Calendar.getInstance().apply {
            set(Calendar.YEAR, binding.dateendpicker.year)
            set(Calendar.MONTH, binding.dateendpicker.month)
            set(Calendar.DAY_OF_MONTH, binding.dateendpicker.dayOfMonth)
            set(Calendar.HOUR_OF_DAY, binding.timeendpicker.hour)
            set(Calendar.MINUTE, binding.timeendpicker.minute)
        }.time
        val formattedDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(date)
        binding.dateEndEditText.text = formattedDate
        Log.e("aaaa", formattedDate.toString())
        return formattedDate
    }

    @SuppressLint("SimpleDateFormat")
    private fun getFormattedStartTime(): String {
        val date = Calendar.getInstance().apply {
            set(Calendar.YEAR, binding.datestartpicker.year)
            set(Calendar.MONTH, binding.datestartpicker.month)
            set(Calendar.DAY_OF_MONTH, binding.datestartpicker.dayOfMonth)
            set(Calendar.HOUR_OF_DAY, binding.timestartpicker.hour)
            set(Calendar.MINUTE, binding.timestartpicker.minute)
        }.time
        val formattedDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(date)
        binding.dateStartEditText.text = formattedDate
        Log.e("aaaa", formattedDate.toString())
        return formattedDate
    }
}