package com.tam.tesbooks.test

import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.tam.tesbooks.util.showToast
import kotlinx.coroutines.launch

@Composable
fun TestScaffold(navigationScaffoldDestinations: Map<TestDestination, () -> Unit>, content: @Composable () -> Unit) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = { BottomBar(scaffoldState, navigationScaffoldDestinations) },
        drawerContent = { TestDrawer(navigationScaffoldDestinations) },
        content = { content() }
    )
}

@Composable
fun BottomBar(scaffoldState: ScaffoldState, navigationScaffoldDestinations: Map<TestDestination, () -> Unit>) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.secondary,
        contentColor = MaterialTheme.colors.primary,
        elevation = 10.dp
    ) {
//        val selectedIndex = remember { mutableStateOf(1) }
        val context = LocalContext.current
        val scope = rememberCoroutineScope()

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
            selected = false,
            onClick = {
//                selectedIndex.value = 1
                navigationScaffoldDestinations[TestDestination.A]?.invoke()
            }
        )

        BottomNavigationItem(
            icon = { Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                modifier = Modifier.size(30.dp)
            ) },
            selected = false,
            onClick = {
//                selectedIndex.value = 2
                navigationScaffoldDestinations[TestDestination.B]?.invoke()
            }
        )
    }
}