package com.turtleteam.myapp.ui.fragments.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turtleteam.myapp.data.model.event.Events
import com.turtleteam.myapp.data.repositories.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: EventRepository) : ViewModel() {

    private val _events = MutableLiveData<List<Events>>()
    val events: LiveData<List<Events>> = _events

    fun getAllEvents() = viewModelScope.launch(Dispatchers.IO) {
        Log.e("EVENTS", repository.getAllEvent().toString())
        _events.postValue(repository.getAllEvent())
    }
}