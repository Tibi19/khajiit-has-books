package com.tam.tesbooks.presentation.navigation

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.tam.tesbooks.util.*
import kotlinx.coroutines.launch

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
        elevation = Dimens.ELEVATION_DEFAULT,
        modifier = Dimens.SIZE_BOTTOM_BAR_HEIGHT
    ) {

        BottomNavigationItem(
            icon = { Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = CONTENT_DRAWER,
                modifier = Dimens.SIZE_BOTTOM_BAR_ICON
            ) },
            selected = false,
            onClick = {
                scope.launch { scaffoldState.drawerState.open() }
            }
        )

        BottomNavigationItem(
            icon = { Icon(
                imageVector = Icons.Default.Home,
                contentDescription = CONTENT_LIBRARY,
                modifier = Dimens.SIZE_BOTTOM_BAR_ICON
            ) },
            selected = currentDestination?.hierarchy?.any { it.route == Destination.Library.route } == true,
            onClick = {
                navController.navigate(Destination.Library.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                }
            }
        )

        BottomNavigationItem(
            icon = { Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = CONTENT_SETTINGS,
                modifier = Dimens.SIZE_BOTTOM_BAR_ICON
            ) },
            selected = currentDestination?.hierarchy?.any { it.route == Destination.Settings.route } == true,
            onClick = {
                navController.navigate(Destination.Settings.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }
        )

    }
}