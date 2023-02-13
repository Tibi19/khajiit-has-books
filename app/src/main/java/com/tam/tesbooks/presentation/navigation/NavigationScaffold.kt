package com.tam.tesbooks.presentation.navigation

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.tam.tesbooks.presentation.navigation.drawer.Drawer

@Composable
fun NavigationScaffold() {
    val scaffoldState = rememberScaffoldState()
    val navController = rememberNavController()

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = { BottomBar(scaffoldState, navController) },
        drawerContent = { Drawer(scaffoldState, navController) },
        content = { paddingValues ->
            MainNavGraph(
                navController = navController,
                scaffoldPaddingValues = paddingValues
            )
        }
    )

}

