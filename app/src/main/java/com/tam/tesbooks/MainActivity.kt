package com.tam.tesbooks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.tam.tesbooks.presentation.navigation.NavigationScaffold
import com.tam.tesbooks.ui.theme.KhajiitHasBooksTheme
import com.tam.tesbooks.util.showError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                // We wait for UI to start composing
                // This way we omit potential screen flickers from the composing scaffold
                mainViewModel.isLoadingUi.value
            }
        }

        collectLatestFlow(
            flow = mainViewModel.isLoadingData,
            collect = { isLoading ->
                if (isLoading) return@collectLatestFlow
                showUiContent()
            }
        )

        collectLatestFlow(
            flow = mainViewModel.errorFlow,
            collect = { error ->
                showError(this, error)
            }
        )

    }

    private fun showUiContent() {
        setContent {
            KhajiitHasBooksTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavigationScaffold()
                }
            }
        }
    }

}

private fun <T> ComponentActivity.collectLatestFlow(
    flow: Flow<T>,
    collect: suspend (T) -> Unit
) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(collect)
        }
    }
}