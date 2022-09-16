package com.tam.tesbooks.util

fun String.insert(atIndex: Int, insertString: String): String =
    substring(0, atIndex) + insertString + substring(atIndex)