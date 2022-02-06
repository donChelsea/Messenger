package com.example.messenger.ui.messages

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel @Inject constructor(private val auth: FirebaseAuth) : ViewModel() {

    fun getUser(): String? {
        return auth.uid
    }

}