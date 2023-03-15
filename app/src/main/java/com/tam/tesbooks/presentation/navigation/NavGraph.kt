package com.tam.tesbooks.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tam.tesbooks.presentation.screen.book.BookScreen
import com.tam.tesbooks.presentation.screen.book_list.BookListScreen
import com.tam.tesbooks.presentation.screen.bookmarks.BookmarksScreen
import com.tam.tesbooks.presentation.screen.library.LibraryScreen

@Composable
fun MainNavGraph(
    navController: NavHostController,
    scaffoldPaddingValues: PaddingValues
) {

    NavHost(
        navController = navController,
        startDestination = Destination.Library.route,
        modifier = Modifier.padding(bottom = scaffoldPaddingValues.calculateBottomPadding())
    ) {
        composable(route = Destination.Library.route) {
            LibraryScreen(goToBookScreen = { bookId ->
                val bookRoute = Destination.Book.createRoute(bookId)
                navController.navigate(bookRoute)
            })
        }

        composable(
            route = Destination.BookList.route,
            arguments = listOf(navArgument(ARG_BOOK_LIST_ID) { type = NavType.IntType })
        ) {
            BookListScreen(
                goToBookScreen = { bookId ->
                    val bookRoute = Destination.Book.createRoute(bookId)
                    navController.navigate(bookRoute)
                },
                goToPreviousScreen = { navController.popBackStack() }
            )
        }

        composable(
            route = Destination.Book.route,
            arguments = listOf(
                navArgument(ARG_BOOK_ID) { type = NavType.IntType },
                navArgument(ARG_PARAGRAPH_POSITION) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            ),
        ) {
            BookScreen(
                goToLibraryWithSearch = { tag, category ->
                    val libraryWithArguments = Destination.Library.createRoute(tag, category)
                    navController.navigate(libraryWithArguments)
                },
                goToPreviousScreen = { navController.popBackStack() }
            )
        }

        composable(route = Destination.Bookmarks.route) {
            BookmarksScreen(
                goToBookScreenAndParagraph = { bookId, paragraphPosition ->
                    val bookRoute = Destination.Book.createRoute(bookId, paragraphPosition)
                    navController.navigate(bookRoute)
                },
                goToPreviousScreen = { navController.popBackStack() }
            )
        }

    }

}