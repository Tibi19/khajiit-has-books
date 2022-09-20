package com.tam.tesbooks.test

import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tam.tesbooks.util.showToast
import kotlinx.coroutines.launch

@Composable
fun TestScaffold() {
    val scaffoldState = rememberScaffoldState()
    val navController = rememberNavController()
    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = { BottomBar(scaffoldState, navController) },
        drawerContent = { TestDrawer(scaffoldState, navController) },
        content = { TestNavGraph(navController = navController) }
    )
}

@Composable
fun BottomBar(scaffoldState: ScaffoldState, navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val scope = rememberCoroutineScope()

    val isScreenWithBottomBar = getDestinationsWithBottomBar().any { it.route == currentDestination?.route }
    if(!isScreenWithBottomBar) return

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.secondary,
        contentColor = MaterialTheme.colors.primary,
        elevation = 10.dp
    ) {

        BottomNavigationItem(
            icon = { Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                modifier = Modifier.size(30.dp)
            ) },
            selected = false,
            onClick = {
                scope.launch { scaffoldState.drawerState.open() }
            }
        )

        BottomNavigationItem(
            icon = { Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Home",
                modifier = Modifier.size(30.dp)
            ) },
            selected = currentDestination?.hierarchy?.any { it.route == TestDestination.A.route } == true,
            onClick = {
                navController.navigate(TestDestination.A.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }
        )

        BottomNavigationItem(
            icon = { Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                modifier = Modifier.size(30.dp)
            ) },
            selected = currentDestination?.hierarchy?.any { it.route == TestDestination.B.route } == true,
            onClick = {
                navController.navigate(TestDestination.B.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }
        )
    }
}