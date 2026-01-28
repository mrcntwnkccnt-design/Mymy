package com.example.androidmessenger.data.models

data class User(
    val userId: String = "",
    val username: String = "",
    val email: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val lastSeen: Long = System.currentTimeMillis()
)
