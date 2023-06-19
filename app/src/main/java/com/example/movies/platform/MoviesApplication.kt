package com.example.movies.platform

import android.app.Activity
import android.app.Application
import com.example.movies.app.*
import io.github.xlopec.tea.core.Component
import io.github.xlopec.tea.core.Initializer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job

class MoviesApplication : Application() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val component: Component<Message, AppState, Command> by lazy {
        val env = Environment(
            scope = CoroutineScope(Job() + Dispatchers.Default.limitedParallelism(1))
        )

        Component(
            initializer = Initializer(AppState(MoviesScreen()), DoLoadMovies),
            resolver = { snapshot, context -> with(env) { resolve(snapshot, context) } },
            updater = ::update,
            scope = env,
        )
    }
}

inline val Activity.app
    get() = application as MoviesApplication

inline val Activity.component: Component<Message, AppState, Command>
    get() = app.component
