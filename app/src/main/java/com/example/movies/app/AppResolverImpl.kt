package com.example.movies.app

import io.github.xlopec.tea.core.ResolveCtx
import io.github.xlopec.tea.core.Snapshot
import io.github.xlopec.tea.core.effect

fun AppResolver(): AppResolver<Environment> = AppResolverImpl()

fun MoviesResolver(): MoviesResolver<Environment> = MoviesResolverImpl()

private class MoviesResolverImpl : MoviesResolver<Environment> {

    override fun Environment.resolve(
        command: MoviesCommand,
        context: ResolveCtx<Message>,
    ) {
        when (command) {
            DoLoadMovies -> context.effect { OnMoviesLoadResult(moviesUseCase.fetchMoviePreviews()) }
        }
    }

}

private class AppResolverImpl : AppResolver<Environment> {

    override fun Environment.resolve(
        snapshot: Snapshot<Message, AppState, Command>,
        context: ResolveCtx<Message>,
    ) {
        snapshot.commands.forEach { command ->
            when (command) {
                is MoviesCommand -> resolve(command, context)
            }
        }
    }
}
