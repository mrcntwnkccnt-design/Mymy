package com.example.androidmessenger.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmessenger.data.models.Chat
import com.example.androidmessenger.databinding.ItemChatBinding

class ChatsAdapter : ListAdapter<Chat, ChatsAdapter.ChatViewHolder>(ChatDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ChatViewHolder(private val binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: Chat) {
            binding.userNameText.text = chat.otherUserName
            binding.lastMessageText.text = chat.lastMessage
            binding.timeText.text = formatTime(chat.lastMessageTime)
            
            if (chat.unreadCount > 0) {
                binding.unreadBadge.text = chat.unreadCount.toString()
                binding.unreadBadge.visibility = android.view.View.VISIBLE
            } else {
                binding.unreadBadge.visibility = android.view.View.GONE
            }
        }

        private fun formatTime(timestamp: Long): String {
            val date = java.util.Date(timestamp)
            val formatter = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
            return formatter.format(date)
        }
    }

    class ChatDiffCallback : DiffUtil.ItemCallback<Chat>() {
        override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem.chatId == newItem.chatId
        }

        override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem == newItem
        }
    }
}
