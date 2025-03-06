package com.example.authentication.screens


import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.authentication.AppScreens
import com.example.authentication.Pin
import com.example.authentication.PinViewModel
import np.com.bimalkafle.firebaseauthdemoapp.AuthViewModel

@Composable
fun VerifyPinScreen(navController: NavController,pinViewModel: PinViewModel,authViewModel: AuthViewModel){
    var pin by remember { mutableStateOf("") }

    val colors = MaterialTheme.colorScheme

//    val pinViewModel: PinViewModel = viewModel()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Verify Pin"
        )
        Spacer(modifier = Modifier.height(12.dp))



        OutlinedTextField(
            value = pin,
            singleLine= true,
            onValueChange = { newText ->
                if (newText.length <= 4 && newText.all { it.isDigit() }) {
                    pin = newText
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            visualTransformation = PasswordVisualTransformation(),
            label = { Text("Enter PIN",color = colors.tertiary) },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colors.tertiary,
                unfocusedBorderColor = colors.onPrimary,
                cursorColor = colors.onSecondary,
                focusedLabelColor = colors.onSecondary
            )
        )
        Spacer(modifier = Modifier.height(12.dp))




        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick={
                pinViewModel.verifyPin(pin){isCorrect ->
                    if (isCorrect) {
                        navController.navigate(AppScreens.HiddenNotesScreen.route)
                    } else {
                        Log.d("PIN_CHECK", "Incorrect PIN!")
                    }

                }
            },
            modifier = Modifier.height(54.dp).width(280.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colors.tertiary)

        ){
            Text("Verify Pin",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color =  colors.onSecondary)

        }

    }
}