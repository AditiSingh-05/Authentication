package com.example.authentication

import LoginScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.authentication.screens.AddNoteScreen
import com.example.authentication.screens.ChangePasswordScreen
import com.example.authentication.screens.CustomizeThemeScreen
import com.example.authentication.screens.EditNoteScreen
import com.example.authentication.screens.HiddenNotesScreen
import com.example.authentication.screens.HomeScreen
import com.example.authentication.screens.SetPinScreen
import com.example.authentication.screens.SettingsScreen
import com.example.authentication.screens.SignupScreen
import com.example.authentication.screens.SplashScreen
import com.example.authentication.screens.UserNameScreen
import com.example.authentication.screens.UserProfileScreen
import com.example.authentication.screens.VerifyPinScreen
import com.example.authentication.screens.ViewNoteScreen
import np.com.bimalkafle.firebaseauthdemoapp.AuthViewModel
import java.util.jar.Attributes

@Composable

fun NavGraph(navController: NavController,authViewModel: AuthViewModel,notesViewModel: NotesViewModel,isHidden : Boolean,pinViewModel: PinViewModel,NameViewModel: NameViewModel){
    val navController = rememberNavController()

    val startDestination = if (authViewModel.isUserLoggedIn() != null) {
        AppScreens.HomeScreen.route
    } else {
        AppScreens.LoginScreen.route
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(AppScreens.SignupScreen.route) {
            SignupScreen(navController, authViewModel)
        }
        composable(AppScreens.LoginScreen.route) {
            LoginScreen(navController,authViewModel, NameViewModel )
        }
        composable(AppScreens.HomeScreen.route) {
            HomeScreen(navController,notesViewModel,authViewModel, isHidden)
        }
        composable(AppScreens.HiddenNotesScreen.route) { HiddenNotesScreen(navController,notesViewModel)
        }
        composable(AppScreens.AddNoteScreen.route) {
            AddNoteScreen(navController,notesViewModel)
        }

        composable("view_note/{noteId}") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId") ?: return@composable
            ViewNoteScreen(navController,noteId, notesViewModel)
        }
        composable("edit_note/{noteId}") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId") ?: return@composable
            EditNoteScreen(navController, noteId, notesViewModel)
        }
        composable(AppScreens.splashScreen.route){
            SplashScreen(navController,authViewModel)

        }
        composable(AppScreens.SettingsScreen.route){
            SettingsScreen(navController,authViewModel, pinViewModel)
        }
        composable(AppScreens.VerifyPinScreen.route){
            VerifyPinScreen(navController,pinViewModel,authViewModel)
        }
        composable(AppScreens.SetPinScreen.route){
            SetPinScreen(navController,pinViewModel,authViewModel)
        }
        composable(AppScreens.CustomizeThemeScreen.route){
            CustomizeThemeScreen(navController)
        }
        composable(AppScreens.PrivacyPolicyScreen.route){
            //Laterrrr
        }
        composable(AppScreens.UserProfileScreen.route){
            UserProfileScreen(navController,authViewModel,NameViewModel)
        }
        composable(AppScreens.UserNameScreen.route){
            UserNameScreen(navController,authViewModel,NameViewModel)
        }
        composable(AppScreens.ChangePasswordScreen.route){
            ChangePasswordScreen(authViewModel,navController)
        }

    }
}