package com.example.authentication.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.authentication.AppScreens
import np.com.bimalkafle.firebaseauthdemoapp.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController,authViewModel: AuthViewModel) {
    val colors = MaterialTheme.colorScheme
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        IconButton(
                            onClick = {navController.navigate(AppScreens.HomeScreen.route)}
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "notes",
                                tint = colors.onTertiary
                            )
                        }
                        Text("Settings",
                            color = colors.onTertiary
                        )
                    }

                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colors.tertiary
                )
            )
        }
    ) { ScreenPadding ->
        Column(
            modifier = Modifier.padding(ScreenPadding)
                .background(
                    color = colors.secondary
                ),



        ) {
            Divider(thickness = 3.dp,
                color = colors.primary)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {

                    }
                    .height(56.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    "Account",
                    modifier = Modifier.padding(start = 28.dp),
                    fontSize = 24.sp,
                    color = colors.onSecondary
                )

            }
            Divider(
                thickness = 3.dp,
                color = colors.primary
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {

                    }
                    .height(56.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    "Theme",
                    modifier = Modifier.padding(start = 28.dp),
                    fontSize = 24.sp
                )

            }
            Divider(thickness = 3.dp,
                color = colors.primary)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(AppScreens.HiddenNotesScreen.route)
                    }
                    .height(56.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    "Hidden Notes", //can add touch actions laterr
                    modifier = Modifier.padding(start = 28.dp),
                    fontSize = 24.sp
                )

            }
            Divider(thickness = 3.dp,
                color = colors.primary)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {

                    }
                    .height(56.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    "Privacy Policy",
                    modifier = Modifier.padding(start = 28.dp),
                    fontSize = 24.sp
                )

            }
            Divider(thickness = 3.dp,
                color = colors.primary)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clickable {
                        authViewModel.signout()
                        navController.navigate(AppScreens.LoginScreen.route) {
                            popUpTo(AppScreens.HomeScreen.route) {
                                inclusive = true
                            }
                        }
                    }
                ,
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    "Sign Out",
                    modifier = Modifier.padding(start = 28.dp),
                    fontSize = 24.sp
                )

            }
            Divider(thickness = 3.dp,
                color = colors.primary)
        }
    }

}