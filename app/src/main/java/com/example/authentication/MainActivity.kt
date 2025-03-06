package com.example.authentication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.authentication.ui.theme.AuthenticationTheme
import com.example.authentication.viewmodel.ThemeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import np.com.bimalkafle.firebaseauthdemoapp.AuthViewModel

class MainActivity : ComponentActivity() {
    private val themeViewModel: ThemeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var isSplashScreenVisible = true
        splashScreen.setKeepOnScreenCondition { isSplashScreenVisible }

        lifecycleScope.launch {
            delay(3000L)
            isSplashScreenVisible = false
        }

        enableEdgeToEdge()

        setContent {
            AuthenticationTheme(themeViewModel) {
                val navController = rememberNavController()
                val authViewModel: AuthViewModel = viewModel()
                val notesViewModel: NotesViewModel = viewModel()
                val pinViewModel: PinViewModel = viewModel()
                val nameViewModel: NameViewModel = viewModel()
                val isHidden = false
                NavGraph(
                    navController,
                    authViewModel = authViewModel,
                    pinViewModel = pinViewModel,
                    notesViewModel = notesViewModel,
                    isHidden = isHidden,
                    NameViewModel = nameViewModel
                )
            }
        }
    }
}
