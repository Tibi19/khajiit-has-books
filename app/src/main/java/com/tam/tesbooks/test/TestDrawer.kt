package com.tam.tesbooks.test

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.tam.tesbooks.util.showToast
import kotlinx.coroutines.launch

@Composable
fun TestDrawer(scaffoldState: ScaffoldState, navController: NavHostController) {

    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 50.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Header", fontSize = 50.sp)
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                scope.launch { scaffoldState.drawerState.close() }
                navController.navigate(TestDestination.C.createRoute("Got message from C")) {
                    launchSingleTop = true
                }
            }
        ) {
            Text(text = "Go to C with message")
        }

        Button(
            onClick = {
                scope.launch { scaffoldState.drawerState.close() }
                navController.navigate(TestDestination.C.route) {
                    launchSingleTop = true
                }
            }
        ) {
            Text(text = "Go to C with no message")
        }
    }

}