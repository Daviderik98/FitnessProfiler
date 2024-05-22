package com.example.myapplication.screens.registration

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.navigation.ScreenRoute
import com.example.myapplication.presentation.DatabaseViewModel
import com.example.myapplication.presentation.MainEvent
import com.example.myapplication.presentation.MainMenuViewModel

@Composable
fun registerScreen(
    navController: NavController,
    mainViewModel: MainMenuViewModel,
    dbViewModel: DatabaseViewModel
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    )
    {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize(0.6f)
                .border(
                    width = 3.dp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    shape = RectangleShape
                )
        ) {
            Text(
                text = stringResource(id = R.string.menu_title_one),
                textAlign = TextAlign.Center,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = stringResource(id = R.string.menu_title_two),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.padding(13.dp))
            Text(
                text = stringResource(id = R.string.menu_subtitle),
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.padding(vertical = 25.dp))

            TextField(
                value = mainViewModel.signUpState.fullName,
                onValueChange = { mainViewModel.onEvent(MainEvent.FullNameChanged(it)) },
                label = { Text(stringResource(id = R.string.menu_label_one)) },
                singleLine = true,
            )

            Spacer(modifier = Modifier.padding(vertical = 12.dp))

            TextField(
                value = mainViewModel.signUpState.passWord,
                onValueChange = { mainViewModel.onEvent(MainEvent.PasswordChanged(it)) },
                label = { Text(stringResource(id = R.string.menu_label_two)) },
                singleLine = true,
            )

            Spacer(modifier = Modifier.padding(vertical = 48.dp))

            Button(
                onClick = {
                    mainViewModel.onEvent(MainEvent.Submit)
                    if (mainViewModel.signUpState.isValid) {

                            dbViewModel.registerProfile(
                                thisName = mainViewModel.signUpState.fullName,
                                thisPassword = mainViewModel.signUpState.passWord
                            )
                            println(dbViewModel.profileState.name)
                            println(mainViewModel.signUpState.fullName)
                            println(dbViewModel.profileState.password)
                            println(mainViewModel.signUpState.passWord)
                            println(dbViewModel.profileState.loggedIn)

                            navController.navigate(route = ScreenRoute.InsertData.route)


                    } else {
                        println("YOU DO NOT HAVE VALID DETAILS YET!") //TODO - Create other indicator of error
                    }

                }
            ) {
                Text(text = stringResource(id = R.string.register_user))
            }
            Text(
                text = dbViewModel.profileState.errorMessage
            )

            Text(
                text ="Delete Profile with entered\n name and password",
                color = Color.Red,
                modifier = Modifier.clickable{}
            )
            Spacer(modifier = Modifier.padding(vertical = 40.dp))

            Text(
                text = "Already have an account? Click here to log in",
                modifier = Modifier.clickable {

                    navController.navigate(route = ScreenRoute.MainMenu.route)
                }
            )
        }
    }
}