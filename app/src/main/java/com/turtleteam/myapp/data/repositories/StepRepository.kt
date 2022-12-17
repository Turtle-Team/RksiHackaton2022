package com.turtleteam.myapp.data.repositories

import com.turtleteam.myapp.data.api.StepService
import com.turtleteam.myapp.data.model.step.Step
import com.turtleteam.myapp.data.model.step.StepRequestBody
import com.turtleteam.myapp.data.wrapper.NetworkResultWrapper
import com.turtleteam.myapp.data.wrapper.Result
import javax.inject.Inject

class StepRepository @Inject constructor(private val apiService: StepService) {

    suspend fun getStepsByEvent(id: Int, token: String): Result<List<Step>> =
        NetworkResultWrapper.wrapWithResult {
            apiService.getAllStepsByStep(id = id, token = token)
        }

    suspend fun createStep(id: Int, stepModel: StepRequestBody, token: String): Result<Throwable> =
        NetworkResultWrapper.wrapWithResult {
            apiService.createStep(id = id,
                stepModel = stepModel,
                token = token)
        }

    suspend fun deleteStep(id: Int, stepId: Int, token: String) =
        apiService.deleteStep(id = id, stepId = stepId, token = token)
}