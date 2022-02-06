package com.example.messenger

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val auth: FirebaseAuth): ViewModel() {

    fun signOut() {
        auth.signOut()
    }
}