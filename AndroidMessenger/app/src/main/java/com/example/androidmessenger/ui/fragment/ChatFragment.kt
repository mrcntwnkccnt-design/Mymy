package com.example.androidmessenger.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Button
import android.widget.TextView
import android.widget.ProgressBar
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmessenger.data.repository.ChatRepository
import com.example.androidmessenger.data.repository.AuthRepository
import com.example.androidmessenger.ui.adapter.MessagesAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ChatFragment : Fragment() {
    private val chatRepository = ChatRepository()
    private val authRepository = AuthRepository()
    private val messagesAdapter = MessagesAdapter()
    private var recipientId: String = ""
    private var recipientName: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = LinearLayout(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            orientation = LinearLayout.VERTICAL
        }

        // Get arguments
        recipientId = arguments?.getString("recipientId") ?: ""
        recipientName = arguments?.getString("recipientName") ?: "User"

        // Create UI elements
        val toolbar = LinearLayout(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.wrap_content
            )
            orientation = LinearLayout.HORIZONTAL
            backgroundColor = 0xFF6200EE.toInt()
        }

        val titleText = TextView(requireContext()).apply {
            text = recipientName
            textSize = 18f
            setTextColor(0xFFFFFFFF.toInt())
            layoutParams = LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.wrap_content,
                1f
            )
            setPadding(16, 16, 16, 16)
        }
        toolbar.addView(titleText)

        view.addView(toolbar)

        // Messages RecyclerView
        val messagesRecycler = RecyclerView(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1f
            )
            layoutManager = LinearLayoutManager(requireContext())
            adapter = messagesAdapter
        }
        view.addView(messagesRecycler)

        // Input area
        val inputArea = LinearLayout(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.wrap_content
            )
            orientation = LinearLayout.HORIZONTAL
            setPadding(8, 8, 8, 8)
        }

        val messageInput = EditText(requireContext()).apply {
            hint = "Type a message..."
            layoutParams = LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.wrap_content,
                1f
            )
            setPadding(8, 8, 8, 8)
        }
        inputArea.addView(messageInput)

        val sendButton = Button(requireContext()).apply {
            text = "Send"
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.wrap_content,
                ViewGroup.LayoutParams.wrap_content
            )
            setOnClickListener {
                val message = messageInput.text.toString().trim()
                if (message.isNotEmpty()) {
                    sendMessage(message)
                    messageInput.text.clear()
                }
            }
        }
        inputArea.addView(sendButton)

        view.addView(inputArea)

        loadMessages()

        return view
    }

    private fun sendMessage(content: String) {
        GlobalScope.launch {
            chatRepository.sendMessage(recipientId, content)
            requireActivity().runOnUiThread {
                loadMessages()
            }
        }
    }

    private fun loadMessages() {
        GlobalScope.launch {
            val messages = chatRepository.getMessages(recipientId)
            requireActivity().runOnUiThread {
                messagesAdapter.submitList(messages)
            }
        }
    }
}
