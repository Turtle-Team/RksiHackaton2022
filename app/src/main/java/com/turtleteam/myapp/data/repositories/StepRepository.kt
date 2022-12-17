package com.turtleteam.myapp.data.repositories

import com.turtleteam.myapp.data.api.StepService
import com.turtleteam.myapp.data.model.step.Step
import javax.inject.Inject

class StepRepository @Inject constructor(private val apiService: StepService) {

    suspend fun getStepsByEvent(id: Int, token: String): List<Step> =
        apiService.getAllStepsByEvent(id = id, token = token)

    suspend fun deleteStep(id: Int, stepId: Int, token: String) = apiService.deleteStep(id = id, stepId = stepId, token = token)
}