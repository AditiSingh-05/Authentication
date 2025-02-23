package com.example.authentication.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.authentication.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HiddenNotesScreen(navController: NavController, notesViewModel: NotesViewModel) {
    val notes by notesViewModel.notes.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        notesViewModel.fetchNotes()
    }

    val colors = MaterialTheme.colorScheme
    val selectedNotes = remember { mutableStateListOf<String>() }

    fun deleteSelectedNotes() {
        selectedNotes.forEach { noteId ->
            notesViewModel.deleteNote(noteId)
        }
        selectedNotes.clear()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Hidden Notes",
                        color = colors.onTertiary,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colors.tertiary
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = colors.onTertiary
                        )
                    }
                },
                actions = {
                    if (selectedNotes.isNotEmpty()) {
                        IconButton(onClick = { deleteSelectedNotes() }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = colors.onTertiary
                            )
                        }
                        IconButton(onClick = { selectedNotes.clear() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Cancel Selection",
                                tint = colors.onTertiary
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(color = colors.primary)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            if (notes.none { it.isHidden }) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        "No hidden notes found!",
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.onSecondary
                    )
                }
            } else {
                LazyColumn {
                    items(notes.filter { it.isHidden }) { note ->
                        NoteItem(
                            note = note,
                            selectedNotes = selectedNotes,
                            notesViewModel = notesViewModel,
                            isHidden = true, // Hidden Notes screen
                            onEdit = { navController.navigate("edit_note/${note.id}") }
                        )
                    }
                }
            }
        }
    }
}
