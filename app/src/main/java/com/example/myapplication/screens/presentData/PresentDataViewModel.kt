package com.example.myapplication.screens.presentData

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class PresentDataViewModel: ViewModel(){
    var presentState by mutableStateOf(State())

   private fun changeLabel(newtext: String){
        presentState = presentState.copy()
    }


    fun onEvent(event: PresentEvent){
        when(event){
            is PresentEvent.FirstInputChanged ->{
                presentState = presentState.copy(
                    currentTextOne = event.enteredData
                )
            }

            is PresentEvent.SecondInputChanged -> {
                presentState = presentState.copy(
                    currentTextTwo = event.enteredData
                )
            }

            is PresentEvent.ThirdnputChanged -> {
                presentState = presentState.copy(
                    currentTextThree = event.enteredData
                )
            }

            is PresentEvent.ResetAllInputs ->{
                presentState = presentState.copy(
                    currentTextOne = "",
                    currentTextTwo = "",
                    currentTextThree = ""
                )
            }
        }

    }

}


data class State(
    var currentTextOne: String = "",
    var currentTextTwo: String = "",
    var currentTextThree: String = ""
)

sealed class PresentEvent{
    data class FirstInputChanged(val enteredData: String) : PresentEvent()
    data class SecondInputChanged(val enteredData: String) : PresentEvent()
    data class ThirdnputChanged(val enteredData: String) : PresentEvent()
    data class ResetAllInputs(val noData: String? = null): PresentEvent()
}