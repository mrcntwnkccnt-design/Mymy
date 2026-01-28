package com.example.androidmessenger.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.androidmessenger.data.repository.ChatRepository
import com.example.androidmessenger.databinding.FragmentSearchBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val chatRepository = ChatRepository()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchButton.setOnClickListener {
            searchUser()
        }

        binding.userIdInput.setOnEditorActionListener { _, _, _ ->
            searchUser()
            true
        }
    }

    private fun searchUser() {
        val userId = binding.userIdInput.text.toString().trim()

        if (userId.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter a user ID", Toast.LENGTH_SHORT).show()
            return
        }

        binding.progressBar.visibility = View.VISIBLE
        GlobalScope.launch {
            val user = chatRepository.searchUserById(userId)
            requireActivity().runOnUiThread {
                binding.progressBar.visibility = View.GONE
                if (user != null) {
                    binding.userInfo.visibility = View.VISIBLE
                    binding.userNameText.text = "Username: ${user.username}"
                    binding.userIdText.text = "ID: ${user.userId}"

                    binding.startChatButton.setOnClickListener {
                        // Navigate to chat screen
                        Toast.makeText(requireContext(), "Opening chat with ${user.username}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    binding.userInfo.visibility = View.GONE
                    Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
