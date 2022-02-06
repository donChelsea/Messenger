package com.example.messenger.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val auth: FirebaseAuth): ViewModel() {

    fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) return@addOnCompleteListener
                Log.d("RegisterViewModel", "created user: ${task.result?.user?.uid}")
            }
            .addOnFailureListener { exception ->
                Log.d("RegisterViewModel", "failed to create user: ${exception.message}")
            }
    }
}