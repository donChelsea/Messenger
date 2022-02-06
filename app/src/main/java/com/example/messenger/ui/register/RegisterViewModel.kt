package com.example.messenger.ui.register

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.messenger.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val storage: FirebaseStorage,
    private val database: FirebaseDatabase
) : ViewModel() {
    var selectedPhotoUri: Uri? = null
    var username: String? = null

    fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("RegisterViewModel", "created user: ${task.result?.user?.uid}")
                    uploadProfileImageToStorage()
                }
                return@addOnCompleteListener
            }
            .addOnFailureListener { exception ->
                Log.d("RegisterViewModel", "failed to create user: ${exception.message}")
            }
    }

    private fun uploadProfileImageToStorage() {
        if (selectedPhotoUri == null) return

        val filename = UUID.randomUUID().toString()
        val ref = storage.getReference("images/$filename")

        selectedPhotoUri?.let {
            ref.putFile(it).addOnSuccessListener { task ->
                Log.d("RegisterViewModel", "created user: ${task.metadata?.path}")
                ref.downloadUrl.addOnSuccessListener { url ->
                    saveUserToDatabase(username, url)
                    Log.d("RegisterViewModel", "file location: $url")
                }.addOnFailureListener { e ->
                    Log.d("RegisterViewModel", "issue with image uri: ${e.message}")
                }
            }
        }
    }

    private fun saveUserToDatabase(username: String?, profileUri: Uri) {
        val uid = auth.uid ?: ""
        val ref = database.getReference("users/$uid")

        val user = User(uid, username, profileUri.toString())

        ref.setValue(user).addOnSuccessListener {
            Log.d("RegisterViewModel", "$uid added to db")
        }
    }
}