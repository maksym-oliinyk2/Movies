package com.example.movies.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.movies.app.*
import com.example.movies.domain.AppRepositoryImpl
import com.example.movies.domain.model.Id
import io.github.xlopec.tea.core.Sink
import io.ktor.client.plugins.logging.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

typealias MessageHandler = (Message) -> Unit

@Composable
fun App(
    app: AppState,
    handler: MessageHandler,
) {

    when (val screen = app.screen) {
        is DetailsScreen -> TODO()
        is MoviesScreen -> {
            val repo = remember { AppRepositoryImpl(LogLevel.ALL) }

            LaunchedEffect(Unit) {
                val shows = repo.fetchMoviePreviews()

                println("Shows $shows")
                println("Details ${repo.fetchMovie(Id("0"))}")
            }

            Scaffold(
                modifier = Modifier.navigationBarsPadding(),
                topBar = {
                    TopAppBar(
                        modifier = Modifier
                            .statusBarsPadding()
                            .fillMaxWidth(),
                        title = {
                            Text(text = "Movies")
                        }
                    )
                }
            ) { paddingValues ->
                Surface(
                    modifier = Modifier.padding(paddingValues),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android ${screen.loadable.loadableState}")
                }
            }
        }
    }


}

fun CoroutineScope.messageHandler(
    sink: Sink<Message>,
): MessageHandler =
    { message ->
        launch {
            sink(message)
        }
    }

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}