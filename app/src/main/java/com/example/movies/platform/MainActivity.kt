package com.example.movies.platform

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.window.DialogWindowProvider
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.movies.app.Message
import com.example.movies.ui.App
import com.example.movies.ui.messageHandler
import com.example.movies.ui.theme.MoviesTheme
import io.github.xlopec.tea.core.ExperimentalTeaApi
import io.github.xlopec.tea.core.toStatesComponent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalTeaApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MoviesTheme {
                val messages = remember { Channel<Message>() }
                val scope = rememberCoroutineScope()
                val handler = remember { scope.messageHandler(messages::send) }
                val snapshots = remember { component.toStatesComponent()(messages.receiveAsFlow()) }
                val app by snapshots.collectAsState(null)
                val systemUiController = rememberWindowInsetsController()
                val isDarkModeEnabled = isSystemInDarkTheme()

                LaunchedEffect(isDarkModeEnabled) {
                    systemUiController.isAppearanceLightStatusBars = !isDarkModeEnabled
                    systemUiController.isAppearanceLightNavigationBars = !isDarkModeEnabled
                }

                App(
                    app = app ?: return@MoviesTheme,
                    handler = handler
                )
            }
        }
    }
}


@Composable
private fun rememberWindowInsetsController(): WindowInsetsControllerCompat {
    val view = LocalView.current

    return remember(view) {
        val window = view.findWindow()
        WindowCompat.getInsetsController(window, window.decorView)
    }
}

private fun View.findWindow(): Window =
    (parent as? DialogWindowProvider)?.window ?: context.findWindow()

private tailrec fun Context.findWindow(): Window =
    when (this) {
        is Activity -> window
        is ContextWrapper -> baseContext.findWindow()
        else -> error("No window found")
    }