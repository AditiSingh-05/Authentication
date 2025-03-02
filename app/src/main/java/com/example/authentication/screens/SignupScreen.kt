package com.example.authentication.screens

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
    var passwordVisible by remember { mutableStateOf(false) }

    val authState by authViewModel.authState.observeAsState()
    val context = LocalContext.current

    val colors = MaterialTheme.colorScheme
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(colors.secondary, colors.secondaryContainer)
    )

    val scaleValue by animateFloatAsState(
        targetValue = if (authState == AuthState.Loading) 0.95f else 1f,
        animationSpec = tween(durationMillis = 150),
        label = "buttonScale"
    )

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> navController.navigate(AppScreens.UserNameScreen.route)
            is AuthState.Error -> Toast.makeText(
                context,
                (authState as AuthState.Error).message,
                Toast.LENGTH_SHORT
            ).show()

            else -> Unit
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().background(gradientBrush),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(80.dp))

        Text(
            "Sign Up",
            fontFamily = FontFamily.Serif,
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            color = colors.onSecondary
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", color = colors.onSecondary) },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colors.tertiary,
                unfocusedBorderColor = colors.onSecondary
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", color = colors.onSecondary) },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colors.tertiary,
                unfocusedBorderColor = colors.onSecondary
            ),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                Text(
                    if (passwordVisible) "Hide" else "Show",
                    color = colors.tertiary,
                    modifier = Modifier.clickable { passwordVisible = !passwordVisible }
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { authViewModel.signup(email, password) },
            enabled = authState != AuthState.Loading,
            modifier = Modifier
                .height(54.dp)
                .width(280.dp)
                .clickable { },
            colors = ButtonDefaults.buttonColors(containerColor = colors.tertiary)
        ) {
            if (authState == AuthState.Loading) {
                CircularProgressIndicator(color = colors.onSecondary)
            } else {
                Text("Sign Up", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = colors.onSecondary)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Text("Already have an account? ", color = colors.onSecondary)
            Text(
                "Login",
                modifier = Modifier.clickable { navController.navigate(AppScreens.LoginScreen.route) },
                color = colors.tertiary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
