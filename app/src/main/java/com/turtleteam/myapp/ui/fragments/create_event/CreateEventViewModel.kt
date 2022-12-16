package com.turtleteam.myapp.ui.fragments.create_event

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turtleteam.myapp.data.repositories.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateEventViewModel @Inject constructor(private val repository: EventRepository) : ViewModel(){

    fun createEvent(eventModel: String) = viewModelScope.launch(Dispatchers.IO) {
        Log.e("CREATE EVENT", repository.createEvent(eventModel).toString())
        repository.createEvent(eventModel)
    }
}