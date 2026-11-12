package com.example.counterapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.setValue

class CounterState(initialCount: Int = 0) {
    // This observable class property makes Compose update the displayed count when it changes.
    var count by mutableIntStateOf(initialCount)
        private set

    fun increment() {
        count++
    }

    companion object {
        // Save only the integer; restore it inside a new CounterState instance.
        val Saver: Saver<CounterState, Int> = Saver(
            save = { it.count },
            restore = { CounterState(it) }
        )
    }
}
