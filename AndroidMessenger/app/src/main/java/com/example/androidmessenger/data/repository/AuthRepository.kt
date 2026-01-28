package com.example.androidmessenger.data.repository

import com.example.androidmessenger.data.models.Chat
import com.example.androidmessenger.data.models.Message
import com.example.androidmessenger.data.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.UUID

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun register(email: String, password: String, username: String): Result<User> = try {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        val userId = result.user?.uid ?: throw Exception("User ID not found")
        
        val user = User(
            userId = userId,
            username = username,
            email = email
        )
        
        firestore.collection("users").document(userId).set(user).await()
        Result.success(user)
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun login(email: String, password: String): Result<User> = try {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        val userId = result.user?.uid ?: throw Exception("User ID not found")
        
        val userDoc = firestore.collection("users").document(userId).get().await()
        val user = userDoc.toObject(User::class.java) ?: throw Exception("User data not found")
        
        Result.success(user)
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun getCurrentUser(): User? = try {
        val userId = auth.currentUser?.uid ?: return null
        val userDoc = firestore.collection("users").document(userId).get().await()
        userDoc.toObject(User::class.java)
    } catch (e: Exception) {
        null
    }

    fun logout() {
        auth.signOut()
    }

    fun isUserLoggedIn(): Boolean = auth.currentUser != null
}
