package com.turtleteam.myapp.ui.fragments.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.turtleteam.myapp.R
import com.turtleteam.myapp.data.model.users.AuthRequestBody
import com.turtleteam.myapp.data.preferences.UserPreferences
import com.turtleteam.myapp.data.wrapper.Result
import com.turtleteam.myapp.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        UserPreferences(requireContext()).setUserToken()?.let {
            viewModel.getUser(it)
        }
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.user.observe(viewLifecycleOwner) {
            handleUserData(it)
        }

        binding.signOutButton.setOnClickListener {
            UserPreferences(requireContext()).getUserToken("0")
            UserPreferences(requireContext()).getUserStatus("0")
            findNavController().navigate(R.id.action_profileFragment_to_authFragment)
        }
    }
    private fun handleUserData(result: Result<AuthRequestBody>) {
        val prefs = UserPreferences(requireContext())
        when (result) {
            is Result.Success -> {
                if (result.value.fio != null) {
                    binding.fioTextView.text = result.value.fio
                    binding.postTextView.text = result.value.post
                    binding.oranizationTextView.text = result.value.organization
                    binding.statusTextView.text = result.value.status
                    binding.emailTextView.text = result.value.email
                }
            }
            is Result.NotFoundError,
            is Result.ConnectionError,
            is Result.Error,
            is Result.Loading,
            -> {
                Toast.makeText(requireContext(), "Не удалось загрузить данные", Toast.LENGTH_SHORT).show()
            }
        }
    }
}