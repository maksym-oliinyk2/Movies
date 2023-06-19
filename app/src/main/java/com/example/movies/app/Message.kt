package com.example.movies.app

import arrow.core.Either
import com.example.movies.domain.model.MoviePreview

sealed interface Message

sealed interface MoviesMessage : Message

@JvmInline
value class OnMoviesLoadResult(
    val result: Either<Throwable, List<MoviePreview>>,
) : MoviesMessage