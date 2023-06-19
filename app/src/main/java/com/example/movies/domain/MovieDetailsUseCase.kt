package com.example.movies.domain

import arrow.core.Either
import com.example.movies.domain.model.Id
import com.example.movies.domain.model.Movie

class MovieDetailsUseCase(
    private val repository: AppRepository,
) {
    suspend fun fetchMovieDetails(
        id: Id,
    ): Either<Throwable, Movie> = repository.fetchMovie(id)
}