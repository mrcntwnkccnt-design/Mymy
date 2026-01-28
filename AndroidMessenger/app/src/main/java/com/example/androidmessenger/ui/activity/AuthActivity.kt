package com.example.androidmessenger.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidmessenger.data.repository.AuthRepository
import com.example.androidmessenger.databinding.ActivityAuthBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    private val authRepository = AuthRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toggleButton.setOnClickListener {
            toggleAuthMode()
        }

        binding.actionButton.setOnClickListener {
            if (binding.signupContainer.visibility == View.VISIBLE) {
                performSignup()
            } else {
                performLogin()
            }
        }
    }

    private fun toggleAuthMode() {
        if (binding.signupContainer.visibility == View.VISIBLE) {
            // Switch to login
            binding.signupContainer.visibility = View.GONE
            binding.titleText.text = "Login"
            binding.actionButton.text = "Login"
            binding.toggleButton.text = "Create Account"
        } else {
            // Switch to signup
            binding.signupContainer.visibility = View.VISIBLE
            binding.titleText.text = "Create Account"
            binding.actionButton.text = "Register"
            binding.toggleButton.text = "Login"
        }
    }

    private fun performLogin() {
        val email = binding.emailInput.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        binding.actionButton.isEnabled = false
        GlobalScope.launch {
            val result = authRepository.login(email, password)
            runOnUiThread {
                binding.actionButton.isEnabled = true
                result.onSuccess { user ->
                    Toast.makeText(this@AuthActivity, "Login successful! Your ID: ${user.userId}", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@AuthActivity, MainActivity::class.java))
                    finish()
                }
                result.onFailure { error ->
                    Toast.makeText(this@AuthActivity, "Login failed: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun performSignup() {
        val email = binding.emailInput.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()
        val username = binding.usernameInput.text.toString().trim()

        if (email.isEmpty() || password.isEmpty() || username.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.length < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
            return
        }

        binding.actionButton.isEnabled = false
        GlobalScope.launch {
            val result = authRepository.register(email, password, username)
            runOnUiThread {
                binding.actionButton.isEnabled = true
                result.onSuccess { user ->
                    Toast.makeText(this@AuthActivity, "Account created! Your ID: ${user.userId}", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@AuthActivity, MainActivity::class.java))
                    finish()
                }
                result.onFailure { error ->
                    Toast.makeText(this@AuthActivity, "Registration failed: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
