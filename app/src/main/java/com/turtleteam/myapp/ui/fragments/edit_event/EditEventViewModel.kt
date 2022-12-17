package com.turtleteam.myapp.ui.fragments.edit_event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turtleteam.myapp.data.model.event.EventRequestBody
import com.turtleteam.myapp.data.repositories.EventRepository
import com.turtleteam.myapp.data.wrapper.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditEventViewModel @Inject constructor(private val repository: EventRepository) : ViewModel(){

    fun editEvent(id: Int, eventModel: EventRequestBody, token: String) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.editEvent(id = id, eventModel = eventModel, token = token)
        }
}