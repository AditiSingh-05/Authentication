package com.example.authentication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.authentication.ui.theme.AuthenticationTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import np.com.bimalkafle.firebaseauthdemoapp.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var isSplashScreenVisisble = true

        splashScreen.setKeepOnScreenCondition{
            isSplashScreenVisisble
        }

        lifecycleScope.launch{
            delay(3000L)
            isSplashScreenVisisble = false
        }

        enableEdgeToEdge()


        setContent {
            AuthenticationTheme {
                val navController = rememberNavController()
                val authViewModel: AuthViewModel = viewModel()
                val notesViewModel: NotesViewModel = viewModel()
                val pinViewModel :PinViewModel= viewModel()
                val nameViewModel : NameViewModel = viewModel()
                val isHidden = false
                NavGraph(navController, authViewModel = authViewModel, pinViewModel = pinViewModel,notesViewModel = notesViewModel, isHidden = isHidden, NameViewModel = nameViewModel)
            }
        }
    }


}
