package com.example.counterapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.firebase.auth.FirebaseAuth

// A single small wrapper keeps an in-flight request across activity rotation.
// It never stores the password as a property.
object AuthManager {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Observable properties make Compose redraw the loading indicator and error message.
    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun register(email: String, password: String) = authenticate(email, password, true)

    fun login(email: String, password: String) = authenticate(email, password, false)

    private fun authenticate(email: String, password: String, register: Boolean) {
        if (isLoading) return
        errorMessage = null
        if (email.isBlank() || password.isBlank()) {
            errorMessage = "Enter both an email address and a password."
            return
        }
        isLoading = true
        val task = if (register) {
            auth.createUserWithEmailAndPassword(email.trim(), password)
        } else {
            auth.signInWithEmailAndPassword(email.trim(), password)
        }
        // Firebase runs asynchronously: this callback updates UI state when it finishes.
        // No Activity is captured, so rotation does not interrupt this callback.
        task.addOnCompleteListener { result ->
            isLoading = false
            if (!result.isSuccessful) {
                errorMessage = result.exception?.localizedMessage ?: "Authentication failed. Try again."
            }
        }
    }

    fun logout() {
        auth.signOut()
        errorMessage = null
    }
}
