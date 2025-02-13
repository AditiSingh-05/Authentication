package com.example.authentication.screens

import BasePage
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.authentication.AppScreens
import com.example.authentication.Note
import com.example.authentication.NotesViewModel
import np.com.bimalkafle.firebaseauthdemoapp.AuthViewModel

@Composable
fun HomeScreen(navController: NavController, notesViewModel: NotesViewModel, authViewModel: AuthViewModel) {
    val notes by notesViewModel.notes.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        notesViewModel.fetchNotes()
    }

    val colors = MaterialTheme.colorScheme

    BasePage(
        navController = navController,
        title = "Home",
        authViewModel = authViewModel,
        content = {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    "Your Notes",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(16.dp),
                    color = colors.onBackground
                )
                Spacer(modifier = Modifier.height(10.dp))

                if (notes.isEmpty()) {
                    Text(
                        "No notes found. Click + to add one!",
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.onSurface
                    )
                } else {
                    LazyColumn {
                        items(notes) { note ->
                            NoteItem(note) {
                                navController.navigate("view_note/${note.id}")
                            }
                        }
                    }
                }
            }
        },
        fab = {
            FloatingActionButton(
                onClick = { navController.navigate(AppScreens.AddNoteScreen.route) },
                containerColor = colors.primary
            ) {
                Text(
                    "+",
                    fontSize = 40.sp,
                    color = colors.onPrimary
                )
            }
        }
    )
}

@Composable
fun NoteItem(note: Note, onClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = colors.secondary)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleMedium,
                color = colors.onSecondary  // Title color
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyMedium,
                color = colors.onSecondary
            )
        }
    }
}
