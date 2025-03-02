package com.example.authentication.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.authentication.AppScreens
import com.example.authentication.NameViewModel
import np.com.bimalkafle.firebaseauthdemoapp.AuthViewModel

@Composable
fun UserName(navController: NavController,authViewModel: AuthViewModel,NameViewModel: NameViewModel){
    var userName by remember {mutableStateOf("")}
    val colors = MaterialTheme.colorScheme

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Enter Name")
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = userName,
            singleLine = true,
            onValueChange = {
                userName = it
            },

            label = { Text("Enter Name",color = colors.tertiary) },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colors.tertiary,
                unfocusedBorderColor = colors.onSecondary,
                cursorColor = colors.onSecondary,
                focusedLabelColor = colors.onSecondary
            )
        )
        Button(
            onClick = {
                if (userName.isNotEmpty()) {
                    NameViewModel.setName(userName) { success ->
                        if (success) {
                            navController.navigate(AppScreens.HomeScreen.route)
                        }
                    }
                }
            }
        ) {
            Text("Save Name")
        }
    }

}