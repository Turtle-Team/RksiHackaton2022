package com.turtleteam.myapp.ui.fragments.edit_step

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.turtleteam.myapp.R
import com.turtleteam.myapp.data.model.step.StepRequestBody
import com.turtleteam.myapp.data.preferences.UserPreferences
import com.turtleteam.myapp.data.wrapper.Result
import com.turtleteam.myapp.databinding.FragmentEditStepBinding
import com.turtleteam.myapp.ui.fragments.create_step.CreateStepViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class EditStepFragment : Fragment() {

    private lateinit var binding: FragmentEditStepBinding
    private val viewModel: CreateStepViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditStepBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val step = arguments?.getInt("key")

        binding.titleEditText.setText(arguments?.getString("header"))
        binding.dateStartEditText.text = arguments?.getString("date_start")
        binding.dateEndEditText.text = arguments?.getString("date_end")
        binding.stepurl.setText(arguments?.getString("url"))
        binding.descriptionEditText.setText(arguments?.getString("text"))

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
            val eId = arguments?.getInt("event_id")
            if (eId!=null)
            UserPreferences(requireContext()).setUserToken()?.let { savedToken ->
                if (step!=null) {
                    viewModel.editStep(
                        eId, StepRequestBody(
                            eId,
                            getFormattedStartTime(),
                            getFormattedEndTime(),
                            binding.titleEditText.text.toString(),
                            binding.descriptionEditText.text.toString(),
                            binding.stepurl.text.toString()
                        ),
                        savedToken,
                        step
                    )
                    it.isClickable = false
                }
            }
        }

        viewModel.result.observe(viewLifecycleOwner){
            handleResult(it)
            binding.createStepButton.isClickable = true
        }

    }

    private fun handleResult(result: Result<Throwable>) {
        when (result) {
            is Result.Success -> {
                if (result.value != null) {
                    Log.e("dddddddd", result.toString())
                }
            }
            is Result.Loading,
            is Result.ConnectionError,
            is Result.Error,
            -> { findNavController().navigate(R.id.action_editStepFragment_to_stepFragment)
            }
            is Result.NotFoundError,
            -> {
                Toast.makeText(requireContext(), result.toString(), Toast.LENGTH_LONG).show()
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