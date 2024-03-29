package com.turtleteam.myapp.ui.fragments.create_event

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
import com.turtleteam.myapp.data.model.event.EventRequestBody
import com.turtleteam.myapp.data.preferences.UserPreferences
import com.turtleteam.myapp.data.wrapper.Result
import com.turtleteam.myapp.databinding.FragmentCreateEventBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CreateEventFragment : Fragment() {

    private lateinit var binding: FragmentCreateEventBinding

    private val viewModel: CreateEventViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCreateEventBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.timepicker.setIs24HourView(true)
        binding.timepicker.hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

        binding.createEventButton.setOnClickListener {

            UserPreferences(requireContext()).setUserToken()?.let { savedToken ->
                viewModel.createEvent(
                    EventRequestBody(
                        header = binding.titleEditText.text.toString(),
                        text = binding.descriptionEditText.text.toString(),
                        url = binding.urlEditText.text.toString(),
                        date_start = getFormattedTime()
                    ),
                    savedToken
                )
            }
            it.isClickable = false
        }

        binding.datepicker.setOnDateChangedListener { _, _, _, _ ->
            getFormattedTime()
        }
        binding.timepicker.setOnTimeChangedListener { _, _, _ ->
            getFormattedTime()
        }
        viewModel.result.observe(viewLifecycleOwner) {
            handleResult(it)
            binding.createEventButton.isClickable = true
        }
    }

    private fun handleResult(result: Result<Throwable>) {
        when (result) {
            is Result.Success -> {
                if (result.value != null) {
                    Log.e("dddddddd", result.toString())
                    findNavController().navigate(R.id.action_createEventFragment_to_homeFragment)
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
    private fun getFormattedTime(): String {
        val date = Calendar.getInstance().apply {
            set(Calendar.YEAR, binding.datepicker.year)
            set(Calendar.MONTH, binding.datepicker.month)
            set(Calendar.DAY_OF_MONTH, binding.datepicker.dayOfMonth)
            set(Calendar.HOUR_OF_DAY, binding.timepicker.hour)
            set(Calendar.MINUTE, binding.timepicker.minute)
        }.time
        val formattedDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(date)
        binding.dateEditText.text = formattedDate
        Log.e("aaaa", formattedDate.toString())
        return formattedDate
    }
}