package com.example.counterapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier

@Composable
fun CounterRoute(modifier: Modifier = Modifier) {
    // remember keeps this holder across recompositions, but rotation creates a new one at zero.
    val temporaryCounter = remember { CounterState() }

    // rememberSaveable uses our Saver to restore the count after activity recreation, such as rotation.
    val savedCounter = rememberSaveable(saver = CounterState.Saver) { CounterState() }

    // State hoisting: this parent owns the holders; CounterScreen receives values and an event callback.
    CounterScreen(
        temporaryCount = temporaryCounter.count,
        savedCount = savedCounter.count,
        onIncrement = {
            temporaryCounter.increment()
            savedCounter.increment()
        },
        modifier = modifier
    )
}
