package com.turtleteam.myapp.ui.fragments.auth.loginfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.turtleteam.myapp.R
import com.turtleteam.myapp.databinding.FragmentAuthBinding
import com.turtleteam.myapp.ui.fragments.auth.base.BaseAuthFragment
import com.turtleteam.myapp.utils.ViewAnimations
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class AuthFragment : BaseAuthFragment<FragmentAuthBinding>() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.authButton.setOnClickListener {
            ViewAnimations.blackout(false, binding.cardAuth)
            binding.apply {
                authButton.isClickable = false
                registerButton.isClickable = false
                passwordEditText.isFocusable = false
                fioEditText.isFocusable = false
                loadingview.visibility = View.VISIBLE
            }
            handleResult()
        }

        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_authFragment_to_registerFragment)
        }
    }

    private fun handleResult() {
        lifecycleScope.launch {
            delay(3000)
            ViewAnimations.blackout(true, binding.cardAuth)
            Toast.makeText(context, "Вход выполнен", Toast.LENGTH_SHORT).show()
            delay(200)
            findNavController().navigate(R.id.action_authFragment_to_homeFragment)
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentAuthBinding = FragmentAuthBinding.inflate(inflater, container, false)

}