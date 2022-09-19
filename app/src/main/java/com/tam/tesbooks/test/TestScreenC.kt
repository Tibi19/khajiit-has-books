package com.tam.tesbooks.test

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.tam.tesbooks.util.showToast

@Composable
fun TestScreenC(navigationScaffoldDestinations: Map<TestDestination, () -> Unit>) {

    val context = LocalContext.current

    TestScaffold(navigationScaffoldDestinations) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(onClick = { showToast(context, "Clicked in C") }) {
                Text(text = "C button")
            }

            Spacer(modifier = Modifier.size(10.dp))

            Text(text = "C text")

        }

    }

}