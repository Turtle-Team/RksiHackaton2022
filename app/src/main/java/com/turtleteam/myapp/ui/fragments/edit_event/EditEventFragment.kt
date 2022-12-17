package com.turtleteam.myapp.ui.fragments.edit_event

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.turtleteam.myapp.R
import com.turtleteam.myapp.data.model.event.EventRequestBody
import com.turtleteam.myapp.data.preferences.UserPreferences
import com.turtleteam.myapp.data.wrapper.Result
import com.turtleteam.myapp.databinding.FragmentEditEventBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class EditEventFragment : Fragment() {

    private lateinit var binding: FragmentEditEventBinding
    private val viewModel: EditEventViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentEditEventBinding.inflate(layoutInflater, container, false)
        binding.timepicker.setIs24HourView(true);
        binding.timepicker.hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val id = arguments?.getInt("key")
        val header = arguments?.getString("header")
        val text = arguments?.getString("text")
        val url = arguments?.getString("url")
        val date = arguments?.getString("date")

        binding.titleEditText.setText(header)
        binding.descriptionEditText.setText(text)
        binding.urlEditText.setText(url)
        binding.dateEditText.setText(date)

        Log.e("ID", id.toString())
        binding.saveEventButton.setOnClickListener {
            UserPreferences(requireContext()).setUserId()?.let { savedToken ->
                viewModel.editEvent(
                    id = id!!,
                    EventRequestBody(
                        header = binding.titleEditText.text.toString(),
                        text = binding.descriptionEditText.text.toString(),
                        url = binding.urlEditText.text.toString(),
                        date = getFormattedTime()
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
            binding.saveEventButton.isClickable = true
        }
    }

    private fun handleResult(result: Result<Throwable>) {
        when (result) {
            is Result.Success -> {
                if (result.value != null) {
                    Log.e("dddddddd", result.toString())
                    findNavController().navigate(R.id.action_editEventFragment_to_homeFragment)
                }
            }
            is Result.Loading,
            is Result.ConnectionError,
            is Result.Error,
            -> {
                Toast.makeText(requireContext(), "Не удалось сохранить", Toast.LENGTH_LONG).show()
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