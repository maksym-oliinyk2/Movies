package com.example.movies.domain

import arrow.core.Either
import com.example.movies.domain.model.Id
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.MoviePreview

interface AppRepository {

    suspend fun fetchMoviePreviews(): Either<Throwable, List<MoviePreview>>

    suspend fun fetchMovie(
        id: Id,
    ): Either<Throwable, Movie>
}