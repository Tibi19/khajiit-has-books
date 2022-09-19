package com.tam.tesbooks.test

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun TestNavHost() {
    val navController = rememberNavController()
    val navigationScaffoldDestinations = getNavigationScaffoldDestinations(navController)

    NavHost(navController, startDestination = TestDestination.A.route) {
        composable(route = TestDestination.A.route) { TestScreenA(navigationScaffoldDestinations) }
        composable(route = TestDestination.B.route) { TestScreenB(navigationScaffoldDestinations) }
        composable(route = TestDestination.C.route) { TestScreenC(navigationScaffoldDestinations) }
    }

}

private fun getNavigationScaffoldDestinations(navController: NavController): Map<TestDestination, () -> Unit> =
    mapOf(
        TestDestination.A to { navController.navigate(TestDestination.A.route) },
        TestDestination.B to { navController.navigate(TestDestination.B.route) },
        TestDestination.C to { navController.navigate(TestDestination.C.route) }
    )