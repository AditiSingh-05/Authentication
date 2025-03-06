package com.example.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions


data class Pin( var userID:String = "",val pin : String = "")

class PinViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    private val _pin = MutableLiveData<Pin>()
    val pin : LiveData<Pin> get()= _pin

    fun setPin(pinValue: String, onComplete: (Boolean) -> Unit) {
        val userId = auth.currentUser?.uid ?: return
        val pinData = hashMapOf("pin" to pinValue)

        firestore.collection("users")
            .document(userId)
            .set(pinData, SetOptions.merge())
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }

    fun fetchPin(){

    }

    fun ifPinExists(onResult: (Boolean) -> Unit){
        val userId = auth.currentUser?.uid ?: return
        firestore.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                onResult(document.contains("pin"))
            }
    }

    fun verifyPin(enteredPin: String, onResult: (Boolean) -> Unit) {
        val userId = auth.currentUser?.uid ?: return
        val pinRef = firestore.collection("users").document(userId)

        pinRef.get().addOnSuccessListener { documents ->
            if (documents.exists()) {
                val savedPin = documents.getString("pin") ?: ""
                onResult(enteredPin == savedPin)
            } else {
                onResult(false)
            }
        }.addOnFailureListener {
            onResult(false)
        }
    }


}