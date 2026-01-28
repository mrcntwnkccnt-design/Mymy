package com.example.androidmessenger.databinding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

object ViewBindingUtil {
    // Activity Auth Binding
    fun createActivityAuthBinding(inflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean = false): ActivityAuthBinding {
        val root = LinearLayout(inflater.context)
        root.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        if (parent != null && attachToParent) parent.addView(root)
        return ActivityAuthBinding(root)
    }

    // Activity Main Binding
    fun createActivityMainBinding(inflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean = false): ActivityMainBinding {
        val root = LinearLayout(inflater.context)
        root.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        if (parent != null && attachToParent) parent.addView(root)
        return ActivityMainBinding(root)
    }

    // Fragment Chats Binding
    fun createFragmentChatsBinding(inflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean = false): FragmentChatsBinding {
        val root = LinearLayout(inflater.context)
        root.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        if (parent != null && attachToParent) parent.addView(root)
        return FragmentChatsBinding(root)
    }

    // Fragment Search Binding
    fun createFragmentSearchBinding(inflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean = false): FragmentSearchBinding {
        val root = LinearLayout(inflater.context)
        root.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        if (parent != null && attachToParent) parent.addView(root)
        return FragmentSearchBinding(root)
    }
}

// Activity Auth Binding
class ActivityAuthBinding(val root: View) {
    val titleText: TextView = root.findViewById<TextView>(android.R.id.text1) ?: TextView(root.context)
    val emailInput: EditText = root.findViewById<EditText>(android.R.id.edit) ?: EditText(root.context)
    val passwordInput: EditText = root.findViewById<EditText>(android.R.id.text2) ?: EditText(root.context)
    val signupContainer: LinearLayout = LinearLayout(root.context)
    val usernameInput: EditText = EditText(root.context)
    val actionButton: Button = root.findViewById<Button>(android.R.id.button1) ?: Button(root.context)
    val toggleButton: Button = root.findViewById<Button>(android.R.id.button2) ?: Button(root.context)

    fun getRoot(): View = root
    fun inflate(inflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean = false): ActivityAuthBinding {
        return ViewBindingUtil.createActivityAuthBinding(inflater, parent, attachToParent)
    }
}

// Activity Main Binding
class ActivityMainBinding(val root: View) {
    val toolbar: View = root.findViewById(android.R.id.action_bar) ?: View(root.context)
    val fragmentContainer: View = root.findViewById(android.R.id.content) ?: View(root.context)
    val bottomNavigation: View = root.findViewById(android.R.id.list) ?: View(root.context)

    fun getRoot(): View = root
    fun inflate(inflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean = false): ActivityMainBinding {
        return ViewBindingUtil.createActivityMainBinding(inflater, parent, attachToParent)
    }
}

// Fragment Chats Binding
class FragmentChatsBinding(val root: View) {
    val chatsList: android.view.View = root.findViewById(android.R.id.list) ?: android.view.View(root.context)
    val progressBar: ProgressBar = root.findViewById<ProgressBar>(android.R.id.progress) ?: ProgressBar(root.context)
    val logoutButton: Button = root.findViewById<Button>(android.R.id.button1) ?: Button(root.context)

    fun getRoot(): View = root
    fun inflate(inflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean = false): FragmentChatsBinding {
        return ViewBindingUtil.createFragmentChatsBinding(inflater, parent, attachToParent)
    }
}

// Fragment Search Binding
class FragmentSearchBinding(val root: View) {
    val userIdInput: EditText = root.findViewById<EditText>(android.R.id.edit) ?: EditText(root.context)
    val searchButton: Button = root.findViewById<Button>(android.R.id.button1) ?: Button(root.context)
    val progressBar: ProgressBar = root.findViewById<ProgressBar>(android.R.id.progress) ?: ProgressBar(root.context)
    val userInfo: LinearLayout = root.findViewById<LinearLayout>(android.R.id.list) ?: LinearLayout(root.context)
    val userNameText: TextView = root.findViewById<TextView>(android.R.id.text1) ?: TextView(root.context)
    val userIdText: TextView = root.findViewById<TextView>(android.R.id.text2) ?: TextView(root.context)
    val startChatButton: Button = root.findViewById<Button>(android.R.id.button2) ?: Button(root.context)

    fun getRoot(): View = root
    fun inflate(inflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean = false): FragmentSearchBinding {
        return ViewBindingUtil.createFragmentSearchBinding(inflater, parent, attachToParent)
    }
}
