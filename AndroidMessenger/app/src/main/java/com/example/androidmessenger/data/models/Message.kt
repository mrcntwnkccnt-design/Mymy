package com.example.androidmessenger.data.models

data class Message(
    val messageId: String = "",
    val senderId: String = "",
    val recipientId: String = "",
    val content: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = false
)
