package com.example.authentication

sealed class AppScreens(val route : String) {
    object HomeScreen : AppScreens("homescreen")
    object SignupScreen : AppScreens("signupscreen")
    object LoginScreen : AppScreens("logincreen")
    object AddNoteScreen : AppScreens("addnotescreen")
    object splashScreen : AppScreens("splashscreen")
    object SettingsScreen : AppScreens("settingscreen")
    object ViewNoteScreen : AppScreens("viewnotescreen")
    object HiddenNotesScreen : AppScreens("hiddennotescreen")
    object VerifyPinScreen : AppScreens("verifypinscreen")
    object SetPinScreen : AppScreens("setpinscreen")
    object CustomizeThemeScreen : AppScreens("customizethemescreen")
    object PrivacyPolicyScreen : AppScreens("privacypolicyscreen")
    object UserProfileScreen : AppScreens("userprofilescreen")
    object UserNameScreen : AppScreens("usernamescreen")
}