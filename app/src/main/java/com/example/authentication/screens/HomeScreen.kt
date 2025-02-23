package com.example.authentication.screens

import BasePage
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.test.isSelected
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.authentication.AppScreens
import com.example.authentication.Note
import com.example.authentication.NotesViewModel
import np.com.bimalkafle.firebaseauthdemoapp.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun HomeScreen(navController: NavController, notesViewModel: NotesViewModel, authViewModel: AuthViewModel,isHidden: Boolean) {
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
                {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(
                                imageVector = Icons.Default.Create,
                                contentDescription = "notes",
                                tint = colors.onTertiary
                            )
                        }

                        Text(
                            "Notes",
                            color = colors.onTertiary,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                            ,

                            modifier = Modifier
                                .weight(1f)
//                                .padding(top = 13.dp)
                        )

                        IconButton(
                            onClick = {
                                navController.navigate(AppScreens.SettingsScreen.route)
                            },
                            modifier = Modifier.padding(end = 8.dp) // Reduce padding to prevent cutoff
                        ) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Settings",
                                tint = colors.onTertiary,
                                modifier = Modifier.size(30.dp)
                            )
                        }

                    }




                },
//                title = { Text("Notes", color = colors.onPrimary) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colors.tertiary
                ),
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
                                imageVector = Icons.Default.Close,
                                contentDescription = "Cancel Selection",
                                tint = colors.onTertiary
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(AppScreens.AddNoteScreen.route) },
                containerColor = colors.tertiary
            ) {
                Text("+", fontSize = 40.sp, color = colors.onTertiary)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(
                    color = colors.primary
                )


        ) {
            Spacer(modifier = Modifier.height(8.dp))
            if (notes.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        "No notes found. Click + to add one!",
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.onSecondary
                    )
                }
            } else {
                LazyColumn {
                    items(notes.filter { !it.isHidden }) { note -> // Show only unhidden notes
                        NoteItem(
                            note = note,
                            selectedNotes = selectedNotes,
                            notesViewModel = notesViewModel,
                            isHidden = false, // Home screen
                            onEdit = { navController.navigate("edit_note/${note.id}") }
                        )
                    }
                }

            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteItem(
    note: Note,
    selectedNotes : MutableList<String>,
    notesViewModel: NotesViewModel,
    isHidden: Boolean, onEdit: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    val isSelected = selectedNotes.contains(note.id)
    var offsetX by remember{ mutableStateOf(0f) }
    val swipeThreshold = 100f

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp,bottom = 8.dp)
            .height(100.dp)
            .pointerInput(Unit){
                detectHorizontalDragGestures(
                    onDragEnd = {
                        if(offsetX < -swipeThreshold){
                            notesViewModel.toggleHiddenStatus(note.id,!isHidden)
                        }
                        offsetX = 0f
                    },
                    onHorizontalDrag = {
                        _,dragAmount ->
                        offsetX += dragAmount
                    }
                )
            }
            .offset(x = offsetX.dp)
            .combinedClickable(
                onClick = {
                    if (isSelected) selectedNotes.remove(note.id)
                    else if (selectedNotes.isEmpty()) onEdit()
                },
                onLongClick = {
                    if (isSelected) selectedNotes.remove(note.id)
                    else selectedNotes.add(note.id)
                } //Selection
            )
        ,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(8.dp)
        ,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) colors.secondary else colors.secondary   //onSelected
        )    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = note.title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium,
                color = if (isSelected) colors.onSecondary else colors.onSecondary  //onSelected
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyMedium,
                color = if (isSelected) colors.onSecondary else colors.onSecondary //onSelected

            )
        }
    }
}
