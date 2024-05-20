package com.example.myapplication.navigation

import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.domain.Calculator
import com.example.myapplication.presentation.DatabaseViewModel
import com.example.myapplication.presentation.MainMenuViewModel
import com.example.myapplication.presentation.mainMenu
import com.example.myapplication.screens.insertScreen.InsertScreenViewModel
import com.example.myapplication.screens.insertScreen.insertDataScreen
import com.example.myapplication.screens.presentData.PresentDataViewModel
import com.example.myapplication.screens.presentData.presentDataScreen
import com.example.myapplication.screens.registration.registerScreen

@Composable
fun setUpNavigation(
    navController: NavHostController,
    ){
    val viewModelMain = MainMenuViewModel()
    val viewModelInsert = InsertScreenViewModel()
    val viewModelPresent = PresentDataViewModel()
    val scrollingState = rememberScrollState()
    val dbViewModel = DatabaseViewModel()
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.MainMenu.route,
        ) {
        composable(
            route = ScreenRoute.MainMenu.route
        ){
            mainMenu(
                navController = navController,
                mainViewModel = viewModelMain,
                dbViewModel = dbViewModel
                )
        }

        composable(
            route = ScreenRoute.Registration.route
        ){
            registerScreen(
                navController = navController,
                mainViewModel = viewModelMain,
                dbViewModel = dbViewModel
            )
        }

        composable(
            route = ScreenRoute.InsertData.route
        ){
            insertDataScreen(
                navController = navController,
                viewModel = viewModelInsert,
                scrollState = scrollingState,
                dbViewModel = dbViewModel
            )
        }

        composable(
            route = ScreenRoute.PresentData.route
        ){
            presentDataScreen(
                navController = navController,
              calculator = Calculator(
                  height = viewModelInsert.insertState.inputHeight,
                  weight = viewModelInsert.insertState.inputWeight,
                  age = viewModelInsert.insertState.inputAge,
                  restPulse = viewModelInsert.insertState.inputRestingPulse
              ),
                viewModel = viewModelPresent,
                scrollstate = scrollingState,
                dbViewModel = dbViewModel
            )
        }
    }
}