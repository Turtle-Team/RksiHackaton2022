package com.turtleteam.myapp.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.turtleteam.myapp.R
import com.turtleteam.myapp.databinding.DialogEventBinding

class EventDialog : DialogFragment() {

    private lateinit var binding: DialogEventBinding
    companion object{
        var urls:String? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogEventBinding.inflate(layoutInflater, container, false)
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.edit_text_corners_night)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (urls!=null) {
            binding.urlsTextView.text = urls
        }

        binding.hideEventButton.setOnClickListener {
            requireDialog().cancel()
        }
    }
}