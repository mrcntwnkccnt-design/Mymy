package com.example.androidmessenger.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.androidmessenger.R
import com.example.androidmessenger.databinding.ActivityMainBinding
import com.example.androidmessenger.ui.fragment.ChatsFragment
import com.example.androidmessenger.ui.fragment.SearchFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set default fragment
        if (savedInstanceState == null) {
            loadFragment(ChatsFragment())
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_chats -> {
                    loadFragment(ChatsFragment())
                    true
                }
                R.id.nav_search -> {
                    loadFragment(SearchFragment())
                    true
                }
                R.id.nav_profile -> {
                    binding.toolbar.title = "Profile"
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
