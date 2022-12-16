package com.tam.tesbooks.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tam.tesbooks.presentation.screen.book.BookScreen
import com.tam.tesbooks.presentation.screen.library.LibraryScreen
import com.tam.tesbooks.test.TestBookScreen

@Composable
fun MainNavGraph(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Destination.Library.route) {
        composable(
            route = Destination.Library.route,
            arguments = listOf(navArgument(ARG_TAG) { nullable = true })
        ) {
            LibraryScreen(goToBookScreen = { bookId ->
                val bookRoute = Destination.Book.createRoute(bookId)
                navController.navigate(bookRoute)
            })
        }

        composable(
            route = Destination.BookList.route,
            arguments = listOf(navArgument(ARG_BOOK_LIST_ID) { type = NavType.StringType })
        ) {}

        composable(
            route = Destination.Book.route,
            arguments = listOf(navArgument(ARG_BOOK_ID) { type = NavType.IntType }),
        ) {
            BookScreen(goToLibraryWithSearch = { tag, category ->
                val libraryWithArguments = Destination.Library.createRoute(tag, category)
                navController.navigate(libraryWithArguments)
            })
        }

        composable(route = Destination.Bookmarks.route) {}
        composable(route = Destination.Settings.route) { TestBookScreen() }
    }

}