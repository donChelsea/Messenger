package com.example.messenger.ui.chatlog

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.messenger.models.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase
) : ViewModel() {

    private val _message = MutableLiveData<ChatMessage?>()
    val message: LiveData<ChatMessage?> = _message

    var toUser: User? = null
    var currentUser: User? = null

    fun sendMessage(text: String) {
        val fromId = auth.uid
        val toId = toUser?.uid
        val ref = database.getReference("user-messages/$fromId/$toId").push()
        val toRef = database.getReference("user-messages/$toId/$fromId").push()

        if (ref.key == null || fromId == null || toId == null) return

        val chatMessage = ChatMessage(ref.key!!, text, fromId, toId, System.currentTimeMillis() / 1000)
        ref.setValue(chatMessage)
            .addOnSuccessListener {
                Log.d("ChatViewModel", "SUCCESS: ${ref.key}")
            }
        toRef.setValue(chatMessage)
    }

    fun listenForMessages() {
        val fromId = auth.uid
        val toId = toUser?.uid
        val ref = database.getReference("user-messages/$fromId/$toId")

        ref.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = snapshot.getValue(ChatMessage::class.java)
                chatMessage?.let {
                    Log.d("ChatViewModel", chatMessage.text)
                    _message.value = chatMessage
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getUid() = auth.uid

    fun fetchCurrentUser() {
        val uid = auth.uid
        val ref = database.getReference("users/$uid")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                currentUser = snapshot.getValue(User::class.java)
                Log.d("ChatViewModel", currentUser?.profileImageUrl.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}