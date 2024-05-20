package com.example.myapplication.screens.presentData

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.domain.Calculator
import com.example.myapplication.navigation.ScreenRoute
import com.example.myapplication.presentation.DatabaseViewModel
import com.example.myapplication.presentation.topBarComponent

@Composable
fun presentDataScreen(
    navController: NavController,
    calculator: Calculator,
    viewModel: PresentDataViewModel,
    scrollstate: ScrollState,
    dbViewModel: DatabaseViewModel
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    )
    {
    Column(
        modifier = Modifier
            .fillMaxSize()
           // .wrapContentSize(align = Alignment.Center)
            .verticalScroll(scrollstate),

    ) {
        topBarComponent(
            topBarTitle = stringResource(R.string.present_opening),
            navController = navController,
            dbViewModel = dbViewModel
        )
        Text(
            text = stringResource(id = R.string.present_welcome),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.padding(vertical = 30.dp))
        Text(
            text = stringResource(R.string.present_allinputs),
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = calculator.height.toString())
            Spacer(modifier = Modifier.padding(horizontal = 10.dp))
            Text(text = calculator.weight.toString())
            Spacer(modifier = Modifier.padding(horizontal = 10.dp))
            Text(text = calculator.age.toString())
            Spacer(modifier = Modifier.padding(horizontal = 10.dp))
            Text(text = calculator.restingPulse.toString())
        }

        Spacer(Modifier.padding(vertical = 35.dp))
        Text(
            text = stringResource(R.string.calculate_first),
                textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .fillMaxHeight(0.2f)
                .background(Color.Blue)
                .align(Alignment.CenterHorizontally)
        ){

            Spacer(modifier = Modifier.padding(horizontal = 17.dp))
            Text(
                text = viewModel.presentState.currentTextOne
            )
        }
        Spacer(modifier = Modifier.padding(15.dp))
        Button(onClick = {
            calculator.calculateBMI(calculator.height, calculator.weight)
            viewModel.onEvent(
                PresentEvent.FirstInputChanged(
                    calculator.bmi.toString()
                )
            )
        },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        {
            Text(
                text = stringResource(R.string.start_calculation)
            )
        }
        Spacer(modifier = Modifier.padding(vertical = 25.dp))
        Text(
            text = stringResource(R.string.calculate_second),
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .fillMaxHeight(0.2f)
                .background(Color.Blue)
                .align(Alignment.CenterHorizontally)

        ){

            Spacer(modifier = Modifier.padding(horizontal = 17.dp))
            Text(
                text = viewModel.presentState.currentTextTwo
            )
        }
        Spacer(modifier = Modifier.padding(15.dp))
        Button(onClick = {
            calculator.calculateMaxPulse(calculator.age)
            viewModel.onEvent(
                PresentEvent.SecondInputChanged(
                    enteredData = calculator.maxPulse.toString()
                )
            )
        },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        {
            Text(
                text = stringResource(R.string.start_calculation)
            )
        }
        Spacer(modifier = Modifier.padding(25.dp))
        Text(
            text = stringResource(R.string.calculate_third),
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .fillMaxHeight(0.2f)
                .background(Color.Blue)
                .align(Alignment.CenterHorizontally)
        ){

            Spacer(modifier = Modifier.padding(horizontal = 17.dp))
            Text(
                text = viewModel.presentState.currentTextThree
            )
        }
        Spacer(modifier = Modifier.padding(15.dp))
        Button(onClick = {
            calculator.calculateHRR(calculator.maxPulse, calculator.restingPulse)
            viewModel.onEvent(
                PresentEvent.ThirdnputChanged(
                    enteredData = calculator.hrr.toString()
                )
            )
        },
            modifier = Modifier.align(Alignment.CenterHorizontally)
            ){
            Text(
                text = stringResource(R.string.start_calculation)
            )
        }
        Button(
            onClick = {
                viewModel.onEvent(PresentEvent.ResetAllInputs())
                navController.navigate(route = ScreenRoute.InsertData.route)
                      },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(35.dp)
        ) {
            Text(
                text = stringResource(id = R.string.present_navigate)
            )
        }
    }
}
}