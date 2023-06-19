package com.example.movies.app

import com.example.movies.BuildConfig
import com.example.movies.domain.AppRepositoryImpl
import com.example.movies.domain.MovieDetailsUseCase
import com.example.movies.domain.MoviesUseCase
import io.ktor.client.plugins.logging.*
import kotlinx.coroutines.CoroutineScope

interface Environment : CoroutineScope, Dependencies, AppResolver<Environment>, MoviesResolver<Environment>

fun Environment(
    scope: CoroutineScope,
): Environment = object : Environment,
    CoroutineScope by scope,
    Dependencies by Dependencies(if (BuildConfig.DEBUG) LogLevel.ALL else LogLevel.NONE),
    MoviesResolver<Environment> by MoviesResolver(),
    AppResolver<Environment> by AppResolver() {
}

interface Dependencies {
    val moviesUseCase: MoviesUseCase
    val movieDetailsUseCase: MovieDetailsUseCase
}

fun Dependencies(
    logLevel: LogLevel,
): Dependencies {
    val repository = AppRepositoryImpl(logLevel)
    return object : Dependencies {
        override val moviesUseCase: MoviesUseCase = MoviesUseCase(repository)
        override val movieDetailsUseCase: MovieDetailsUseCase = MovieDetailsUseCase(repository)
    }
}