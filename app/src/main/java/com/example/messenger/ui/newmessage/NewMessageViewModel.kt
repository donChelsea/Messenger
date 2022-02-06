package com.example.messenger.ui.newmessage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.messenger.models.User
import com.example.messenger.models.UserItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewMessageViewModel @Inject constructor(private val database: FirebaseDatabase) : ViewModel() {
    private val _users = MutableLiveData<List<UserItem>>()
    val users: LiveData<List<UserItem>> = _users

    fun fetchUsers() {
        val userList = mutableListOf<UserItem>()
        val ref = database.getReference("users")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    Log.d("NewMessageViewModel", it.toString())
                    val user = it.getValue(User::class.java)
                    if (user != null) {
                        userList.add(UserItem(user))
                    }
                }
                _users.value = userList
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}