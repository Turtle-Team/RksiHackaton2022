package com.turtleteam.myapp.ui.fragments.create_step

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turtleteam.myapp.data.model.event.EventRequestBody
import com.turtleteam.myapp.data.model.step.Step
import com.turtleteam.myapp.data.model.step.StepRequestBody
import com.turtleteam.myapp.data.repositories.EventRepository
import com.turtleteam.myapp.data.repositories.StepRepository
import com.turtleteam.myapp.data.wrapper.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateStepViewModel @Inject constructor(private val repository: StepRepository) : ViewModel(){

    private val _result = MutableLiveData<Result<Throwable>>()
    val result: LiveData<Result<Throwable>> = _result

    fun createEvent(id: Int, stepModel: StepRequestBody, token: String) = viewModelScope.launch(Dispatchers.IO) {
//        _result.postValue(repository.createEvent(eventModel,token))
        repository.createStep(id = id, stepModel = stepModel, token = token)
    }
}