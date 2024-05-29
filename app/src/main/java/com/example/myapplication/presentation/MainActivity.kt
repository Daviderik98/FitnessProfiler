package com.example.myapplication.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R
import com.example.myapplication.domain.repository.ProfileRepository
import com.example.myapplication.navigation.ScreenRoute
import com.example.myapplication.navigation.setUpNavigation
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SetupDatabase.provideDB(this)
        //Keep working on DB later
        //May need a separate viewModel
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                navController = rememberNavController()
                setUpNavigation(navController = navController)

                /*  Surface(
                      modifier = Modifier.fillMaxSize(),
                      color = MaterialTheme.colorScheme.background
                  ) {
                  }
                 */
            }
        }
    }
}

@Composable
fun mainMenu(
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
                text = stringResource(id = R.string.menu_title_two),
                textAlign = TextAlign.Center,
                fontSize = 38.sp,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
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
            Text(text = mainViewModel.signUpState.fullNameError.toString())
            Spacer(modifier = Modifier.padding(vertical = 12.dp))

            TextField(
                value = mainViewModel.signUpState.passWord,
                onValueChange = { mainViewModel.onEvent(MainEvent.PasswordChanged(it)) },
                label = { Text(stringResource(id = R.string.menu_label_two)) },
                singleLine = true,
            )
            Text(
                text = mainViewModel.signUpState.passWordError.toString()
            )
            Spacer(modifier = Modifier.padding(vertical = 48.dp))



            Button(onClick = {
                mainViewModel.onEvent(MainEvent.Submit)
                if (mainViewModel.signUpState.isValid) {
                            dbViewModel.logInByPassword(
                                password = mainViewModel.signUpState.passWord,
                                fullName = mainViewModel.signUpState.fullName
                            )
                    navController.navigate(route = ScreenRoute.InsertData.route)
                } else {
                    println("YOU DO NOT HAVE VALID DETAILS YET!") //TODO - Create other indicator of error
                }
            }
            ) {
                Text(text = stringResource(id = R.string.login))
            }
            Text(
                text = dbViewModel.profileState.errorMessage
            )

            Spacer(modifier = Modifier.padding(vertical = 40.dp))

            Text(
                text = stringResource(id = R.string.to_registration),
                modifier = Modifier.clickable {
                    navController.navigate(route = ScreenRoute.Registration.route)
                    mainViewModel.betweenMainScreens()
                }
            )
            //The Text Component below is needed for further testing later
            /*
            Text(
                text = "Delete one profile",
                modifier = Modifier.clickable{
                    dbViewModel.deleteOneProfile(
                        mainViewModel.signUpState.fullName,
                        mainViewModel.signUpState.passWord
                    )
                }
            )*/
        }
    }
}
