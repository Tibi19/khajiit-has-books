package com.tam.tesbooks.test

sealed class TestDestination(val route: String) {
    object A: TestDestination("A")
    object B: TestDestination("B")
    object C: TestDestination("C")
}