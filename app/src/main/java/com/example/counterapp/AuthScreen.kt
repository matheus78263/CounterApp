package com.example.counterapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun AuthScreen(modifier: Modifier = Modifier) {
    var email by rememberSaveable { mutableStateOf("") }
    // Keep the password in memory only, outside saved instance state.
    var password by remember { mutableStateOf("") }
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("State and Exceptions", style = MaterialTheme.typography.headlineMedium)
        Text("Account", style = MaterialTheme.typography.titleLarge)
        Text("Log in or register to use the counter and exception examples.")
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email address") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            enabled = !AuthManager.isLoading
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            enabled = !AuthManager.isLoading
        )
        // Requests start only from click callbacks, never from recomposition.
        Button(onClick = { AuthManager.register(email, password) }, enabled = !AuthManager.isLoading,
            modifier = Modifier.fillMaxWidth()) {
            Text("Register")
        }
        Button(onClick = { AuthManager.login(email, password) }, enabled = !AuthManager.isLoading,
            modifier = Modifier.fillMaxWidth()) {
            Text("Log in")
        }
        if (AuthManager.isLoading) CircularProgressIndicator()
        AuthManager.errorMessage?.let { Text(it, color = MaterialTheme.colorScheme.error) }
    }
}
