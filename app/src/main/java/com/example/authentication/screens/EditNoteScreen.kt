package com.example.authentication.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.authentication.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(
    navController: NavController,
    noteId: String,
    notesViewModel: NotesViewModel
) {
    val noteLiveData = notesViewModel.getNoteById(noteId)
    val note = noteLiveData.observeAsState().value
    val colors = MaterialTheme.colorScheme

    var title by remember { mutableStateOf(note?.title ?: "") }
    var content by remember { mutableStateOf(note?.content ?: "") }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Edit Note",
                        style = MaterialTheme.typography.headlineSmall,
                        color = colors.onTertiary
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = colors.tertiary,
                    titleContentColor = colors.onTertiary
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = colors.onTertiary
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            notesViewModel.editNote(noteId, title, content)
                            navController.popBackStack() // Navigate back after saving
                        }
                    ) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = "Save",
                            tint = colors.onTertiary
                        )
                    }
                }
            )


        },

    ){
            paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colors.secondary)
                .padding(paddingValues)
//                    .padding(16.dp)

        ){
            if (note != null) {

                Column(modifier = Modifier.padding(16.dp)) {

                    TextField(
                        value = content,
                        onValueChange = { content = it },
                        textStyle = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxSize(),
                        maxLines = Int.MAX_VALUE,
                        singleLine = false,
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        )
                    )
                }

            } else {
                Text(
                    "Note not found!",
                    style = MaterialTheme.typography.headlineSmall,
                    color = colors.onError
                )
            }

        }
    }
}