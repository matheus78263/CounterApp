package com.example.counterapp

import android.util.Log

private const val EXCEPTIONS_TAG = "ExceptionsDemo"

fun validateWord(word: String) {
    if (word.length > 10) {
        // throw raises an exception; Kotlin constructs it without the Java keyword new.
        throw WordTooLongException(word)
    }
}

fun demonstrateWordException(word: String) {
    try {
        validateWord(word)
        Log.d(EXCEPTIONS_TAG, "Word accepted: '$word' (${word.length} characters).")
    } catch (exception: WordTooLongException) {
        // catch handles this specific exception so the app can continue running.
        Log.d(EXCEPTIONS_TAG, "${exception.javaClass.simpleName}: ${exception.message}")
    }
}

fun checkLight(isLightOn: Boolean) {
    try {
        // check validates state and throws IllegalStateException when false.
        check(isLightOn) { "The light must be on." }
        Log.d(EXCEPTIONS_TAG, "Light check passed: the light is on.")
    } catch (exception: IllegalStateException) {
        // Handle this failure here so the next example can still run.
        Log.d(EXCEPTIONS_TAG, "${exception.javaClass.simpleName}: ${exception.message}")
    }
}

fun checkNumber(number: Int) {
    try {
        // require validates an argument and throws IllegalArgumentException when false.
        require(number >= 0) { "The number must be non-negative: $number." }
        Log.d(EXCEPTIONS_TAG, "Number check passed: $number is non-negative.")
    } catch (exception: IllegalArgumentException) {
        Log.d(EXCEPTIONS_TAG, "${exception.javaClass.simpleName}: ${exception.message}")
    }
}
