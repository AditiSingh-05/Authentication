package com.example.authentication.screens




import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.authentication.AppScreens
import com.example.authentication.R

import kotlinx.coroutines.delay
import np.com.bimalkafle.firebaseauthdemoapp.AuthViewModel


@Composable
fun SplashScreen(navController: NavController,authViewModel: AuthViewModel){

    val navigationScreen = if (authViewModel.isUserLoggedIn() != null) {
        AppScreens.HomeScreen.route
    } else {
        AppScreens.SignupScreen.route
    }
    LaunchedEffect (Unit){
        delay(3000L)
        navController.navigate(navigationScreen)
    }
    Box(
        modifier = Modifier.fillMaxSize(),

        ){
        Image(
            painter = painterResource(id = R.drawable.splashscreenicon),
            contentDescription = "Splash Screen",
            modifier = Modifier.fillMaxSize(),
            contentScale= ContentScale.FillHeight
        )

    }
}
