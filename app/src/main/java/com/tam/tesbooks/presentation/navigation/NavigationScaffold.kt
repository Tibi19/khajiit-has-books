package com.tam.tesbooks.presentation.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.tam.tesbooks.presentation.dialog.BottomSheetDialogProvider
import com.tam.tesbooks.presentation.navigation.drawer.Drawer
import com.tam.tesbooks.util.ALPHA_NORMAL_SCRIM
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NavigationScaffold() {
    val scaffoldState = rememberScaffoldState()
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()

    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { BottomSheetDialogProvider.isValueChanging(it) },
        skipHalfExpanded = false
    )

    BackHandler(bottomSheetState.isVisible) {
        coroutineScope.launch { bottomSheetState.hide() }
    }

    val scrimColor = Color.Black.copy(alpha = ALPHA_NORMAL_SCRIM)
    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        scrimColor = scrimColor,
        sheetContent = { BottomSheetDialogProvider.Content(bottomSheetState) }
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            bottomBar = { BottomBar(scaffoldState, navController) },
            drawerContent = { Drawer(scaffoldState, navController) },
            drawerScrimColor = scrimColor,
            content = { paddingValues ->
                MainNavGraph(
                    navController = navController,
                    scaffoldPaddingValues = paddingValues
                )
            }
        )
    }

}

