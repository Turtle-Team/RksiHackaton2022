package com.turtleteam.myapp.ui.fragments.step

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turtleteam.myapp.data.model.step.Step
import com.turtleteam.myapp.data.repositories.StepRepository
import com.turtleteam.myapp.data.wrapper.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StepViewModel @Inject constructor(private val repository: StepRepository) : ViewModel() {

    private val _steps = MutableLiveData<Result<List<Step>>>()
    var steps: LiveData<Result<List<Step>>> = _steps

    var eventId = 0
    var mtoken = ""

    fun getStepsByEvent(id: Int, token: String) = viewModelScope.launch(Dispatchers.IO) {
        _steps.postValue(repository.getStepsByEvent(id, token))
    }

    fun deleteStep(id: Int, stepId: Int, token: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteStep(id, stepId, token)
    }
}