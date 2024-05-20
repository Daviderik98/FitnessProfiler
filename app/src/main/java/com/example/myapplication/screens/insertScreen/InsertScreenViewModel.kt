package com.example.myapplication.screens.insertScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myapplication.domain.InsertValidation

class InsertScreenViewModel : ViewModel() {

    private val insertValidation = InsertValidation()

    var insertState by mutableStateOf(State())

    private fun validateInput(textInput: String): Boolean {
        val inputResult = insertValidation.execute(textInput)
        insertState = insertState.copy(inputError = insertState.inputError)
        return inputResult.isSuccessful
    }

    fun onEvent(event: InsertEvent) {
        when (event) {
            is InsertEvent.HeightChanged -> {
                insertState = insertState.copy(heightInput = event.fullInput)
                validateInput(insertState.heightInput)
            }

            is InsertEvent.WeightChanged -> {
                insertState = insertState.copy(weightInput = event.fullInput)
                validateInput(insertState.weightInput)
            }

            is InsertEvent.AgeChanged -> {
                insertState = insertState.copy(ageInput = event.fullInput)
                validateInput(insertState.ageInput)
            }

            is InsertEvent.RestingPulseChanged -> {
                insertState = insertState.copy(pulseInput = event.fullInput)
                validateInput(insertState.pulseInput)
            }

            is InsertEvent.SubmitInput -> {
                if (
                    validateInput(insertState.heightInput) &&
                    validateInput(insertState.weightInput) &&
                    validateInput(insertState.ageInput) &&
                    validateInput(insertState.pulseInput)
                ) {
                    insertState = insertState.copy(
                        isValid = true,
                        inputHeight = insertState.heightInput.toDouble(),
                        inputWeight = insertState.weightInput.toDouble(),
                        inputAge = insertState.ageInput.toInt(),
                        inputRestingPulse = insertState.pulseInput.toInt()
                    )
                }
                else {
                    insertState = insertState.copy(clickError = "All text-fields must be filled in")
                }
            }

            is InsertEvent.InputReset ->{
                insertState = insertState.copy(
                    heightInput = "",
                    weightInput = "",
                    ageInput = "",
                    pulseInput = "",
                    isValid = false
                )
            }
        }
    }

}

data class State(
    var heightInput: String = "",
    var weightInput: String = "",
    var ageInput: String = "",
    var pulseInput: String = "",
    var inputError: String? = null,
    var clickError: String = "",
    var isValid: Boolean = false,

    var inputHeight: Double = 0.0,
    var inputWeight: Double = 0.0,
    var inputAge: Int = 0,
    var inputRestingPulse: Int = 0
)

sealed class InsertEvent {
    data class HeightChanged(val fullInput: String) : InsertEvent()
    data class WeightChanged(val fullInput: String) : InsertEvent()
    data class AgeChanged(val fullInput: String) : InsertEvent()
    data class RestingPulseChanged(val fullInput: String) : InsertEvent()
    data class InputReset(val noData: String? = null): InsertEvent()
    object SubmitInput : InsertEvent()
}