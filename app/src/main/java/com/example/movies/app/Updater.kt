package com.example.movies.app

import io.github.xlopec.tea.core.Update
import io.github.xlopec.tea.core.noCommand

fun update(
    message: Message,
    state: AppState,
): Update<AppState, Command> =
    when (message) {
        is OnMoviesLoadResult -> state.noCommand()
    }