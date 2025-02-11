package com.example.authentication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.authentication.ui.theme.AuthenticationTheme
import np.com.bimalkafle.firebaseauthdemoapp.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AuthenticationTheme {
                val navController = rememberNavController()
                val authViewModel: AuthViewModel = viewModel()
                NavGraph(navController, authViewModel = authViewModel)
            }
        }
    }
}
