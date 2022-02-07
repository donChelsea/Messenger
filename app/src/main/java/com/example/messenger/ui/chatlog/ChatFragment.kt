package com.example.messenger.ui.chatlog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.messenger.databinding.FragmentChatBinding
import com.example.messenger.models.ReceiveMessageItem
import com.example.messenger.models.SendMessageItem
import com.example.messenger.ui.newmessage.NewMessageFragment.Companion.ARG_USER
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding
    private val viewModel: ChatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) viewModel.toUser = requireArguments().getParcelable(ARG_USER)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchCurrentUser()

        val adapter = GroupAdapter<GroupieViewHolder>()

        viewModel.message.observe(viewLifecycleOwner) { chatMessage ->
            chatMessage?.let {
                if (chatMessage.fromId == viewModel.getUid()) {
                    adapter.add(SendMessageItem(chatMessage.text, viewModel.toUser))
                } else {
                    adapter.add(ReceiveMessageItem(chatMessage.text, viewModel.currentUser))
                }
            }
        }

        binding.apply {
            recyclerview.adapter = adapter

            var text = ""
            sendMessageEt.doAfterTextChanged {
                text = it.toString()
            }

            sendBtn.setOnClickListener {
                viewModel.sendMessage(text)
                sendMessageEt.text.clear()
                recyclerview.scrollToPosition(adapter.itemCount -1)
            }
        }

        viewModel.listenForMessages()
    }

    companion object {
        const val TAG = "ChatFragment"
    }

}