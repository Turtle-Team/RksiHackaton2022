package com.turtleteam.myapp.ui.fragments.create_step

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.turtleteam.myapp.R
import com.turtleteam.myapp.data.model.step.StepRequestBody
import com.turtleteam.myapp.data.preferences.UserPreferences
import com.turtleteam.myapp.data.wrapper.Result
import com.turtleteam.myapp.databinding.FragmentCreateStepBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_create_step.*
import kotlinx.android.synthetic.main.fragment_create_step.view.*
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
            UserPreferences(requireContext()).setUserToken()?.let { savedToken ->
                if (id!=null) {
                    viewModel.createStep(
                        id, StepRequestBody(
                            id,
                            binding.dateStartEditText.text.toString(),
                            binding.dateEndEditText.text.toString(),
                            binding.titleEditText.text.toString(),
                            binding.descriptionEditText.text.toString(),
                            binding.stepurl.text.toString()
                        ),
                        savedToken
                    )
                    it.isClickable = false
                }else{
                    Toast.makeText(requireContext(), "Отсутствует id мероприятия", Toast.LENGTH_SHORT).show()
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
                    findNavController().navigate(R.id.action_createStepFragment_to_stepFragment)
                }
            }
            is Result.Loading,
            is Result.ConnectionError,
            is Result.Error,
            -> {
                Toast.makeText(requireContext(), "Не удалось создать", Toast.LENGTH_LONG).show()
            }
            is Result.NotFoundError,
            -> {
                Log.e("bbbbb", result.toString())
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