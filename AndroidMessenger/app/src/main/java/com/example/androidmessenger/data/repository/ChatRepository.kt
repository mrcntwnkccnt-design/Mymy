package com.example.androidmessenger.data.repository

import com.example.androidmessenger.data.models.User
import com.example.androidmessenger.data.models.Message
import com.example.androidmessenger.data.models.Chat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.UUID

class ChatRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    suspend fun searchUserById(userId: String): User? = try {
        val userDoc = firestore.collection("users").document(userId).get().await()
        userDoc.toObject(User::class.java)
    } catch (e: Exception) {
        null
    }

    suspend fun sendMessage(recipientId: String, content: String): Result<Message> = try {
        val currentUserId = auth.currentUser?.uid ?: throw Exception("Not logged in")
        val messageId = UUID.randomUUID().toString()
        
        val message = Message(
            messageId = messageId,
            senderId = currentUserId,
            recipientId = recipientId,
            content = content
        )
        
        val chatPath = getChatPath(currentUserId, recipientId)
        firestore.collection(chatPath).document(messageId).set(message).await()
        
        updateLastMessage(currentUserId, recipientId, content)
        
        Result.success(message)
    } catch (e: Exception) {
        Result.failure(e)
    }

    private suspend fun updateLastMessage(userId: String, otherUserId: String, lastMessage: String) {
        try {
            val chatPath = getChatPath(userId, otherUserId)
            firestore.collection("chats").document("${userId}_${otherUserId}").update(
                mapOf(
                    "lastMessage" to lastMessage,
                    "lastMessageTime" to System.currentTimeMillis()
                )
            ).await()
        } catch (e: Exception) {
            // Create new chat if it doesn't exist
            try {
                val chatId = "${userId}_${otherUserId}"
                val otherUser = firestore.collection("users").document(otherUserId).get().await()
                    .toObject(User::class.java) ?: return
                
                val chat = Chat(
                    chatId = chatId,
                    userId = userId,
                    otherUserId = otherUserId,
                    otherUserName = otherUser.username,
                    lastMessage = lastMessage
                )
                
                firestore.collection("chats").document(chatId).set(chat).await()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    suspend fun getChats(): List<Chat> = try {
        val currentUserId = auth.currentUser?.uid ?: return emptyList()
        
        val querySnapshot = firestore.collection("chats")
            .whereEqualTo("userId", currentUserId)
            .get()
            .await()
        
        querySnapshot.toObjects(Chat::class.java)
    } catch (e: Exception) {
        emptyList()
    }

    suspend fun getMessages(otherUserId: String): List<Message> = try {
        val currentUserId = auth.currentUser?.uid ?: return emptyList()
        val chatPath = getChatPath(currentUserId, otherUserId)
        
        val querySnapshot = firestore.collection(chatPath)
            .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.ASCENDING)
            .get()
            .await()
        
        querySnapshot.toObjects(Message::class.java)
    } catch (e: Exception) {
        emptyList()
    }

    private fun getChatPath(userId: String, otherUserId: String): String {
        return "chats/${userId}_${otherUserId}/messages"
    }
}
