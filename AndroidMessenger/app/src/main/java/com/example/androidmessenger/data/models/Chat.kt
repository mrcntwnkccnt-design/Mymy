package com.example.androidmessenger.data.models

data class Chat(
    val chatId: String = "",
    val userId: String = "",
    val otherUserId: String = "",
    val otherUserName: String = "",
    val lastMessage: String = "",
    val lastMessageTime: Long = System.currentTimeMillis(),
    val unreadCount: Int = 0
)
