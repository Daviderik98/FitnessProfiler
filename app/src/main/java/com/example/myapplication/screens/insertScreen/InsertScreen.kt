package com.example.myapplication.screens.insertScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.myapplication.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.navigation.ScreenRoute
import com.example.myapplication.presentation.DatabaseViewModel
import com.example.myapplication.presentation.topBarComponent
import kotlin.coroutines.coroutineContext

@Composable
fun insertDataScreen(
    navController: NavController,
    viewModel: InsertScreenViewModel,
    scrollState: ScrollState,
    dbViewModel: DatabaseViewModel
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ){

           Column(modifier = Modifier.fillMaxWidth()) {
               topBarComponent(
                   topBarTitle = stringResource(R.string.insert_opening),
                   navController = navController,
                   dbViewModel = dbViewModel
               )
                   Text(
                       text = "Welcome ${dbViewModel.profileState.name}",
                       color = MaterialTheme.colorScheme.secondary,
                       fontSize = 20.sp,
                       fontWeight = FontWeight.Medium,
                       modifier = Modifier.align(Alignment.CenterHorizontally)
                   )
                   Spacer(modifier = Modifier.padding(vertical = 9.dp))
                   Text(
                       text = stringResource(R.string.insert_description),
                       fontWeight = FontWeight.Medium,
                       textAlign = TextAlign.Center,
                       modifier = Modifier
                           .fillMaxWidth(0.7f)
                           .align(Alignment.CenterHorizontally),
                   )
              Text(
                  text = stringResource(R.string.insert_extra),
                //  fontSize = TextUnit.Unspecified,
                  fontWeight = FontWeight.Medium,
                  color = Color.Red,
                  modifier = Modifier
                      .fillMaxWidth(0.85f)
                      .align(Alignment.CenterHorizontally)
              )
               Spacer(modifier = Modifier.padding(vertical = 20.dp))
               Column(
                   modifier = Modifier
                       .fillMaxWidth(0.8f)
                       .fillMaxHeight(0.75f)
                       .border(3.dp, Color.LightGray)
                       .padding(vertical = 15.dp)
                       .align(Alignment.CenterHorizontally)
                       .verticalScroll(scrollState)
               ){

                   OutlinedTextField(
                       value = viewModel.insertState.heightInput,
                       onValueChange = {viewModel.onEvent(InsertEvent.HeightChanged(it))},
                       label = {Text(stringResource(R.string.insert_input_one))},
                       suffix = {Text(stringResource(R.string.insert_label_one))},
                       singleLine = true,
                       keyboardOptions = KeyboardOptions(
                           keyboardType = KeyboardType.Decimal
                       ),
                    //   colors = ,
                       modifier = Modifier
                           .align(Alignment.CenterHorizontally)
                           .background(
                               color = MaterialTheme.colorScheme.inversePrimary,
                               shape = RoundedCornerShape(5.dp)
                           )
                   )
                   Spacer(modifier = Modifier.padding(vertical = 23.dp))
                   OutlinedTextField(
                       value = viewModel.insertState.weightInput,
                       onValueChange = {viewModel.onEvent(InsertEvent.WeightChanged(it))},
                       label = {Text(stringResource(R.string.insert_input_two))},
                       suffix = {Text(stringResource(R.string.insert_label_two))},
                       singleLine = true,
                       keyboardOptions = KeyboardOptions(
                           keyboardType = KeyboardType.Decimal
                       ),
                       modifier = Modifier.align(Alignment.CenterHorizontally)
                           .background(
                               color = MaterialTheme.colorScheme.inversePrimary,
                               shape = RoundedCornerShape(5.dp)
                           )
                   )
                   Spacer(modifier = Modifier.padding(vertical = 23.dp))
                   OutlinedTextField(
                       value = viewModel.insertState.ageInput,
                       onValueChange = {viewModel.onEvent(InsertEvent.AgeChanged(it))},
                       label = {Text(stringResource(R.string.insert_input_three))},
                       suffix = {Text(stringResource(R.string.insert_label_three))},
                       singleLine = true,
                       keyboardOptions = KeyboardOptions(
                           keyboardType = KeyboardType.Number
                       ),
                       modifier = Modifier
                           .align(Alignment.CenterHorizontally)
                           .background(
                               color = MaterialTheme.colorScheme.inversePrimary,
                               shape = RoundedCornerShape(5.dp)
                           )
                   )
                   Spacer(modifier = Modifier.padding(vertical = 23.dp))
                   OutlinedTextField(
                       value = viewModel.insertState.pulseInput,
                       onValueChange = {viewModel.onEvent(InsertEvent.RestingPulseChanged(it))},
                       label = {Text(stringResource(R.string.insert_input_four))},
                       suffix = {Text(stringResource(R.string.insert_label_four))},
                       singleLine = true,
                       keyboardOptions = KeyboardOptions(
                           keyboardType = KeyboardType.Number
                       ),
                       modifier = Modifier
                           .align(Alignment.CenterHorizontally)
                           .background(
                               color = MaterialTheme.colorScheme.inversePrimary,
                               shape = RoundedCornerShape(5.dp)
                           )
                   )
                   Spacer(modifier = Modifier.padding(vertical = 23.dp))

               }
               Button(
                   onClick = {
                       viewModel.onEvent(InsertEvent.SubmitInput)
                       if(viewModel.insertState.isValid){
                           viewModel.onEvent(InsertEvent.InputReset())
                           navController.navigate(route = ScreenRoute.PresentData.route)
                       }
                             },
                   modifier = Modifier
                       .align(Alignment.CenterHorizontally)
                       .padding(20.dp)
                       .border(
                           width = 3.dp,
                           color = MaterialTheme.colorScheme.onTertiaryContainer,
                           shape = RoundedCornerShape(5.dp)
                       )
               ) {
                   Text(
                       text = stringResource(id = R.string.insert_navigate),
                       textAlign = TextAlign.Center,
                   )
               }
               Text(
                   text = viewModel.insertState.clickError,
                   color = Color.Red,
                   modifier = Modifier.align(Alignment.CenterHorizontally)
                   )

            }

        }
}

