package com.example.authentication.screens

import android.graphics.Paint.Align
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.authentication.AppScreens
import np.com.bimalkafle.firebaseauthdemoapp.AuthState
import np.com.bimalkafle.firebaseauthdemoapp.AuthViewModel

@Composable
fun SignupScreen(navController: NavController, authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current


    var isPressed by remember { mutableStateOf(false) }

    // Adding tween() for smooth animation
    val scaleValue by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(durationMillis = 150), // Animation duration
        label = "buttonScale"
    )



    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> navController.navigate(AppScreens.HomeScreen.route)
            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT
            ).show()

            else -> Unit
        }
    }
    //
    val colors = MaterialTheme.colorScheme
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(colors.secondary,colors.secondaryContainer)
    )
    //

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            "Sign Up",
            fontFamily = FontFamily.Serif,
            fontSize = 70.sp,
            fontWeight = FontWeight.ExtraBold,
            color = colors.onSecondary
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text("Email",color = colors.onSecondary)
            },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colors.tertiary,
                unfocusedBorderColor = colors.onSecondary,
                cursorColor = colors.onSecondary,
                focusedLabelColor = colors.onSecondary
            )

        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text("Password",color = colors.onSecondary)
            },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colors.tertiary,
                unfocusedBorderColor = colors.onSecondary,
                cursorColor = colors.onSecondary,
                focusedLabelColor = colors.onSecondary
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                isPressed = true
                authViewModel.signup(email, password)

            },
            enabled = authState.value != AuthState.Loading,
            modifier = Modifier.height(54.dp).width(280.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colors.tertiary)

        ) {
            Text("Sign up",
            fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color =  colors.onSecondary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        Row(
        ) {
            Text("Already have an account? ",
            color = colors.onSecondary
            )
            Text("Login",
                modifier = Modifier.clickable {
                    navController.navigate(AppScreens.LoginScreen.route)
                },
                color = colors.tertiary,
                fontWeight = FontWeight.Bold
            )
        }


    }

}