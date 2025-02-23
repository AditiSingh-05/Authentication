package com.example.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

data class Note(var id: String = "", val title: String = "", val content: String = "",val isHidden: Boolean = false)

class NotesViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    private val _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>> get()= _notes



    fun fetchNotes(){
        val userId = auth.currentUser?.uid?:return
        firestore.collection("users").document(userId).collection("notes")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener{snapshot, e->
                if(e == null && snapshot != null){
                    val noteList = snapshot.documents.map{doc ->
                        Note(
                            id = doc.id,
                            title = doc.getString("title") ?: "",
                            content = doc.getString("content") ?: "",
                            isHidden = doc.getBoolean("isHidden") ?: false
                        )
                    }
                    _notes.value = noteList
                }
            }
    }

    fun addNote(title:String,content:String){
        val userId = auth.currentUser?.uid?:return
        val noteData = hashMapOf(
            "title" to title,
            "content" to content,
            "timestamp" to System.currentTimeMillis()
        )
        firestore.collection("users").document(userId).collection("notes").add(noteData)
    }

    fun editNote(noteId: String, newTitle: String, newContent: String) {
        val userId = auth.currentUser?.uid ?: return
        val updatedData = hashMapOf(
            "title" to newTitle,
            "content" to newContent,
            "timestamp" to System.currentTimeMillis()
        )
        firestore.collection("users").document(userId).collection("notes")
            .document(noteId)
            .update(updatedData as Map<String, Any>)
    }
    fun deleteNote(noteId: String) {
        val userId = auth.currentUser?.uid ?: return
        firestore.collection("users").document(userId).collection("notes")
            .document(noteId).delete()
    }

    fun getNoteById(noteId: String): LiveData<Note?> {
        val noteLiveData = MutableLiveData<Note?>()
        noteLiveData.value = _notes.value?.find { it.id == noteId }
        return noteLiveData
    }

    fun toggleHiddenStatus(noteId: String, hide: Boolean) {
        val userId = auth.currentUser?.uid ?: return

        firestore.collection("users").document(userId).collection("notes")
            .document(noteId)
            .update("isHidden", hide)

        val updatedNotes = _notes.value?.map { note ->
            if (note.id == noteId) note.copy(isHidden = hide) else note
        }
        _notes.value = updatedNotes ?: emptyList()
    }





}