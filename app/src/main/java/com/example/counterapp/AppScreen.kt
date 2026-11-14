package com.example.counterapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppScreen(lifecycle: Lifecycle, modifier: Modifier = Modifier) {
    val auth = AuthManager.auth
    var user by remember { mutableStateOf(auth.currentUser) }

    // One listener while started; remove it when stopped or leaving composition.
    // DisposableEffect does not rerun during ordinary recomposition.
    DisposableEffect(lifecycle, auth) {
        val listener = FirebaseAuth.AuthStateListener { user = it.currentUser }
        var listening = false
        fun startListening() {
            if (!listening) {
                listening = true
                auth.addAuthStateListener(listener)
            }
        }
        fun stopListening() {
            if (listening) {
                auth.removeAuthStateListener(listener)
                listening = false
            }
        }
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> startListening()
                Lifecycle.Event.ON_STOP -> stopListening()
                else -> Unit
            }
        }
        lifecycle.addObserver(observer)
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) startListening()
        onDispose {
            stopListening()
            lifecycle.removeObserver(observer)
        }
    }

    val signedInUser = user
    if (signedInUser == null) {
        AuthScreen(modifier)
    } else {
        // Removing this branch at logout removes both counter holders and their saved state.
        // The same user's activity recreation still restores the saved counter normally.
        key(signedInUser.uid) {
            Column(
                modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("State and Exceptions", style = MaterialTheme.typography.headlineMedium)
                Text("Signed in as ${signedInUser.email.orEmpty()}")
                Button(
                    onClick = {
                        AuthManager.logout()
                        user = null
                    },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Log out") }
                HorizontalDivider()
                CounterRoute()
                HorizontalDivider()
                Text("Precondition examples", style = MaterialTheme.typography.titleLarge)
                Text("Check light state and validate non-negative numbers.")
                Button(
                    onClick = {
                        // Run only on a click. Each function handles its own failure.
                        checkLight(false)
                        checkLight(true)
                        checkNumber(-1)
                        checkNumber(0)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Run precondition examples") }
                HorizontalDivider()
                Text("Custom exception examples", style = MaterialTheme.typography.titleLarge)
                Text("Accept words up to 10 characters and handle longer words.")
                Button(
                    onClick = {
                        // Each call handles its own exception, and runs only on a click.
                        demonstrateWordException("hello")
                        demonstrateWordException("abcdefghij")
                        demonstrateWordException("programming")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Run word exception examples") }
                Text(
                    "Results for both demonstrations appear in Logcat. Filter by tag: ExceptionsDemo",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
