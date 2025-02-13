package com.example.authentication

sealed class AppScreens(val route : String) {
    object HomeScreen : AppScreens("homescreen")
    object SignupScreen : AppScreens("signupscreen")
    object LoginScreen : AppScreens("logincreen")
    object AddNoteScreen : AppScreens("addnotescreen")
}