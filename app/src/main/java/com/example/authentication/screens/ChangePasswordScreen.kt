package com.example.authentication.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import np.com.bimalkafle.firebaseauthdemoapp.AuthViewModel

@Composable
fun ChangePasswordScreen(authViewModel: AuthViewModel = viewModel(), navController: NavController) {
    var newPassword by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = { Text("New Password") }
        )
        Button(onClick = {
            authViewModel.auth.currentUser?.updatePassword(newPassword)
                ?.addOnCompleteListener { task ->
                    message = if (task.isSuccessful) "Password updated successfully!" else "Failed to update password"
                }
        }) {
            Text("Update Password")
        }
        Text(text = message)
    }
}
