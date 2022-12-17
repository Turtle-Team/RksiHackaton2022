package com.turtleteam.myapp.ui.fragments.create_event

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
import com.google.gson.Gson
import com.turtleteam.myapp.R
import com.turtleteam.myapp.data.model.event.EventRequestBody
import com.turtleteam.myapp.data.model.event.Events
import com.turtleteam.myapp.data.preferences.UserPreferences
import com.turtleteam.myapp.data.wrapper.Result
import com.turtleteam.myapp.databinding.FragmentCreateEventBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime

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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.createEventButton.setOnClickListener {
            UserPreferences(requireContext()).setUserId()?.let { savedToken ->
                viewModel.createEvent(
                    EventRequestBody(
                        header = binding.titleEditText.text.toString(),
                        text = binding.descriptionEditText.text.toString(),
                        url = binding.urlEditText.text.toString(),
                        date = "2022-12-16T22:23:21.451Z"
                    ),
                    savedToken
                )
            }
            it.isClickable = false
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
}