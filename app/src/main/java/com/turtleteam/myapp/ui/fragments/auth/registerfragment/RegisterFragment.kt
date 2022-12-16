package com.turtleteam.myapp.ui.fragments.auth.registerfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.navigation.fragment.findNavController
import com.turtleteam.myapp.R
import com.turtleteam.myapp.databinding.FragmentRegisterBinding
import com.turtleteam.myapp.ui.fragments.auth.base.BaseAuthFragment

class RegisterFragment : BaseAuthFragment<FragmentRegisterBinding>() {

    private var selectStatus = "СТАТУС"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
        }

        binding.statusPopupButton.setOnClickListener {
            showPopup(binding.statusPopupButton)
        }
    }

    private fun showPopup(view: View) {
        val popup = PopupMenu(view.context, view)
        popup.inflate(R.menu.status_menu)
        popup.setOnMenuItemClickListener {
            selectStatus = it.toString()
            binding.statusPopupButton.text = selectStatus
            return@setOnMenuItemClickListener true
        }
        popup.show()
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentRegisterBinding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
}