package com.example.androidmessenger.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidmessenger.data.repository.AuthRepository
import com.example.androidmessenger.data.repository.ChatRepository
import com.example.androidmessenger.databinding.FragmentChatsBinding
import com.example.androidmessenger.ui.activity.AuthActivity
import com.example.androidmessenger.ui.adapter.ChatsAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ChatsFragment : Fragment() {
    private lateinit var binding: FragmentChatsBinding
    private val chatRepository = ChatRepository()
    private val authRepository = AuthRepository()
    private val chatsAdapter = ChatsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.chatsList.layoutManager = LinearLayoutManager(requireContext())
        binding.chatsList.adapter = chatsAdapter

        binding.logoutButton.setOnClickListener {
            authRepository.logout()
            startActivity(Intent(requireContext(), AuthActivity::class.java))
            requireActivity().finish()
        }

        loadChats()
    }

    private fun loadChats() {
        binding.progressBar.visibility = View.VISIBLE
        GlobalScope.launch {
            val chats = chatRepository.getChats()
            requireActivity().runOnUiThread {
                binding.progressBar.visibility = View.GONE
                chatsAdapter.submitList(chats)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadChats()
    }
}
