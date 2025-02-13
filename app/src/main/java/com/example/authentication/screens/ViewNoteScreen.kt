package com.example.authentication.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.authentication.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewNoteScreen(navController: NavController, noteId: String, notesViewModel: NotesViewModel) {
    val noteLiveData = notesViewModel.getNoteById(noteId)
    val note = noteLiveData.observeAsState().value
    val colors = MaterialTheme.colorScheme // Get theme colors

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = note?.title ?: "View Note",
                        style = MaterialTheme.typography.headlineSmall,
                        color = colors.onPrimary
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = colors.primary,
                    titleContentColor = colors.onPrimary
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = colors.onPrimary
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colors.background)
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                if (note != null) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(8.dp, shape = RoundedCornerShape(12.dp))
                            .padding(8.dp),
                        colors = CardDefaults.cardColors(containerColor = colors.surface)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {

                            Text(
                                text = note.content,
                                style = MaterialTheme.typography.bodyMedium,
                                color = colors.onSurface,
                                fontSize = 18.sp
                            )
                        }
                    }
                } else {
                    Text(
                        "Note not found!",
                        style = MaterialTheme.typography.headlineSmall,
                        color = colors.error
                    )
                }
            }
        }
    )
}
