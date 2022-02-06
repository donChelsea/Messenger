package com.example.messenger.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val auth: FirebaseAuth): ViewModel() {

    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) return@addOnCompleteListener
                Log.d("LoginViewModel", "logged in: ${task.result?.user?.uid}")
            }
            .addOnFailureListener { exception ->
                Log.d("LoginViewModel", "failed to log in user: ${exception.message}")
            }
    }
}