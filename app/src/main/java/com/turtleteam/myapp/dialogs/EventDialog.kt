package com.turtleteam.myapp.dialogs

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.turtleteam.myapp.R
import com.turtleteam.myapp.adapters.UrlsAdapter
import com.turtleteam.myapp.databinding.DialogEventBinding

class EventDialog : DialogFragment() {

    private lateinit var binding: DialogEventBinding

    companion object {
        var urls: List<String>? = null
    }

    private var urlsAdapter = UrlsAdapter(
        onClick = { clickOnUrl(it) }
    )

    private fun clickOnUrl(url: String) {
        try {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }catch (e: Exception){
            Toast.makeText(requireContext(), "Ссылка не работает", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DialogEventBinding.inflate(layoutInflater, container, false)
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.edit_text_corners_night)
        binding.urlslistadapter.adapter = urlsAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (urls != null) {
            Log.e("dialog", urls.toString())
            urlsAdapter.submitList(urls)
        }

        binding.hideEventButton.setOnClickListener {
            requireDialog().cancel()
        }
    }
}