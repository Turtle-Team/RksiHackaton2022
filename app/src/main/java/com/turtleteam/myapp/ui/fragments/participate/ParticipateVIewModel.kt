package com.turtleteam.myapp.ui.fragments.participate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turtleteam.myapp.data.model.event.Events
import com.turtleteam.myapp.data.model.member.MemberModel
import com.turtleteam.myapp.data.preferences.UserPreferences
import com.turtleteam.myapp.data.repositories.EventRepository
import com.turtleteam.myapp.data.repositories.MemberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.turtleteam.myapp.data.wrapper.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ParticipateVIewModel @Inject constructor(
    private val repository: MemberRepository,
    private val eventRepository: EventRepository,
    private val userPrefs: UserPreferences,
) : ViewModel() {

    private val _myEvents = MutableLiveData<Result<MemberModel>>()
    val myEvents: LiveData<Result<MemberModel>> = _myEvents

    private val _myEvents2 = MutableLiveData<Result<List<Events>>>()
    val myEvents2: LiveData<Result<List<Events>>> = _myEvents2

    val listIds = mutableListOf<Int>()


    fun getAllEvents() = viewModelScope.launch(Dispatchers.IO) {
        _myEvents2.postValue(eventRepository.getAllEvent())
    }

    fun getMyEvents() = viewModelScope.launch(Dispatchers.IO) {
        _myEvents.postValue(repository.getMembersMe(userPrefs.setUserToken().toString()))
    }

    fun deleteMember(eventId: Int) = viewModelScope.launch {
        userPrefs.setUserToken()?.let { repository.deleteMember(eventId, it) }
    }
}