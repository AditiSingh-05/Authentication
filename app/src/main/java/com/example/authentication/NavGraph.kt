package com.example.authentication

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.authentication.screens.AddNoteScreen
import com.example.authentication.screens.HomeScreen
import com.example.authentication.screens.LoginScreen
import com.example.authentication.screens.SignupScreen
import com.example.authentication.screens.ViewNoteScreen
import np.com.bimalkafle.firebaseauthdemoapp.AuthViewModel

@Composable

fun NavGraph(navController: NavController,authViewModel: AuthViewModel,notesViewModel: NotesViewModel){
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
            HomeScreen(navController,notesViewModel,authViewModel)
        }
        composable(AppScreens.AddNoteScreen.route) {
            AddNoteScreen(navController,notesViewModel)
        }

        composable("view_note/{noteId}") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId") ?: return@composable
            ViewNoteScreen(navController,noteId, notesViewModel)
        }

    }
}