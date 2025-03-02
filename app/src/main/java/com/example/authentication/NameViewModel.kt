package com.example.authentication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions


data class Name(var userID : String = "",val userName : String = "")
class NameViewModel : ViewModel(){

    private val auth = FirebaseAuth.getInstance()
    private val firestore= FirebaseFirestore.getInstance()

    private val _userName = MutableLiveData<Name>()
    val name : LiveData<Name> get() = _userName

    fun setName(userNameValue : String,onResult:(Boolean) -> Unit){
        val userId = auth.currentUser?.uid ?: return

        val nameData = hashMapOf("name" to userNameValue)

        firestore.collection("users")
            .document(userId)
            .set(nameData)
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }
    fun fetchUserName() {
        val userId = auth.currentUser?.uid ?: return

        firestore.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val userNameValue = document.getString("name") ?: "Unknown"
                    _userName.value = Name(userID = userId, userName = userNameValue)
                } else {
                    _userName.value = Name(userID = userId, userName = "No Name Found")
                }
            }
            .addOnFailureListener {
                _userName.value = Name(userID = userId, userName = "Error Fetching Name")
            }
    }

    fun checkIfUsernameExists(userId: String, onResult: (Boolean) -> Unit) {
        firestore.collection("users").document(userId)
            .addSnapshotListener { document, error ->
                if (error != null) {
                    onResult(false)
                    return@addSnapshotListener
                }
                val usernameExists = document?.exists() == true && document.contains("name")
                onResult(usernameExists)
            }
    }




}