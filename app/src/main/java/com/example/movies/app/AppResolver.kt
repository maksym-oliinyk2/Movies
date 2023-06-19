package com.example.movies.app

import io.github.xlopec.tea.core.ResolveCtx
import io.github.xlopec.tea.core.Snapshot

interface AppResolver<Env> {

    fun Env.resolve(
        snapshot: Snapshot<Message, AppState, Command>,
        context: ResolveCtx<Message>,
    )

}

interface MoviesResolver<Env> {
    fun Env.resolve(
        command: MoviesCommand,
        context: ResolveCtx<Message>,
    )
}