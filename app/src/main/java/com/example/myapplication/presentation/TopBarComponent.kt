package com.example.myapplication.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.navigation.ScreenRoute
import com.example.myapplication.screens.insertScreen.InsertEvent
import com.example.myapplication.screens.insertScreen.InsertScreenViewModel
import com.example.myapplication.screens.presentData.PresentDataViewModel
import com.example.myapplication.screens.presentData.PresentEvent


@Composable
fun topBarComponent(
    topBarTitle: String,
    navController: NavController,
    dbViewModel: DatabaseViewModel
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier.clickable {
                navController.popBackStack()
            }

        )
        Spacer(Modifier.padding(vertical = 20.dp))
        Text(
            text = topBarTitle,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic
        )
        Text(
            text = "Sign out\n${dbViewModel.profileState.name}",
            color = Color.LightGray,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 12.dp)
                .clickable {
                    println(dbViewModel.profileState.password)
                    println(dbViewModel.profileState.loggedIn)
                dbViewModel.loggingOut()
                navController.navigate(route = ScreenRoute.MainMenu.route)
            }
        )
    }


}