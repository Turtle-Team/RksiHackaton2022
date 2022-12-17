package com.turtleteam.myapp.ui.fragments.edit_event

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.turtleteam.myapp.R
import com.turtleteam.myapp.data.model.event.EventRequestBody
import com.turtleteam.myapp.data.preferences.UserPreferences
import com.turtleteam.myapp.databinding.FragmentEditEventBinding
import com.turtleteam.myapp.ui.fragments.create_event.CreateEventViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class EditEventFragment : Fragment() {

    private lateinit var binding : FragmentEditEventBinding
    private val viewModel: EditEventViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditEventBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // TODO ( Реализовать вставку данных )
        val id = arguments?.getInt("key")
        Log.e("ID", id.toString())
        binding.saveEventButton.setOnClickListener {
            UserPreferences(requireContext()).setUserId()?.let { savedToken ->
                viewModel.editEvent(
                    id = id!!,
                    EventRequestBody(
                        header = binding.titleEditText.text.toString(),
                        text = binding.descriptionEditText.text.toString(),
                        url = binding.urlEditText.text.toString(),
                        date = "2022-12-16T22:23:21.451Z"
                    ),
                    savedToken
                )
            }
            findNavController().navigate(R.id.action_editEventFragment_to_homeFragment)
        }
    }
}