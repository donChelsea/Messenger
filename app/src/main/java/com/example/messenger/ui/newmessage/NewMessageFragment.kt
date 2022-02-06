package com.example.messenger.ui.newmessage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.messenger.R
import com.example.messenger.databinding.FragmentNewMessageBinding
import com.example.messenger.models.UserItem
import com.example.messenger.ui.chatlog.ChatFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import com.xwray.groupie.OnItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewMessageFragment : Fragment() {
    private lateinit var binding: FragmentNewMessageBinding
    private val viewModel: NewMessageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = GroupAdapter<GroupieViewHolder>()

        viewModel.users.observe(viewLifecycleOwner) { users ->
            users.forEach {
                adapter.add(it)
                adapter.setOnItemClickListener { p0, p1 ->
                    val chatFragment = ChatFragment()
                    val args = Bundle().apply {
                        putParcelable(ARG_USER, it.user)
                    }
                    chatFragment.arguments = args
                    requireActivity().supportFragmentManager.beginTransaction()
                        .addToBackStack(ChatFragment.TAG)
                        .replace(R.id.nav_host_fragment_main, chatFragment)
                        .commit()
                }
            }

            binding.recyclerview.adapter = adapter
        }

        viewModel.fetchUsers()
    }

    private val onItemClick = OnItemClickListener { item, view ->
        val chatFragment = ChatFragment()
        val args = Bundle().apply {
        }
        chatFragment.arguments = args
        requireActivity().supportFragmentManager.beginTransaction()
            .addToBackStack(ChatFragment.TAG)
            .replace(R.id.nav_host_fragment_main, chatFragment)
            .commit()
        true
    }

    companion object {
        const val TAG = "NewMessageFragment"
        const val ARG_USER = "user"
    }

}
