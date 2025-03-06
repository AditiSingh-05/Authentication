package com.example.authentication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.authentication.AppScreens
import com.example.authentication.NameViewModel
import np.com.bimalkafle.firebaseauthdemoapp.AuthViewModel
@Composable
fun UserProfileScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(),
    nameViewModel: NameViewModel = viewModel()
) {
    val colors = MaterialTheme.colorScheme
    val nameState by nameViewModel.name.observeAsState()
    val userEmail = authViewModel.getUserEmail()

    var isEditing by remember { mutableStateOf(false) }
    var newName by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        nameViewModel.fetchUserName()
    }

    LaunchedEffect(nameState) {
        nameState?.userName?.let { newName = it }
    }

    Scaffold(
        topBar = { ProfileTopBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = CardDefaults.cardColors(containerColor = colors.tertiary)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Email: $userEmail",
                        style = MaterialTheme.typography.bodyLarge,
                        color = colors.onTertiary
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Name: ",
                            style = MaterialTheme.typography.bodyLarge,
                            color = colors.onTertiary
                        )

                        if (isEditing) {
                            OutlinedTextField(
                                value = newName,
                                onValueChange = { newName = it },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                                keyboardActions = KeyboardActions(onDone = { isEditing = false }),
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 8.dp)
                            )
                        } else {
                            Text(
                                text = newName.ifBlank { "Fetching..." },
                                style = MaterialTheme.typography.bodyLarge,
                                color = colors.onTertiary,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }

                        // Edit Name Button (Pencil Icon)
                        IconButton(onClick = { isEditing = true }) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit Name",
                                tint = colors.primary
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { navController.navigate(AppScreens.ChangePasswordScreen.route) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.tertiary,
                        contentColor = colors.onTertiary
                    )
                ) {
                    Text("Change Password")
                }

                if (isEditing) {
                    Button(
                        onClick = {
                            nameViewModel.setName(newName) { success ->
                                if (success) isEditing = false
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colors.tertiary,
                            contentColor = colors.onTertiary
                        )
                    ) {
                        Text("Save Name")
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopBar(navController: NavController) {
    TopAppBar(
        title = { Text("Profile") },
        navigationIcon = {
            IconButton(onClick = { navController.navigate(AppScreens.SettingsScreen.route) }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            titleContentColor = MaterialTheme.colorScheme.onTertiary
        )
    )
}
