package com.example.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

data class Name(val userName: String = "")

class NameViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    private val _userName = MutableLiveData<Name>()
    val name: LiveData<Name> get() = _userName

    fun setName(userNameValue: String, onResult: (Boolean) -> Unit) {
        val userId = auth.currentUser?.uid ?: return

        val nameData = hashMapOf("name" to userNameValue)

        firestore.collection("users").document(userId)
            .set(nameData)
            .addOnSuccessListener {
                _userName.value = Name(userNameValue)
                onResult(true)
            }
            .addOnFailureListener {
                onResult(false)
            }
    }


    fun doesUserNameExist(onResult: (Boolean) -> Unit) {
        val userId = auth.currentUser?.uid ?: return

        firestore.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                onResult(document.exists() && document.contains("name"))
            }
            .addOnFailureListener {
                onResult(false)
            }
    }

    fun fetchUserName() {
        val userId = auth.currentUser?.uid ?: return

        firestore.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                val userNameValue = document.getString("name") ?: "No Name Found"
                _userName.value = Name(userNameValue)
            }
            .addOnFailureListener {
                _userName.value = Name("Error Fetching Name")
            }
    }
}
