package com.example.authentication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.authentication.AppScreens
import com.example.authentication.NameViewModel
import np.com.bimalkafle.firebaseauthdemoapp.AuthViewModel

@Composable
fun UserNameScreen(navController: NavController, authViewModel: AuthViewModel, nameViewModel: NameViewModel) {
    var userName by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val colors = MaterialTheme.colorScheme

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Enter Name")
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = userName,
            singleLine = true,
            onValueChange = { userName = it },
            label = { Text("Enter Name", color = colors.tertiary) },
            textStyle = LocalTextStyle.current.copy(color = colors.onSecondary),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colors.tertiary,
                unfocusedBorderColor = colors.onPrimary,
                cursorColor = colors.onSecondary,
                focusedLabelColor = colors.onSecondary
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (userName.isNotEmpty()) {
                    nameViewModel.setName(userName) { success ->
                        if (success) {
                            navController.navigate(AppScreens.HomeScreen.route)
                        } else {
                            errorMessage = "Failed to save name. Try again!"
                        }
                    }
                } else {
                    errorMessage = "Name cannot be empty!"
                }
            },
            modifier = Modifier.height(54.dp).width(280.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colors.tertiary)
        ) {
            Text("Save Name",color = colors.onTertiary)
        }

        errorMessage?.let {
            Spacer(modifier = Modifier.height(12.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }
    }
}
