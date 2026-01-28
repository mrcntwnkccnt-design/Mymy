package com.example.androidmessenger.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmessenger.data.models.Message
import com.google.firebase.auth.FirebaseAuth

class MessagesAdapter : ListAdapter<Message, MessagesAdapter.MessageViewHolder>(MessageDiffCallback()) {
    private val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val messageContainer = LinearLayout(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.wrap_content
            )
            orientation = LinearLayout.HORIZONTAL
            setPadding(8, 4, 8, 4)
        }

        val messageText = TextView(parent.context).apply {
            layoutParams = LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.wrap_content,
                1f
            )
            setPadding(12, 8, 12, 8)
        }

        messageContainer.addView(messageText)
        return MessageViewHolder(messageContainer, messageText)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(getItem(position), currentUserId)
    }

    class MessageViewHolder(
        private val container: LinearLayout,
        private val messageText: TextView
    ) : RecyclerView.ViewHolder(container) {
        fun bind(message: Message, currentUserId: String) {
            messageText.text = message.content

            if (message.senderId == currentUserId) {
                // Sent by current user
                container.gravity = android.view.Gravity.END
                messageText.setBackgroundColor(0xFF6200EE.toInt())
                messageText.setTextColor(0xFFFFFFFF.toInt())
            } else {
                // Received message
                container.gravity = android.view.Gravity.START
                messageText.setBackgroundColor(0xFFF5F5F5.toInt())
                messageText.setTextColor(0xFF333333.toInt())
            }
        }
    }

    class MessageDiffCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.messageId == newItem.messageId
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }
    }
}
