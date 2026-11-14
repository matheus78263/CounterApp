package com.example.counterapp

// Inherit from Exception and pass our message to its constructor.
// Kotlin classes are final by default; open allows this class to be extended.
open class WordTooLongException(word: String) : Exception(
    "Word '$word' has ${word.length} characters; the maximum is 10."
)
