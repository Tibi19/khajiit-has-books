package com.tam.tesbooks.test

import com.tam.tesbooks.presentation.navigation.ARG_BOOK_ID

const val ARG_C_MESSAGE = "c_message"

sealed class TestDestination(val route: String) {
    object A: TestDestination("A")
    object B: TestDestination("B")
    object C: TestDestination("C?$ARG_C_MESSAGE={$ARG_C_MESSAGE}") {
        fun createRoute(cMessage: String) = "C?$ARG_C_MESSAGE=$cMessage"
    }
    object Book: TestDestination("book/{$ARG_BOOK_ID}") {
        fun createRoute(bookId: Int) = "book/$bookId"
    }
}

fun getDestinationsWithBottomBar(): List<TestDestination> =
    listOf(
        TestDestination.A,
        TestDestination.B,
        TestDestination.C
    )