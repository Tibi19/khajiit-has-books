package com.tam.tesbooks.test

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun TestNavGraph(navController: NavHostController) {

    NavHost(navController, startDestination = TestDestination.A.route) {
        composable(route = TestDestination.A.route) {
            TestScreenA(navigateToBook = { bookId ->
                // navigate will also save the argument in savedStateHandle
                navController.navigate(TestDestination.Book.createRoute(bookId))
            })
        }

        composable(route = TestDestination.B.route) { TestScreenB() }
        composable(
            route = TestDestination.C.route,
            arguments = listOf(navArgument(ARG_C_MESSAGE) { defaultValue = "default" })
        ) { backStackEntry ->
            val message = backStackEntry.arguments?.getString(ARG_C_MESSAGE) ?: "default"
            TestScreenC(message)
        }

        composable(
            route = TestDestination.Book.route,
            arguments = listOf(navArgument(ARG_BOOK_ID) { type = NavType.IntType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt(ARG_BOOK_ID, 0) ?: 0
            // bookId can be passed to the composable now
            // here we don't need to because the viewmodel initializes with the id from the saved state handle
            TestBookScreen()
        }
    }

}