package com.example.messenger.ui.messages

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.messenger.databinding.FragmentMessagesBinding
import com.example.messenger.models.MessagesItem
import com.example.messenger.ui.login.LoginActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MessagesFragment : Fragment() {
    private lateinit var binding: FragmentMessagesBinding
    private val viewModel: MessagesViewModel by viewModels()
    private val adapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessagesBinding.inflate(inflater, container, false)
        binding.recyclerview.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.latestMessage.observe(viewLifecycleOwner) { message ->
            adapter.add(MessagesItem(message))

        }


        verifyUserIsLoggedIn()
        viewModel.fetchCurrentUser()
    }

    private fun verifyUserIsLoggedIn() {
        val uid = viewModel.getUser()
        if (uid == null) {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }


}