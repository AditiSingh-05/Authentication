package com.example.authentication

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.authentication.screens.HomeScreen
import com.example.authentication.screens.LoginScreen
import com.example.authentication.screens.SignupScreen
import np.com.bimalkafle.firebaseauthdemoapp.AuthViewModel

@Composable

fun NavGraph(navController: NavController,authViewModel: AuthViewModel){
    val navController =
        rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.SignupScreen.route) {
        composable(AppScreens.SignupScreen.route) {
            SignupScreen(navController, authViewModel)
        }
        composable(AppScreens.LoginScreen.route) {
            LoginScreen(navController,authViewModel)
        }
        composable(AppScreens.HomeScreen.route) {
            HomeScreen(navController,authViewModel)
        }
    }
}