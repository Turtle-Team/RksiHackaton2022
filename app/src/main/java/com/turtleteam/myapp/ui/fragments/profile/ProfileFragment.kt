package com.turtleteam.myapp.ui.fragments.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.turtleteam.myapp.data.preferences.UserPreferences
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
            Log.e("aaaa", it)
        }
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.user.observe(viewLifecycleOwner) { user ->
            if (user != null) {
//                Toast.makeText(requireContext(), user.body()?.fio.toString(), Toast.LENGTH_SHORT).show()
                binding.fioTextView.text = user.body()?.fio
                binding.postTextView.text = "Должность: ${user.body()?.post}"
                binding.oranizationTextView.text = "Организация: ${user.body()?.organization}"
                binding.statusTextView.text = "Статус: ${user.body()?.status}"
                binding.emailTextView.text = "Почта: ${user.body()?.email}"
            }
        }
    }
}