package com.example.counterapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.counterapp.ui.theme.CounterAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CounterAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Keep the content clear of the system bars using Scaffold's padding.
                    AppScreen(
                        lifecycle = lifecycle,
                        // Consume Scaffold insets so IME padding adds only the remaining keyboard space.
                        modifier = Modifier.padding(innerPadding)
                            .consumeWindowInsets(innerPadding)
                            .imePadding()
                    )
                }
            }
        }
    }
}

