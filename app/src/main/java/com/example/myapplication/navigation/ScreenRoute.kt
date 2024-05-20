package com.example.myapplication.navigation

sealed class ScreenRoute(val route: String) {
    object MainMenu: ScreenRoute(route = "main_menu_screen")
    object Registration: ScreenRoute(route = "register_screen")
    object InsertData: ScreenRoute(route = "insert_data_screen")
    object PresentData: ScreenRoute(route = "present_data_screen")
}