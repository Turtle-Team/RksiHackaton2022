package com.turtleteam.myapp.ui.fragments.create_event

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.turtleteam.myapp.R
import com.turtleteam.myapp.data.model.event.Events
import com.turtleteam.myapp.databinding.FragmentCreateEventBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateEventFragment : Fragment() {

    private lateinit var binding: FragmentCreateEventBinding

    private val viewModel : CreateEventViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateEventBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.createEventButton.setOnClickListener {
            viewModel.createEvent(
                Gson().toJson(
                    Events(
                        header = binding.titleEditText.text.toString(),
                        text = binding.descriptionEditText.text.toString(),
                        url = binding.urlEditText.text.toString(),
                        date = binding.dateEditText.text.toString()
                    )
                )

            )
            findNavController().navigate(R.id.action_createEventFragment_to_homeFragment)
        }
    }
}